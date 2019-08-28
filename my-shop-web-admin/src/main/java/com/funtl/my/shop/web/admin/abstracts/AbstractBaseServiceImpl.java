package com.funtl.my.shop.web.admin.abstracts;

import com.funtl.my.shop.commons.dto.PageInfo;
import com.funtl.my.shop.commons.persisten.BaseDao;
import com.funtl.my.shop.commons.persisten.BaseEntity;
import com.funtl.my.shop.commons.persisten.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共业务实现抽象类
 * @author Zhan
 * @create 2019/8/24 - 8:02
 */
public abstract class AbstractBaseServiceImpl<T extends BaseEntity, D extends BaseDao<T>> implements BaseService<T> {

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
    public void delete(Long id) {
        dao.delete(id);
    }

    /**
     * 根据 ID 获取
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
    public void update(T entity) {
        dao.update(entity);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        dao.deleteMulti(ids);
    }

    /**
     * 获取总笔数
     * @param entity
     * @return
     */
    @Override
    public int count(T entity) {
        return dao.count(entity);
    }

    @Override
    public PageInfo<T> page(int start, int length, int draw, T entity) {
        int count = count(entity);

        Map<String ,Object> params = new HashMap<>();
        params.put("start",start);
        params.put("length",length);
        params.put("pageParams",entity);

        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(dao.page(params));

        return pageInfo;
    }
}
