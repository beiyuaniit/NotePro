package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: beiyuan
 * @className: RestController
 * @date: 2022/7/22  9:54
 */
@RestController
public class RestReq {

    @GetMapping("/rest")
    public String restGet(){

        return "get";
    }

    @PostMapping("/rest")
    public String restPost(){

        return "post";
    }


    @DeleteMapping("/rest")
    public String restDelete(){

        return "delete";
    }

    @PutMapping("/rest")
    public String restPut(){

        return "put";
    }


}
