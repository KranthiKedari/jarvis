package com.kk.jarvis.dto;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/17/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryDto {
    private String category;
    private String subCategory;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    public CategoryDto() {

    }
    public CategoryDto(int userId, String category, String subCategory) {
        this.category = category;
        this.subCategory = subCategory;
        this.userId = userId;
    }


    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
