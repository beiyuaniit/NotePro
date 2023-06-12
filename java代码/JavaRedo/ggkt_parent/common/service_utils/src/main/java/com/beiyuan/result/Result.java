package com.beiyuan.result;

import com.beiyuan.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author: beiyuan
 * @date: 2022/11/2  16:41
 */
@Data
public class Result <T>{

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("信息")
    private String message;

    @ApiModelProperty("数据")
    private T data;


    public static<T> Result<T> ok(T data){
        Result<T> result=new Result<>();
        if(data!=null){
            result.setData(data);
        }
        result.setCode(20000);
        result.setMessage("success");
        return result;
    }

    public static<T> Result<T> fail(T data){
        Result<T> result=new Result<>();
        if(data!=null){
            result.setData(data);
        }
        result.setCode(400);
        result.setMessage("fail");
        return result;
    }

    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

}
