package com.funtl.my.shop.commons.persisten;

import com.funtl.my.shop.commons.dto.BaseResult;

import java.util.List;

/**
 * 通用树形结构接口
 * @author Zhan
 * @create 2019/8/24 - 7:08
 */
public interface BaseTreeService<T extends BaseEntity> {
    /**
     * 查询所有信息
     * @return
     */
    List<T> selectAll();

    /**
     * 新增
     * @param entity
     */
    BaseResult save(T entity);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据 ID 查询信息
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
     * 根据父节点 ID 查询所有子节点
     * @param pid
     * @return
     */
    List<T> selectByPid(Long pid);
}
