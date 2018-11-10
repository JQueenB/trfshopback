package com.jqueenb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.dao.ShoppingMapper;
import com.jqueenb.pojo.Shopping;
import com.jqueenb.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    ShoppingMapper shoppingMapper;
    @Override
    public ServerResponse add(Integer usrId, Shopping shopping) {
        //step1:参数校验
        if(shopping==null){
            return ServerResponse.createByError("参数错误");
        }
        //step2:添加
        shopping.setUserId(usrId);
        shoppingMapper.insert(shopping);
        //step3:返回结果
        Map<String,Integer> map=Maps.newHashMap();
        map.put("shoppingId",shopping.getId());
        return ServerResponse.createBySuccess(map);
    }

    @Override
    public ServerResponse del(Integer userId, Integer shoppingId) {
        //step1:参数校验
        if(shoppingId==null){
            return ServerResponse.createByError("参数错误");
        }
        //step2:删除
        int result=shoppingMapper.deleteByUserIdAndShopping(userId,shoppingId);
        //step3:返回结果
        if(result>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError("删除失败");
    }

    @Override
    public ServerResponse update(Shopping shopping) {
        //step1:参数校验
        if(shopping==null){
            return ServerResponse.createByError("参数错误");
        }
        //step2:更新
        int result=shoppingMapper.updateSelectiveKey(shopping);
        //step3:返回结果
        if(result>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError("更新失败");
    }

    @Override
    public ServerResponse select(Integer shoppingId) {
        //step1:参数校验
        if(shoppingId==null){
            return ServerResponse.createByError("参数错误");
        }
        //step2:查看
        Shopping shopping=shoppingMapper.selectByPrimaryKey(shoppingId);
        //step3:返回结果
        return ServerResponse.createBySuccess(shopping);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shopping> shoppingList=shoppingMapper.selectAll();
        PageInfo pageInfo=new PageInfo(shoppingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
