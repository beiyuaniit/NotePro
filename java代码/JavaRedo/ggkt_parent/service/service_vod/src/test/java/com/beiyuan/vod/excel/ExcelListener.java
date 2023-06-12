package com.beiyuan.vod.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author: beiyuan
 * @date: 2022/11/30  21:00
 */
public class ExcelListener extends AnalysisEventListener<User> {

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);

    }

    //一行行读取并封装
    // 默认不读取excel的表头、
    @Override
    public void invoke(User data, AnalysisContext context) {
        System.out.println(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
