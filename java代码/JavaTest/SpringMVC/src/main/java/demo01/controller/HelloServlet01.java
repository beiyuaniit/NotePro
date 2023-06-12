package demo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: beiyuan
 * @className: HelloServlet01
 * @date: 2022/7/11  17:25
 */
@Controller

public class HelloServlet01 {

    @RequestMapping("/")
    public String index(){
        System.out.println("ok");
        return "index";
    }

    @RequestMapping("target")
    public String toTarget(){

        return "target";
    }

    @GetMapping("/te?tGetMapping")
    public String testGetMapping(){
        return "success";
    }

    @RequestMapping("/testRest/{id}/{name}")
    public String testRest(
            @PathVariable("id") String id,
            @PathVariable("name")String name){
        System.out.println(id+"  "+name);
        return "success";
    }
}
