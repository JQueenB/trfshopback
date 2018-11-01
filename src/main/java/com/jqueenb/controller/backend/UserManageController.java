package com.jqueenb.controller.backend;

import com.jqueenb.common.Const;
import com.jqueenb.common.ResponseCode;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
* 后台用户控制器类
* */
@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {
    @Autowired
    IUserService userService;
    /*
    * 管理员登录
    * */
    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession session, String username, String password) {
        ServerResponse serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            UserInfo userInfo = (UserInfo) serverResponse.getData();
            if(userInfo.getRole()==Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                return ServerResponse.createByError("无权限登录");
            }
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }
}
