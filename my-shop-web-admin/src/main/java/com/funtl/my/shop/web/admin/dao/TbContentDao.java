package com.funtl.my.shop.web.admin.dao;

import com.funtl.my.shop.commons.persisten.BaseDao;
import com.funtl.my.shop.domain.TbContent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 内容管理
 * @author Zhan
 * @create 2019/8/22 - 17:07
 */
@Repository
public interface TbContentDao extends BaseDao<TbContent> {
    /**
     * 根据类目 ID 删除内容
     * @param categoryIds
     */
    void deleteByCategoryId(String[] categoryIds);
}
