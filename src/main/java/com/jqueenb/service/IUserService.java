package com.jqueenb.service;

import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.UserInfo;

public interface IUserService {
    /*
    * 用户登录
    * */
    ServerResponse login(String username,String password);
    /*
    * 用户注册
    * */
    ServerResponse register(UserInfo userInfo);
    /*
    * 忘记密码
    * */
    //根据用户名查询密保问题
    ServerResponse forget_get_question(String username);
    //检查问题的答案
    ServerResponse forget_check_answer(String username,String question,String answer);
    //修改密码
    ServerResponse forget_reset_password(String username,String password,String forgetToken);
    /*
    * 校验用户名或邮箱是否有效
    * */
    ServerResponse check_valid(String str,String type);
    /*
    * 登录中状态重置密码
    * */
    ServerResponse reset_password(String username,String passwordOld,String passwordNew);
    /*
    * 登录状态下更新个人信息
    * */
    ServerResponse update_information(UserInfo user);
    /*
    * 根据userid查询用户信息
    * */
    UserInfo findUserInfoByUserid(Integer userId);
}
