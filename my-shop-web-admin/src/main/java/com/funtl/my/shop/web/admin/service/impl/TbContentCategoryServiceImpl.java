package com.funtl.my.shop.web.admin.service.impl;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.commons.dto.PageInfo;
import com.funtl.my.shop.commons.validator.BeanValidator;
import com.funtl.my.shop.domain.TbContent;
import com.funtl.my.shop.domain.TbContentCategory;
import com.funtl.my.shop.web.admin.abstracts.AbstractBaseTreeServiceImpl;
import com.funtl.my.shop.web.admin.dao.TbContentCategoryDao;
import com.funtl.my.shop.web.admin.service.TbContentCategoryService;
import com.funtl.my.shop.web.admin.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhan
 * @create 2019/8/22 - 13:59
 */
@Service
@Transactional(readOnly = true)
public class TbContentCategoryServiceImpl extends AbstractBaseTreeServiceImpl<TbContentCategory,TbContentCategoryDao> implements TbContentCategoryService {

    @Autowired
    private TbContentService tbContentService;

    @Override
    @Transactional(readOnly = false)
    public BaseResult save(TbContentCategory entity) {
        String validator = BeanValidator.validator(entity);
        if (validator != null){
            return BaseResult.fail(validator);
        }

        else {
            TbContentCategory parent = entity.getParent();
            //如果没有选择节点则默认设置为根目录
            if (parent == null || parent.getId() == null){
                //根目录
                parent.setId(0L);
                //根目录一定为父级目录
                entity.setIsParent(true);
            }

            if(parent.getId() == entity.getId()) {
                return BaseResult.fail("不可选择本目录，请重新选择");
            }

            entity.setUpdated(new Date());
            //新增
            if (entity.getId() == null){
                entity.setCreated(new Date());
                entity.setIsParent(false);

                //判断当前新增的节点有没有父级节点
                if (parent.getId() != 0){
                    TbContentCategory currentCategoryParent = getById(parent.getId());
                    if (currentCategoryParent != null){
                        //为父节点设置 isParent 为true
                        currentCategoryParent.setIsParent(true);
                        update(currentCategoryParent);
                    }
                }
                dao.insert(entity);
            }

            //修改
            else {
                update(entity);
            }

            return BaseResult.success("保存分类信息成功!");
        }
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id){
        List<String> targetArray = new ArrayList<>();
        findAllChlid(targetArray,id);

        String[] categoryIds = targetArray.toArray(new String[targetArray.size()]);

        //删除类目及其子类目
        dao.delete(categoryIds);

        //删除类目下的所有内容
        tbContentService.deleteByCategoryId(categoryIds);
    }

    /**
     * 根据 ID 查找其下所有子类
     * @param targetList
     * @param parentId
     */
    public void findAllChlid(List<String> targetList, Long parentId){
        targetList.add(String.valueOf(parentId));

        List<TbContentCategory> tbContentCategories = selectByPid(parentId);
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            findAllChlid(targetList, tbContentCategory.getId());
        }
    }
}
