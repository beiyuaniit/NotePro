package com.beiyuan.vod.queryCondition;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;


/**
 * @author: beiyuan
 * @date: 2022/11/2  17:22
 */
@Data
public class TeacherQueryPage {

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("职称等级")
    private Integer level;

    //入驻时间在下面两个时间之间
    @ApiModelProperty("入驻时间起点")
    private String joinDateBegin;

    @ApiModelProperty("入驻时间结尾")
    private String joinDateEnd;
}
