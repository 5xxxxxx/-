package org.example.duanLianJie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.result.Result;
import org.example.duanLianJie.admin.common.convention.result.Results;
import org.example.duanLianJie.admin.dto.req.GroupSaveReqDTO;
import org.example.duanLianJie.admin.dto.req.GroupUpdateDTO;
import org.example.duanLianJie.admin.dto.resp.GroupRespDTO;
import org.example.duanLianJie.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    /**
     * 查询短链接分组
     * @return
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<GroupRespDTO>> groupList() {
        return Results.success(groupService.groupList());
    }

    /**
     * 修改短链接分组
     * @param requestParam
     * @return
     */
    @PutMapping("/api/short-link/v1/group")
    Result<Void> update(@RequestBody GroupUpdateDTO requestParam) {
        groupService.update(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接分组
     * @param gid 短链接分组标识
     * @return
     */
    @DeleteMapping ("/api/short-link/v1/group")
    Result<Void> delete(@RequestParam String gid) {
        groupService.delete(gid);
        return Results.success();
    }

}
