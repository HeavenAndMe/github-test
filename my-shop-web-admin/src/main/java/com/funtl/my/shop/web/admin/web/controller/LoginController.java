package com.funtl.my.shop.web.admin.web.controller;

import com.funtl.my.shop.commons.constant.ConstantUtils;
import com.funtl.my.shop.commons.utils.CookieUtils;
import com.funtl.my.shop.domain.TbUser;
import com.funtl.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Zhan
 * @create 2019/8/20 - 20:26
 */
@Controller
public class LoginController {
    public static final String COOK_NAME_USER_INFO = "userInfo";

    @Autowired
    private TbUserService tbUserService;

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping(value = {"","login"},method = RequestMethod.GET)
    public String login(HttpServletRequest request,Model model){
        String userInfo = CookieUtils.getCookieValue(request, COOK_NAME_USER_INFO);

        if (!StringUtils.isBlank(userInfo)){
            String[] userInfoArray = userInfo.split(":");
            String email = userInfoArray[0];
            String password = userInfoArray[1];

            model.addAttribute("email",email);
            model.addAttribute("password",password);
            model.addAttribute("isRemember",true);
        }

        return "login";
    }

    /**
     * 登录逻辑
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(String email, String password, String isRemember,
                        HttpServletRequest request, HttpServletResponse response,Model model){
        TbUser tbUser = tbUserService.login(email, password);
        boolean isRememberB = isRemember == null ? false : true;

        if (!isRememberB){
            //清除记住的密码
            CookieUtils.deleteCookie(request,response,COOK_NAME_USER_INFO);
        }

        //登录失败
        if (tbUser==null){
            model.addAttribute("message","用户名或密码错误，请重新输入");
            return login(request,model);
        }

        //登录成功
        else {
            if (isRememberB){
                //用户信息存储一周
                CookieUtils.setCookie(request,response,COOK_NAME_USER_INFO,String.format("%s:%s",email,password),7 * 24 * 60 *60);
            }

            //将登录信息放入会话
            request.getSession().setAttribute(ConstantUtils.SESSION_USER,tbUser);
            return "redirect:/main";
        }
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request,Model model){
        request.getSession().invalidate();
        return login(request,model);
    }
}
