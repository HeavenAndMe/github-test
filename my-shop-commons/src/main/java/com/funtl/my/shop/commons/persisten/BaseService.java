package com.funtl.my.shop.commons.persisten;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.dto.PageInfo;

import java.util.List;

/**
 * 所有业务逻辑层的基类
 * @author Zhan
 * @create 2019/8/23 - 15:45
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 查询所有
     * @return
     */
    public List<T> selectAll();

    /**
     * 保存
     * @param entity
     * @return
     */
    BaseResult save(T entity);

    /**
     * 根据 ID 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据 ID 获取
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 更新
     * @param entity
     */
    void update(T entity);

    /**
     * 批量删除
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param start
     * @param length
     * @param draw
     * @param entity
     * @return
     */
    PageInfo<T> page(int start, int length, int draw, T entity);

    /**T
     * 查询总笔数
     * @param entity
     * @return
     */
    int count(T entity);
}
