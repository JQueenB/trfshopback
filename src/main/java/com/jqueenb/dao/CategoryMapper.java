package com.jqueenb.dao;

import com.jqueenb.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_category
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_category
     *
     * @mbg.generated
     */
    int insert(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_category
     *
     * @mbg.generated
     */
    Category selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_category
     *
     * @mbg.generated
     */
    List<Category> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jqueenb_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Category record);
    /*
    * 查询子类（平级）
    * */
    List<Category> findChildCategory(Integer categoryId);
    /*
    * 根据品类parentid和categoryName查询信息
    * */
    Category findByParentIdAndCategoryName(@Param("parentId") Integer parentId,
                                           @Param("categoryName") String categoryName);
}