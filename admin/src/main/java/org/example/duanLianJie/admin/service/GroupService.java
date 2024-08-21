package org.example.duanLianJie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.admin.dao.entity.GroupDO;
import org.example.duanLianJie.admin.dto.req.GroupSaveReqDTO;

public interface GroupService extends IService<GroupDO> {

    void save(GroupSaveReqDTO requestParam);
}
