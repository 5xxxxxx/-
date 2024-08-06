package org.example.duanLianJie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.result.Result;
import org.example.duanLianJie.admin.common.convention.result.Results;
import org.example.duanLianJie.admin.dto.resp.UserRespDTO;
import org.example.duanLianJie.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    /**
     *根据用户名查询用户
     */
    @GetMapping("/api/link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }
}
