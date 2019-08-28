package com.funtl.my.shop.web.api.service;

import com.funtl.my.shop.domain.TbContent;

import java.util.List;

/**
 * @author Zhan
 * @create 2019/8/24 - 19:56
 */
public interface TbContentService {
    /**
     * 根据类别查询内容列表
     * @param categoryId
     * @return
     */
    List<TbContent> selectByCategoryId(Long categoryId);
}
