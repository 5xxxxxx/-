package org.example.duanLianJie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.admin.dao.entity.GroupDO;
import org.example.duanLianJie.admin.dto.req.GroupSaveReqDTO;
import org.example.duanLianJie.admin.dto.req.GroupUpdateDTO;
import org.example.duanLianJie.admin.dto.resp.GroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     * @param requestParam
     */
    void save(GroupSaveReqDTO requestParam);

    /**
     * 查询短链接分组
     * @return
     */
    List<GroupRespDTO> groupList();

    /**
     * 修改短链接分组
     * @param requestParam
     */
    void update(GroupUpdateDTO requestParam);

    /**
     * 删除短链接分组
     * @param gid
     */
    void delete(String gid);
}
