package com.jqueenb.controller;

import com.jqueenb.pojo.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/user")
public class UserCon {
    @RequestMapping(value="/login.co")
    @ResponseBody
    public UserInfo u(){
        UserInfo userlogin=new UserInfo();
        userlogin.setId(1);
        userlogin.setUsername("baijingkun");
        return userlogin;
    }
}
