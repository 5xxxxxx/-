package org.example.duanLianJie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.result.Result;
import org.example.duanLianJie.admin.common.convention.result.Results;
import org.example.duanLianJie.admin.dto.req.GroupSaveReqDTO;
import org.example.duanLianJie.admin.service.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody GroupSaveReqDTO requestParam) {
        groupService.save(requestParam);
        return Results.success();
    }
}
