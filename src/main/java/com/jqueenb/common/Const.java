package com.jqueenb.common;

public class Const {
    public static final String CURRENTUSER="current_user";
    public static final String TRADE_SUCCESS="TRADE_SUCCESS";




    public enum RoleEnum{
        ROLE_ADMIN(0,"管理员"),
        ROLE_CUSTOMER(1,"普通用户")
        ;
        private int code;
        private String desc;
        private RoleEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
    public enum CategoryEnum{
        CATEGORY_D(0,"有效"),
        CATEGORY_I(1,"失效")
        ;
        private int code;
        private String desc;
        private CategoryEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
    public enum ProductStatusEnum {
        PRODUCT_ONLINE(1, "在售"),
        PRODUCT_OFFLINE(2, "下架"),
        PRODUCT_DELETE(3,"删除")
        ;
        private int code;
        private String desc;
        private ProductStatusEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public enum CartCheckedEnum{
        PRODUCT_CHECKED(1,"已勾选"),
        PRODUCT_UNCHECKED(0,"未勾选")
        ;
        private int code;
        private String desc;
        private CartCheckedEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum OrderStatusEnum{
        Order_CANCELED(0,"已取消"),
        Order_UN_PAY(10,"未付款"),
        Order_PAYDE(20,"已付款"),
        Order_SEND(40,"已发货"),
        Order_SUCCESS(50,"交易成功"),
        Order_CLOSED(60,"交易关闭")
        ;
        private int code;
        private String desc;
        private OrderStatusEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }
        public String getDesc() {
            return desc;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static OrderStatusEnum codeOf(Integer code){
            for(OrderStatusEnum orderStatusEnum:values()){
                if(code==orderStatusEnum.getCode()){
                    return orderStatusEnum;
                }
            }
            return null;
        }
    }

    public enum PaymentEnum{
        PAY_ONLINE(1,"线上支付")
        ;
        private int code;
        private String desc;
        private PaymentEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }
        public String getDesc() {
            return desc;
        }
        public void setCode(int code) {
            this.code = code;
        }

        public static PaymentEnum codeOf(Integer code){
            for(PaymentEnum paymentEnum:values()){
                if(code==paymentEnum.getCode()){
                    return paymentEnum;
                }
            }
            return null;
        }

    }


    public enum PaymentPlatformEnum{
        ALIPAY(1,"支付宝")
        ;
        private int code;
        private String desc;
        private PaymentPlatformEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }
        public String getDesc() {
            return desc;
        }
        public void setCode(int code) {
            this.code = code;
        }
    }
}
