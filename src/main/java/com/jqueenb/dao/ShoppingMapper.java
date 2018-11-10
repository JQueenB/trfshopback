package com.jqueenb.dao;

import com.jqueenb.pojo.Shopping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_shopping
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_shopping
     *
     * @mbg.generated
     */
    int insert(Shopping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_shopping
     *
     * @mbg.generated
     */
    Shopping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_shopping
     *
     * @mbg.generated
     */
    List<Shopping> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_shopping
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Shopping record);

    int updateSelectiveKey(Shopping shopping);

    /*
    * 删除地址
    * */
    int deleteByUserIdAndShopping(@Param("userId") Integer userId,
                                  @Param("shoppingId") Integer shoppingId);
}