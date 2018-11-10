package com.jqueenb.controller.backend;

import com.jqueenb.common.ServerResponse;
import com.jqueenb.service.IProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/manage/product")
public class UploadController {
    @Autowired
    IProductServer productServer;
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";//逻辑视图  前缀+逻辑视图+后缀 --》/WEB-INF/jsp/upload.jsp
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload1(@RequestParam(value = "upload_file",required = false)MultipartFile file){
        String path="/ftpfile/img";
        return productServer.upload(file,path);//逻辑视图  前缀+逻辑视图+后缀 --》/WEB-INF/jsp/upload.jsp
    }
}
