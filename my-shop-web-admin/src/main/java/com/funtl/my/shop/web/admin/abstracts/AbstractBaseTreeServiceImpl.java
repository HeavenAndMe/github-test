package com.funtl.my.shop.web.admin.abstracts;

import com.funtl.my.shop.commons.persisten.BaseEntity;
import com.funtl.my.shop.commons.persisten.BaseTreeDao;
import com.funtl.my.shop.commons.persisten.BaseTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 树形结构业务实现抽象类
 * @author Zhan
 * @create 2019/8/24 - 7:17
 */
@Transactional(readOnly = true)
public abstract class AbstractBaseTreeServiceImpl<T extends BaseEntity,D extends BaseTreeDao<T>> implements BaseTreeService<T> {

    @Autowired
    protected D dao;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> selectAll() {
        return dao.selectAll();
    }

    /**
     * 删除
     * @param id
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        dao.delete(new String[]{String.valueOf(id)});
    }

    /**
     * 获取
     * @param id
     * @return
     */
    @Override
    public T getById(Long id) {
        return dao.getById(id);
    }

    /**
     * 修改
     * @param entity
     */
    @Override
    @Transactional(readOnly = false)
    public void update(T entity) {
        dao.update(entity);
    }

    /**
     * 根据父节点 ID 查询
     * @param pid
     * @return
     */
    @Override
    public List<T> selectByPid(Long pid) {
        return dao.selectByPid(pid);
    }
}
