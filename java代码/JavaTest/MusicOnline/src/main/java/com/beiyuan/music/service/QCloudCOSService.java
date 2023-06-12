package com.beiyuan.music.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: beiyuan
 * @date: 2022/11/9  21:23
 */
public interface QCloudCOSService {
    //文件上传
    String upload(MultipartFile file,String type);

    Boolean delete(String avatorUrl);
}
