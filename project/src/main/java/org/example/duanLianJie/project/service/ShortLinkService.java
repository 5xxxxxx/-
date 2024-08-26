package org.example.duanLianJie.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.duanLianJie.project.dao.entity.ShortLinkDO;
import org.example.duanLianJie.project.dto.req.ShortLinkCreateReqDTO;
import org.example.duanLianJie.project.dto.req.ShortLinkPageReqDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkCreateRespDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkPageRespDTO;

public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     * @param requstParam
     * @return
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requstParam);

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    IPage<ShortLinkPageRespDTO> shortLinkPage(ShortLinkPageReqDTO requestParam);
}
