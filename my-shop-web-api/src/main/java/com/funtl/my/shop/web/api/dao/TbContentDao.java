package com.funtl.my.shop.web.api.dao;

import com.funtl.my.shop.domain.TbContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Zhan
 * @create 2019/8/24 - 19:45
 */
@Repository
public interface TbContentDao {
    /**
     * 根据类别查询内容列表
     * @param tbContent
     * @return
     */
    List<TbContent> selectByCategoryId(TbContent tbContent);
}
