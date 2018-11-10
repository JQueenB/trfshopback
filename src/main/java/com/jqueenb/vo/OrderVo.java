package com.jqueenb.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderVo implements Serializable {
    private Long orderNo;//": 1485158676346,
    private BigDecimal payment;//": 2999.11,
    private Integer paymentType;//": 1,
    private String paymentTypeDesc;//": "在线支付",
    private Integer postage;//": 0,
    private Integer status;//": 10,
    private String statusDesc;//": "未支付",
    private String paymentTime;//": "2017-02-11 12:27:18",
    private String sendTime;//": "2017-02-11 12:27:18",
    private String endTime;//": "2017-02-11 12:27:18",
    private String closeTime;//": "2017-02-11 12:27:18",
    private String createTime;//": "2017-01-23 16:04:36",
    private List<OrderItemVo> orderItemVoList;
    private String imageHost;//": "http://img.business.com/",
    private Integer shoppingId;//": 3,
    private String receiverName;//": "betty",
    private ShoppingVo shoppingVo;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public Integer getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Integer shoppingId) {
        this.shoppingId = shoppingId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public ShoppingVo getShoppingVo() {
        return shoppingVo;
    }

    public void setShoppingVo(ShoppingVo shoppingVo) {
        this.shoppingVo = shoppingVo;
    }
}
