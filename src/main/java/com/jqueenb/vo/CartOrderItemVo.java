package com.jqueenb.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartOrderItemVo {
    private List<OrderItemVo> orderItemVoList;
    private String imageHost;
    private BigDecimal tobalPrice;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getTobalPrice() {
        return tobalPrice;
    }

    public void setTobalPrice(BigDecimal tobalPrice) {
        this.tobalPrice = tobalPrice;
    }
}
