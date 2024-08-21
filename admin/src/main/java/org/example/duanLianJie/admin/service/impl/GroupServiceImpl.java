package org.example.duanLianJie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.dao.entity.GroupDO;
import org.example.duanLianJie.admin.dao.mapper.GroupMapper;
import org.example.duanLianJie.admin.dto.req.GroupSaveReqDTO;
import org.example.duanLianJie.admin.service.GroupService;
import org.example.duanLianJie.admin.util.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Override
    public void save(GroupSaveReqDTO requestParam) {
        String gid;
        while (true) {
            gid = RandomGenerator.generateRandom();
            if (!hasGid(gid)) {
                break;
            }
        }
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(requestParam.getName())
                .build();
        baseMapper.insert(groupDO);
    }

    public Boolean hasGid(String gid) {
        LambdaQueryWrapper<GroupDO> wrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                // TODO: 2024/8/21 获取用户名
                .eq(GroupDO::getUsername, null)
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = baseMapper.selectOne(wrapper);
        return groupDO != null;
    }
}
