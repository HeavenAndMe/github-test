package com.funtl.my.shop.web.admin.web.controller;

import com.funtl.my.shop.commons.dto.BaseResult;
import com.funtl.my.shop.domain.TbContent;
import com.funtl.my.shop.domain.TbContentCategory;
import com.funtl.my.shop.web.admin.abstracts.AbstractBaseTreeController;
import com.funtl.my.shop.web.admin.abstracts.AbstractBaseTreeServiceImpl;
import com.funtl.my.shop.web.admin.service.TbContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 内容分类管理
 * @author Zhan
 * @create 2019/8/22 - 14:00
 */
@Controller
@RequestMapping(value = "content/category")
public class ContentCategoryController extends AbstractBaseTreeController<TbContentCategory, TbContentCategoryService> {

    @ModelAttribute
    public TbContentCategory getTbContentCategory(Long id){
        TbContentCategory tbContentCategory = null;

        // id 不为空，则从数据库获取
        if (id != null) {
            tbContentCategory = service.getById(id);
        } else {
            tbContentCategory = new TbContentCategory();
        }

        return tbContentCategory;
    }

    /**
     * 跳转分类列表页
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(Model model){
        List<TbContentCategory> targetList = new ArrayList<>();
        List<TbContentCategory> sourceList = service.selectAll();

        sortList(sourceList,targetList,0L);

        model.addAttribute("tbContentCategorys",targetList);
        return "content_category_list";
    }

    /**
     * 跳转分类表单页
     * @param tbContentCategory
     * @return
     */
    @Override
    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form(TbContentCategory tbContentCategory){
        return "content_category_form";
    }

    /**
     * 保存分类信息
     * @param tbContentCategory
     * @param model
     * @param redirectAttributes
     * @return
     */
    @Override
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String save(TbContentCategory tbContentCategory, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = service.save(tbContentCategory);

        if (baseResult.getStatus() == 200){
            redirectAttributes.addFlashAttribute("baseResult",baseResult);
            return "redirect:/content/category/list";
        }

        else {
            model.addAttribute("baseResult",baseResult);
            return form(tbContentCategory);
        }
    }

    /**
     * 树形结构
     * @param id
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "tree/data",method = RequestMethod.POST)
    public List<TbContentCategory> treeData(Long id){
        if (id == null){
            id = 0L;
        }
        return service.selectByPid(id);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            service.delete(Long.parseLong(ids));
            return BaseResult.success("删除分类及其子类及其全部内容成功");
        } else {
            return BaseResult.fail("删除分类失败");
        }
    }


}
