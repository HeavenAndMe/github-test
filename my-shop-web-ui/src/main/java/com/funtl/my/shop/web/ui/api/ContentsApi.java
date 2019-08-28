package com.funtl.my.shop.web.ui.api;

import com.funtl.my.shop.commons.utils.HttpClientUtils;
import com.funtl.my.shop.commons.utils.MapperUtils;
import com.funtl.my.shop.web.ui.dto.TbContent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhan
 * @create 2019/8/25 - 9:58
 */
public class ContentsApi {
    public static List<TbContent> ppt(){
        List<TbContent> tbContents = new ArrayList<>();
        String result = HttpClientUtils.doGet(API.API_CONTENTS);
        try {
            tbContents = MapperUtils.json2ByTree(result, "data", TbContent.class);
            for (TbContent tbContent : tbContents) {
                System.out.println(tbContent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbContents;
    }

}
