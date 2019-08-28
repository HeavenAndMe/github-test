package com.funtl.my.shop.web.api.service;

import com.funtl.my.shop.domain.TbUser;

/**
 * 会员管理
 * @author Zhan
 * @create 2019/8/25 - 10:58
 */
public interface TbUserService {
    /**
     * 登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);
}
