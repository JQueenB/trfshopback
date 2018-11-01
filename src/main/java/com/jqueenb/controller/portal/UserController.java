package com.jqueenb.controller.portal;

import com.jqueenb.common.Const;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;

    /*
     * 用户登录
     * */
    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession session,String username, String password) {
        ServerResponse serverResponse = userService.login(username, password);
        if (serverResponse.isSuccess()) {
            UserInfo userInfo = (UserInfo) serverResponse.getData();
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }
    /*
    * 用户注册
    * */
    @RequestMapping(value = "/register.do")
    public ServerResponse register(UserInfo userInfo) {
        ServerResponse serverResponse = userService.register(userInfo);

        return serverResponse;
    }
    /*
    * 忘记密码
    * */
    //根据用户名查询密保问题
    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        ServerResponse serverResponse=userService.forget_get_question(username);
        return serverResponse;
    }
    //提交问题答案
    @RequestMapping(value = "/forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        ServerResponse serverResponse=userService.forget_check_answer(username,question,answer);
        return serverResponse;
    }
    //重置密码
    @RequestMapping(value = "/forget_reset_password")
    public ServerResponse forget_reset_password(String username,String password,String forgetToken){
        ServerResponse serverResponse=userService.forget_reset_password(username,password,forgetToken);
        return serverResponse;
    }
    /*
    * 检查用户名
    * */
    @RequestMapping(value = "/check_valid")
    public ServerResponse check_valid(String str,String type){
        ServerResponse serverResponse=userService.check_valid(str,type);
        return serverResponse;
    }
    /*
    * 获取登录用户信息
    * */
    @RequestMapping(value = "/get_user_info")
    public ServerResponse get_user_info(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError("用户未登录");
        }
        UserInfo newUserInfo=new UserInfo();
        newUserInfo.setId(userInfo.getId());
        newUserInfo.setUsername(userInfo.getUsername());
        newUserInfo.setEmail(userInfo.getEmail());
        newUserInfo.setPhone(userInfo.getPhone());
        newUserInfo.setCreateTime(userInfo.getCreateTime());
        newUserInfo.setUpdateTime(userInfo.getUpdateTime());
        return ServerResponse.createBySuccess(userInfo);
    }
    /*
    * 登录状态重置密码
    * */
    @RequestMapping(value = "reset_password")
    public ServerResponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError("用户未登录");
        }
        return userService.reset_password(userInfo.getUsername(),passwordOld,passwordNew);
    }
    /*
    * 登录状态更新个人信息
    * */
    @RequestMapping(value ="/update_information" )
    public ServerResponse update_information(HttpSession session,UserInfo user){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError("用户未登录");
        }
        user.setId(userInfo.getId());
        ServerResponse serverResponse=userService.update_information(user);
        if(serverResponse.isSuccess()){
            //更新session中用户信息
            UserInfo userInfo1=userService.findUserInfoByUserid(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,userInfo1);
        }
        return serverResponse;
    }
    /*
     * 获取登录用户详细信息
     * */
    @RequestMapping(value = "/get_information")
    public ServerResponse get_information(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.createByError("用户未登录");
        }
        userInfo.setPassword("");
        return ServerResponse.createBySuccess(userInfo);
    }
    /*
    * 退出登录
    * */
    @RequestMapping(value = "/logout")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createBySuccess();
    }
}
