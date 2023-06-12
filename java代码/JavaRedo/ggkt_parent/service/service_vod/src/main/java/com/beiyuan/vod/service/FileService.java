package com.beiyuan.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: beiyuan
 * @date: 2022/11/9  21:23
 */
public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
