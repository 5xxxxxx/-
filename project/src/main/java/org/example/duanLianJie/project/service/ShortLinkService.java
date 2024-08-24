package org.example.duanLianJie.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.project.dao.entity.ShortLinkDO;
import org.example.duanLianJie.project.dto.req.ShortLinkCreateReqDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkCreateRespDTO;

public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     * @param requstParam
     * @return
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requstParam);
}
