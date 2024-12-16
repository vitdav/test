package com.sgugo.sky.controller.admin;

import com.sgugo.sky.constant.MessageConstant;
import com.sgugo.sky.result.R;
import com.sgugo.sky.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common/")
@Tag(name="Common-通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file 上传的文件，MultipartFile格式
     * @return R
     */
    @PostMapping("upload")
    @Operation(summary="upload->文件上传")
    public R<String> upload(MultipartFile file){
        try {
            //1. 获取待上传对象的文件名后缀，用于生成上传后的文件名
            String filename = file.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf("."));

            // 2. 根据UUID生成随机文件名作为objectName（防止上传的文件重名）
            String objectName = UUID.randomUUID().toString() + ext;

            //3. 调用 AliOssUtil.upload 进行文件上传
            String path = aliOssUtil.upload(file.getBytes(), objectName);
            return R.success(path);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }

        return R.error(MessageConstant.UPLOAD_FAILED);
    }
}
