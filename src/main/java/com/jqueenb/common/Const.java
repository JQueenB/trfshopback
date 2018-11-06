package com.jqueenb.common;

public class Const {
    public static final String CURRENTUSER="current_user";
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
}
