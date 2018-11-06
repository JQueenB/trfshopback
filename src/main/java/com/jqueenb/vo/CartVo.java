package com.jqueenb.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/*
* 购物车返回前端实体类
* */
public class CartVo implements Serializable {
    //购物信息集合
    private List<CartProductVo> cartProductVoList;
    //是否全选
    private boolean isallchecked;
    //总价格
    private BigDecimal carttotalprice;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isIsallchecked() {
        return isallchecked;
    }

    public void setIsallchecked(boolean isallchecked) {
        this.isallchecked = isallchecked;
    }

    public BigDecimal getCarttotalprice() {
        return carttotalprice;
    }

    public void setCarttotalprice(BigDecimal carttotalprice) {
        this.carttotalprice = carttotalprice;
    }
}
