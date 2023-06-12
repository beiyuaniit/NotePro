package com.beiyuan.vod.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: beiyuan
 * @date: 2022/11/30  20:44
 */
@Data
public class User {
    @ExcelProperty(value = "用户编号",index = 0) //读的时候不知道是哪一列，所以指定下index
    private int id;
    @ExcelProperty(value = "用户姓名",index = 1)
    private String name;
}
