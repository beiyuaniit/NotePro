package com.beiyuan.music.service.impl;


import com.beiyuan.music.common.utils.ConstantPropertiesUtil;
import com.beiyuan.music.service.QCloudCOSService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Download;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

/**
 * @author: beiyuan
 * @date: 2022/11/9  21:24
 */
@Service
public class QCloudCOSServiceImpl implements QCloudCOSService {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint;

    String bucketName;

    String baseUrl;

    //在构造函数执行开始前执行，每创建一个对象执行一次
    {
        endpoint = ConstantPropertiesUtil.END_POINT;
        bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        baseUrl = "https://"+bucketName+"."+"cos"+"."+endpoint+".myqcloud.com";
    }

    private static COSClient getCOSClient(){
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http),超时, 代理等 set 方法
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
       return new COSClient(cred, clientConfig);
    }
    @Override
    //以下用到的是腾讯对象云服务
    public String upload(MultipartFile file,String type) {

        COSClient cosClient=getCOSClient();

        try{
            // 指定要上传的文件
            InputStream inputStream = file.getInputStream();
            // 指定文件将要存放的存储桶
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = UUID.randomUUID().toString().replaceAll("-","")+
                    file.getOriginalFilename();
            String dateUrl = type+"/"+new DateTime().toString("yyyy/MM/dd");
            key = dateUrl+"/"+key;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, key, inputStream,objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
           // System.out.println(JSON.toJSONString(putObjectResult));
            //https://ggkt-atguigu-1310644373.cos.ap-beijing.myqcloud.com/01.jpg



            //String url = baseUrl+"/"+key;、
            //存储桶要共有读这个链接才能直接访问
            return cosClient.getObjectUrl(bucketName,key).toString();
        } catch (Exception clientException) {
            clientException.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(String avatorUrl) {

        if(avatorUrl==null){
            return true;
        }
        String key=avatorUrl.replace(baseUrl,"");


        COSClient cosClient=getCOSClient();
        try {
            DeleteObjectRequest deleteObjectRequest=new DeleteObjectRequest(bucketName,key);
            cosClient.deleteObject(deleteObjectRequest);
            return true;
        }catch (Exception clientException) {
            clientException.printStackTrace();
            return false;
        }
    }
}
