package com.funtl.my.shop.web.ui.controller;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.utils.EmailSendUtils;
import com.funtl.my.shop.web.ui.api.UsersApi;
import com.funtl.my.shop.web.ui.constrant.SystemConstants;
import com.funtl.my.shop.web.ui.dto.TbUser;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 * @author Zhan
 * @create 2019/8/25 - 10:46
 */
@Controller
public class LoginController {

    @Autowired
    private EmailSendUtils emailSendUtils;

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    /**
     * 登录
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(TbUser tbUser, Model model, HttpServletRequest request) throws Exception {
        emailSendUtils.send("用户登录",String.format("用户 【%s】 登录 MyShop",tbUser.getUsername()),"1463992903@qq.com");
        model.addAttribute("username",tbUser.getUsername());

        //校验验证码
        if (!checkVerification(tbUser,request)){
            model.addAttribute("baseResult", BaseResult.fail("验证码有误，请重新输入！"));
            return "login";
        }

        TbUser user = UsersApi.login(tbUser);

        //登录失败
        if (user == null){
            model.addAttribute("baseResult", BaseResult.fail("用户名或密码错误，请重新输入！"));
            return "login";
        }

        //登录成功
        else {
            //将会员信息放入 session
            request.getSession().setAttribute(SystemConstants.SESSION_USER_KEY,user);
            return "redirect:/index";
        }
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(SystemConstants.SESSION_USER_KEY);
        return "redirect:/index";
    }


    /**
     * 校验验证码
     * @param tbUser
     * @param request
     * @return
     */
    public boolean checkVerification(TbUser tbUser, HttpServletRequest request){
        String verification = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.equals(verification,tbUser.getVerification())){
            return true;
        }
        return false;
    }
}
