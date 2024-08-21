package org.example.duanLianJie.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.dao.entity.GroupDO;
import org.example.duanLianJie.admin.dao.mapper.GroupMapper;
import org.example.duanLianJie.admin.service.GroupService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

}
