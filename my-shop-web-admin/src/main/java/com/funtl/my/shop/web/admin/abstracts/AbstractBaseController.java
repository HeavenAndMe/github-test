package com.funtl.my.shop.web.admin.abstracts;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.dto.PageInfo;
import com.funtl.my.shop.commons.persisten.BaseEntity;
import com.funtl.my.shop.commons.persisten.BaseService;
import com.funtl.my.shop.domain.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共抽象控制器
 * @author Zhan
 * @create 2019/8/24 - 9:15
 */
public abstract class AbstractBaseController<T extends BaseEntity,S extends BaseService<T>> {

    /*
    注入业务逻辑层
     */
    @Autowired
    protected S service;

    /**
     * 跳转列表页
     * @return
     */
    public abstract String list();

    /**
     * 跳转表单页
     * @return
     */
    public abstract String form();

    /**
     * 保存
     * @param entity
     * @param model
     * @param redirectAttributes
     * @return
     */
    public abstract String save(T entity, Model model, RedirectAttributes redirectAttributes);

    /**
     * 删除
     * @param ids
     * @return
     */
    public abstract BaseResult delete(String ids);

    /**
     * 分页查询
     * @param request
     * @param entity
     * @return
     */
    public abstract PageInfo<T> page(HttpServletRequest request, T entity);

    /**
     * 跳转详情页
     * @param entity
     * @return
     */
    public abstract String detail(T entity);
}
