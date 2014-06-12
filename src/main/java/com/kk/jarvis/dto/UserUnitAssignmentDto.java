package com.kk.jarvis.dto;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 6/9/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserUnitAssignmentDto {


    private String unit;
    private String category;
    private String subCategory;
    private int userId;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
