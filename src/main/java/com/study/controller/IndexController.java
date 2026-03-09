package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/login")  //处理GET请求，访问/login路径时会调用这个方法
    public String login() {
        return "login";  //返回视图名称，Spring MVC会根据配置的视图解析器来解析这个视图名称，并找到对应的HTML页面进行渲染
    }

    @GetMapping("/index")
    public String index() {
        return "/";
    }



}
