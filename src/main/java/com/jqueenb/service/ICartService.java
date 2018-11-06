package com.jqueenb.service;

import com.jqueenb.common.ServerResponse;

public interface ICartService {
    /*
    * 添加商品到购物车
    * */
    public ServerResponse add(Integer userId,Integer productId,Integer count);
    /*
    * 购物车列表
    * */
    ServerResponse list(Integer userId);
    /*
    * 更新购物车某个商品数量
    * */
    ServerResponse update(Integer userId,Integer productId,Integer count);
    /*
    移除购物车中某个商品
     */
    ServerResponse delete_product(Integer userId,String productIds);
    /*
    * 购物车选中某个商品
    * */
    ServerResponse select(Integer userId,Integer productId,Integer check);
    /*查询购物车中数量*/
    ServerResponse get_cart_product_count(Integer userId);
}
