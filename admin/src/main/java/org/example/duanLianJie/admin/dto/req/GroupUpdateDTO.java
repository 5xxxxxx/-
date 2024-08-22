package org.example.duanLianJie.admin.dto.req;

import lombok.Data;

@Data
public class GroupUpdateDTO {

    /**
     *  短链接分组标识
     */
    private String gid;

    /**
     *  短链接分组名
     */
    String name;
}
