package org.example.duanLianJie.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.example.duanLianJie.project.common.convention.result.Result;
import org.example.duanLianJie.project.common.convention.result.Results;
import org.example.duanLianJie.project.dto.req.ShortLinkCreateReqDTO;
import org.example.duanLianJie.project.dto.req.ShortLinkPageReqDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkCreateRespDTO;
import org.example.duanLianJie.project.dto.resp.ShortLinkPageRespDTO;
import org.example.duanLianJie.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> shortLinkPage(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.shortLinkPage(requestParam));
    }

}
