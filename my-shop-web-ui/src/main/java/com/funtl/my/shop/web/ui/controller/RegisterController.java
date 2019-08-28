package com.funtl.my.shop.web.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 注册控制器
 * @author Zhan
 * @create 2019/8/25 - 14:40
 */
@Controller
public class RegisterController {

    /**
     * 跳转注册页
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }
}
