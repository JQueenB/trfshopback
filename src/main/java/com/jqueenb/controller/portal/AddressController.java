package com.jqueenb.controller.portal;

import com.jqueenb.common.Const;
import com.jqueenb.common.ResponseCode;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.Shopping;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shipping")

public class AddressController {
    @Autowired
    IAddressService addressService;
    /*
    * 添加地址
    * */
    @RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session, Shopping shopping){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return addressService.add(userInfo.getId(),shopping);
    }
    /*
     * 删除地址
     * */
    @RequestMapping(value = "/del.do")
    public ServerResponse del(HttpSession session, Integer shoppingId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return addressService.del(userInfo.getId(),shoppingId);
    }
    /*
     * 登录状态更新地址
     * */
    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session, Shopping shopping){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        shopping.setUserId(userInfo.getId());
        return addressService.update(shopping);
    }
    /*
     * 选中查看具体的地址
     * */
    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session,Integer shoppingId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return addressService.select(shoppingId);
    }
    /*
     * 分页查询地址列表
     * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getDesc());
        }
        return addressService.list(pageNum,pageSize);
    }
}
