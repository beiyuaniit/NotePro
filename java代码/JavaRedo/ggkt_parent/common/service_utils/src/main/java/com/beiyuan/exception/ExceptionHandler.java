package com.beiyuan.exception;

import com.beiyuan.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: beiyuan
 * @date: 2022/11/3  22:55
 */
@ControllerAdvice   //异常处理。在spring-web知识点中中
public class ExceptionHandler {

    //若局部异常处理器已经处理，则不再处理
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class )
    @ResponseBody
    public Result globalExceptionHandler(){
        return Result.fail(null).message("发生了异常");
    }

    //优先处理局部异常
    @org.springframework.web.bind.annotation.ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result arithmeticExceptionHandler(){
        return  Result.fail(null).message("除法异常");
    }

    //自定义异常
    @org.springframework.web.bind.annotation.ExceptionHandler(MyException.class)
    @ResponseBody
    public Result myExceptionHandler(){
        return  Result.fail(null).message("自定义异常");
    }

}
