package org.example.duanLianJie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.admin.dao.entity.UserDO;
import org.example.duanLianJie.admin.dto.req.UserLoginReqDTO;
import org.example.duanLianJie.admin.dto.req.UserRegisterReqDTO;
import org.example.duanLianJie.admin.dto.req.UserUpdateReqDTO;
import org.example.duanLianJie.admin.dto.resp.UserLoginRespDTO;
import org.example.duanLianJie.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名返回用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return
     */
    Boolean hasusername(String username);

    /**
     * 用户注册
     * @param requestParam 请求参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
     * 根据用户名修改用户信息
     *
     * @param requestParam 用户请求参数
     */
    void updata(UserUpdateReqDTO requestParam);

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 判断用户是否登录
     * @param token
     * @return
     */
    Boolean checkLogin(String username, String token);

    /**
     * 用户退出登录
     *
     * @param username
     * @param token
     */
    void logout(String username, String token);
}
