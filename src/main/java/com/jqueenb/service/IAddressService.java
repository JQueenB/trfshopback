package com.jqueenb.service;

import com.jqueenb.common.ServerResponse;
import com.jqueenb.pojo.Shopping;

public interface IAddressService {
    /*
    * 添加地址
    * */
    public ServerResponse add(Integer usrId, Shopping shopping);
    /*
    * 删除地址
    * */
    ServerResponse del(Integer userId,Integer shoppingId);
    /*
    * 更新
    * */
    ServerResponse update(Shopping shopping);
    /*
    * 查看
    * */
    ServerResponse select(Integer shoppingId);
    /*
    * 分页查询地址列表
    * */
    ServerResponse list(Integer pageNum,Integer pageSize);
}
