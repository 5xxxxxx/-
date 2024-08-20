package org.example.duanLianJie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.errorcode.BaseErrorCode;
import org.example.duanLianJie.admin.common.convention.exception.ClientException;
import org.example.duanLianJie.admin.dao.entity.UserDO;
import org.example.duanLianJie.admin.dao.mapper.UserMapper;
import org.example.duanLianJie.admin.dto.resp.UserRespDTO;
import org.example.duanLianJie.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
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
}
