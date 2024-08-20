package org.example.duanLianJie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.admin.dao.entity.UserDO;
import org.example.duanLianJie.admin.dto.req.UserRegisterReqDTO;
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
}
