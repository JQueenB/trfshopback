package com.jqueenb.controller.portal;

import com.jqueenb.common.Const;
import com.jqueenb.common.ResponseCode;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    ICartService cartService;
    /*
    * 购物车中添加商品
    * */
    @RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(userInfo.getId(),productId,count);
    }
    /*
     * 购物车列表
     * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(userInfo.getId());
    }
    /*
     * 更新购物车某个商品数量
     * */
    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(userInfo.getId(),productId,count);
    }
    /*
     * 移除购物车某个商品
     * */
    @RequestMapping(value = "/delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.delete_product(userInfo.getId(),productIds);
    }
    /*
     * 购物车选中某个商品
     * */
    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.select(userInfo.getId(),productId,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }
    /*
     * 购物车取消选中某个商品
     * */
    @RequestMapping(value = "/un_select.do")
    public ServerResponse un_select(HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.select(userInfo.getId(),productId,Const.CartCheckedEnum.PRODUCT_UNCHECKED.getCode());
    }
    /*
     * 购物车全选
     * */
    @RequestMapping(value = "/select_all.do")
    public ServerResponse select_all(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.select(userInfo.getId(),null,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }
    /*
     * 取消全选
     * */
    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.select(userInfo.getId(),null,Const.CartCheckedEnum.PRODUCT_UNCHECKED.getCode());
    }
    /*
     * 查询在购物车中的产品数量
     * */
    @RequestMapping(value = "/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.get_cart_product_count(userInfo.getId());
    }
}
