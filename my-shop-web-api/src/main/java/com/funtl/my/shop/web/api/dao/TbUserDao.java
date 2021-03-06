package com.funtl.my.shop.web.api.dao;

import com.funtl.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

/**
 * 会员管理
 * @author Zhan
 * @create 2019/8/25 - 10:55
 */
@Repository
public interface TbUserDao {
    /**
     * 登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);

}
