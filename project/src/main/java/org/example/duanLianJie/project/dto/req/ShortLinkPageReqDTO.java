package org.example.duanLianJie.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.example.duanLianJie.project.dao.entity.ShortLinkDO;

@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    private String gid;
}
