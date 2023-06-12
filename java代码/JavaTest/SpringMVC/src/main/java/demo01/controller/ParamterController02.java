package demo01.controller;

import demo01.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * @author: beiyuan
 * @className: ParamterController
 * @date: 2022/7/11  21:47
 */
@Controller
public class ParamterController02 {

    @RequestMapping("/param01")
    public String testParam(String id,@RequestParam("username") String name,
                            @RequestHeader("Host") String host){
        System.out.println(id+" "+name+"  "+host);
        return "success";
    }

    @RequestMapping("/param02")
    public String toUser(User user){
        System.out.println(user);
        return "target";
    }
}
