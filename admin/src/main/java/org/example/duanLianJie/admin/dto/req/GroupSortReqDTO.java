package org.example.duanLianJie.admin.dto.req;

import lombok.Data;

@Data
public class GroupSortReqDTO {
    /**
     * 短链接分组标识
     */
    String gid;

    /**
     * 短链接排序标识
     */
    Integer sortOrder;
}
