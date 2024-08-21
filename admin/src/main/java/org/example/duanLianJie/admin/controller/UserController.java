package org.example.duanLianJie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.admin.common.convention.result.Result;
import org.example.duanLianJie.admin.common.convention.result.Results;
import org.example.duanLianJie.admin.dto.req.UserRegisterReqDTO;
import org.example.duanLianJie.admin.dto.req.UserUpdateReqDTO;
import org.example.duanLianJie.admin.dto.resp.UserRespDTO;
import org.example.duanLianJie.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(String username) {
        return Results.success(userService.hasusername(username));
    }

    /**
     * 用户注册
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户信息
     * @param requestParam
     * @return
     */
    @PutMapping("/api/short-link/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam) {
        userService.updata(requestParam);
        return Results.success();
    }

}
