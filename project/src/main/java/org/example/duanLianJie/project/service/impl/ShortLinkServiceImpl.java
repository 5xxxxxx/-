package org.example.duanLianJie.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.duanLianJie.project.common.convention.exception.ServiceException;
import org.example.duanLianJie.project.dao.entity.ShortLinkDO;
import org.example.duanLianJie.project.dao.mapper.ShortLinkMapper;
import org.example.duanLianJie.project.dto.req.ShortLinkCreateReqDTO;
import org.example.duanLianJie.project.dto.req.ShortLinkPageReqDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkCreateRespDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkPageRespDTO;
import org.example.duanLianJie.project.service.ShortLinkService;
import org.example.duanLianJie.project.toolkit.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> cachePenetrationBloomFilter;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requstParam) {
        String shortUri = generateSuffix(requstParam);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requstParam, ShortLinkDO.class);
        shortLinkDO.setFullShortUrl(requstParam.getDomain() + "/" + shortUri);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setEnableStatus(0);
        try {
            baseMapper.insert(shortLinkDO);
        }catch (DuplicateKeyException exception) {
            LambdaQueryWrapper<ShortLinkDO> wrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, requstParam.getDomain() + "/" + shortUri);
            ShortLinkDO hasShortLinkDo = baseMapper.selectOne(wrapper);
            if (hasShortLinkDo != null) {
                log.error("短链接生成出错");
                throw new ServiceException("短链接生成重复");
            }
        }
        cachePenetrationBloomFilter.add(requstParam.getDomain() + "/" + shortUri);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .gid(shortLinkDO.getGid())
                .originUrl(shortLinkDO.getOriginUrl())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> shortLinkPage(ShortLinkPageReqDTO requestParam) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, requestParam.getGid())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(requestParam, queryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    public String generateSuffix(ShortLinkCreateReqDTO requestParam) {
        String suffix;
        int generateCustomCount = 0;
        while (true) {
            if (generateCustomCount > 10) {
                throw new ServiceException("生成短链接失败");
            }
            String originUrl = requestParam.getOriginUrl();
            originUrl = originUrl += System.currentTimeMillis();
            suffix = HashUtil.hashToBase62(originUrl);
            if (!cachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + suffix)) {
                break;
            }
            generateCustomCount++;
        }
        return suffix;
    }
}
