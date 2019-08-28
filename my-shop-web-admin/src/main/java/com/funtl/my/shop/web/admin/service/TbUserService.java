package com.funtl.my.shop.web.admin.service;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.dto.PageInfo;
import com.funtl.my.shop.commons.persisten.BaseService;
import com.funtl.my.shop.domain.TbUser;

import java.util.List;

/**
 * @author Zhan
 * @create 2019/8/21 - 8:27
 */
public interface TbUserService extends BaseService<TbUser> {
    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    TbUser login(String email,String password);
}
