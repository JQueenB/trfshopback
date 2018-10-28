package com.jqueenb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/user")
public class UserCon {
    @RequestMapping(value="/login.co")
    @ResponseBody
    public int u(){
        int a=9;
        return a;
    }
}
