package com.jqueenb.controller.portal;

import com.jqueenb.common.ServerResponse;
import com.jqueenb.service.IProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    IProductServer productServer;
    /*
    * 商品详情
    * */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Integer productId){
        return productServer.detail_portal(productId);
    }
    /*
    * 前台搜索商品并排序
    * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(@RequestParam(required = false) Integer categoryId,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false,defaultValue = "") String orderBy){

        return productServer.list_portal(categoryId,keyword,pageNum,pageSize,orderBy);
    }
}
