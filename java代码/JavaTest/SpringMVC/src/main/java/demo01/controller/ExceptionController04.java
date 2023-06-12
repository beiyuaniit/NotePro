package demo01.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author: beiyuan
 * @className: ExceptionController04
 * @date: 2022/7/15  16:11
 */
@ControllerAdvice
public class ExceptionController04 {
    @ExceptionHandler({ArithmeticException.class,NullPointerException.class})
    public String handleArithmetic(Exception ex, Model model){
        model.addAttribute("ex",ex);
        return "error";
    }

}
