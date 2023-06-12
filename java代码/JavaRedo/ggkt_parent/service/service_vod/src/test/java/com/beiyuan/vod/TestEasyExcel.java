package com.beiyuan.vod;

import com.alibaba.excel.EasyExcel;
import com.beiyuan.vod.excel.ExcelListener;
import com.beiyuan.vod.excel.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: beiyuan
 * @date: 2022/11/30  20:43
 */
public class TestEasyExcel {
    @Test
    void testWrite(){
        String filePath="C:\\Users\\beilinanju\\Desktop\\user.xlsx";
        List<User>list=new ArrayList<>();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setId(i);
            user.setName("excel"+i);
            list.add(user);
        }
        EasyExcel.write(filePath, User.class).sheet("写表").doWrite(list);
    }

    @Test
    void testRead(){
        String filePath="C:\\Users\\beilinanju\\Desktop\\user.xlsx";
        EasyExcel.read(filePath, User.class,new ExcelListener()).sheet(0).doRead();
    }
}
