package org.example.duanLianJie.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.errorcode.BaseErrorCode;
import org.example.duanLianJie.admin.common.convention.exception.ClientException;
import org.example.duanLianJie.admin.dao.entity.UserDO;
import org.example.duanLianJie.admin.dao.mapper.UserMapper;
import org.example.duanLianJie.admin.dto.req.UserLoginReqDTO;
import org.example.duanLianJie.admin.dto.req.UserRegisterReqDTO;
import org.example.duanLianJie.admin.dto.req.UserUpdateReqDTO;
import org.example.duanLianJie.admin.dto.resp.UserLoginRespDTO;
import org.example.duanLianJie.admin.dto.resp.UserRespDTO;
import org.example.duanLianJie.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.example.duanLianJie.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(wrapper);
        UserRespDTO result = new UserRespDTO();
        if(userDO == null) {
            throw new ClientException(BaseErrorCode.USER_NAME_EXIST_ERROR);
        }
        BeanUtils.copyProperties(userDO, result);
        return result;

    }

    @Override
    public Boolean hasusername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (hasusername(requestParam.getUsername())) {
            throw new ClientException("用户名已存在");
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if (inserted < 1) {
                    throw new ClientException("用户已存在");
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException("用户名已存在");
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void updata(UserUpdateReqDTO requestParam) {
        // TODO: 2024/8/21 判断用户是否为当前登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(wrapper);
        if (userDO == null ) {
            throw new ClientException("用户不存在");
        }
        Boolean hasKey = stringRedisTemplate.hasKey("login_" + requestParam.getUsername());
        if (hasKey != null && hasKey) {
            throw new ClientException("用户已登录");
        }
        String uuid = UUID.randomUUID().toString();
        //stringRedisTemplate.opsForValue().set(uuid, JSON.toJSONString(userDO), 30L, TimeUnit.MINUTES);
        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login_" + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    //4f8a1251-478a-43d1-a5f1-ac93317907f4
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }
}
