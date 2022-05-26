package com.example.demo.controller;

import com.example.demo.service.UploadService;
import com.example.demo.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author gqz20
 * @create 2022-04-24 14:02
 **/
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public Result upload(MultipartFile file) {
        String url = uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            return Result.error("上传图片失败");
        }
        return Result.ok("上传成功",url);
    }
}
