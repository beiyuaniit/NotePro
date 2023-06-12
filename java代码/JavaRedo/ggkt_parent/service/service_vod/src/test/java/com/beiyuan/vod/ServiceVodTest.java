package com.beiyuan.vod;

import com.beiyuan.vod.service.TeacherService;
import com.beiyuan.vod.utils.ConstantPropertiesUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author: beiyuan
 * @date: 2022/11/1  22:08
 */
@SpringBootTest
public class ServiceVodTest {

    @Autowired
    TeacherService teacherService;

    @Test
    void getAllTeachers(){
        teacherService.list().forEach(System.out::println);
    }

    @Test
    void testCos(){
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        System.out.println(secretId+" "+secretKey);
    }

    @Test
    void testMap(){

        List<Integer>list=new ArrayList<>();
        Math.max(2,3);
    }

}
