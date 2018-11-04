package com.jqueenb.controller.backend;

import com.jqueenb.common.Const;
import com.jqueenb.common.ResponseCode;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.Product;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
@RestController
@RequestMapping(value = "/manage/product")
public class ProductManageController {
    @Autowired
    IProductServer productServer;

    /*
    * 获取或更新商品
    * */
    @RequestMapping(value = "saveOrUpadate.do")
    public ServerResponse saveOrUpadate(HttpSession session, Product product){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return productServer.saveOrUpdate(product);
    }
    /*
    * 产品上下架
    * */
    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session, Integer productId,Integer status){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return productServer.set_sale_status(productId,status);
    }
    /*
    * 查看商品详情
    * */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return productServer.detail(productId);
    }
    /*
     * 查看商品列表
     * */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return productServer.list(pageNum,pageSize);
    }
    /*
     * 搜索商品
     * */
    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,
                               @RequestParam(value = "productId",required = false) Integer productId,
                               @RequestParam(value = "productName",required = false)String productName,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return productServer.search(productId,productName,pageNum,pageSize);
    }
}
