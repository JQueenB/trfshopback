package com.jqueenb.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.jqueenb.common.Const;
import com.jqueenb.common.ResponseCode;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    /*
     * 创建订单
     * */
    @RequestMapping(value = "/create.do")
    public ServerResponse createOrder(HttpSession session,Integer shoppingId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.createOrder(userInfo.getId(),shoppingId);
    }
    /*
     * 取消订单
     * */
    @RequestMapping(value = "/cancel.do")
    public ServerResponse cancel(HttpSession session,Long odrderNo){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.cancel(userInfo.getId(),odrderNo);
    }
    /*
    * 获取购物车商品信息
    * */
    @RequestMapping(value = "/get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.get_order_cart_product(userInfo.getId());
    }
    /*
     * 订单list
     * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.list(userInfo.getId(),pageNum,pageSize);
    }
    /*
     * 订单详情
     * */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.detail(orderNo);
    }
    /*
     * 后台-订单发货
     * */
    @RequestMapping(value = "/send_goods.do")
    public ServerResponse send_goods(HttpSession session,Long orderNo){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        //非空判断
        if (userInfo == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
       //权限校验
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.createByError(ResponseCode.NO_PRIVILEGE.getCode(),
                    ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return orderService.send_goods(orderNo);
    }

    /*
    * 支付接口
    * */
    @RequestMapping(value = "/pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.pay(userInfo.getId(),orderNo);
    }
    /*
    * 支付宝服务器回调应用服务器接口
    * */
    @RequestMapping(value = "/alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){
        System.out.println("==支付宝服务器回调应用服务器接口==");
        Map<String,String[]> params=request.getParameterMap();
        Map<String,String> requestparams=Maps.newHashMap();
        Iterator<String> it=params.keySet().iterator();
        while (it.hasNext()){
            String key=it.next();
            String[] strArr=params.get(key);
            String value="";
            for(int i=0;i<strArr.length;i++){
                value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparams.put(key,value);
        }
        //stept1：支付宝验签
        try {
            requestparams.remove("sign_type");
            boolean result=AlipaySignature.rsaCheckV2(requestparams,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!result){
                return ServerResponse.createByError("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //处理业务逻辑
        return orderService.alipay_callback(requestparams);
    }
    /*
     * 查看订单状态接口
     * */
    @RequestMapping(value = "/query_order_pay_status.do")
    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.query_order_pay_status(orderNo);
    }
}
