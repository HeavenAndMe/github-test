package com.funtl.my.shop.web.admin.web.controller;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件上传控制器
 *
 * @author Zhan
 * @create 2019/8/23 - 8:13
 */
@Controller
public class UploadController {

    public static final String UPLOAD_PATH = "/static/upload/";

    /**
     * 文件上传
     * @param dropFile
     * @param editorFile
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile dropFile, MultipartFile[] editorFile, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // Dropzone 上传
        if (dropFile != null){
            result.put("fileName",writeFile(dropFile,request));
        }

        // wangEditor 上传
        if (editorFile != null && editorFile.length > 0){
            List<String> fileNames = new ArrayList<>();

            for (MultipartFile multipartFile : editorFile) {
                fileNames.add(writeFile(multipartFile, request));
            }

            result.put("errno", 0);
            result.put("data",fileNames);
        }
        return result;
    }

    /**
     * 文件写入
     * @param multipartFile
     * @param request
     * @return
     */
    public String writeFile(MultipartFile multipartFile, HttpServletRequest request){
        //获取文件后缀
        String fileName = multipartFile.getOriginalFilename();
        String fileFix = fileName.substring(fileName.lastIndexOf("."));

        //文件存放路径
        String filePath = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
        //判断路径是否存在，不存在则创建文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }

        //将文件写入目标文件夹
        file = new File(filePath, UUID.randomUUID() + fileFix);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * scheme：服务端提供的协议 http/https
         * serverName：服务器名称 localhost/ip/domain(域名)
         * serverPort：服务器端口
         */
        String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return serverPath + UPLOAD_PATH + file.getName();
    }
}
