package com.kk.jarvis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataSearchDto {
    private int userId;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String subCategory;

    private int startDate;
    private int endDate;
    private Map<String,String> searchParams;

    public UserDataSearchDto() {

    }

    @JsonCreator
    public UserDataSearchDto(
            @JsonProperty("userId") int userId,
            @JsonProperty("name") String name,
            @JsonProperty("category") String category,
            @JsonProperty("subCategory") String subCategory,
            @JsonProperty("startTime") int startDate,
            @JsonProperty("endTime") int endDate,
            @JsonProperty("searchParams") Map<String,String> searchParams

    )                   {
        this.userId = userId;
        this.category =category;
        this.subCategory =subCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchParams = searchParams;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public Map<String, String> getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Map<String, String> searchParams) {
        this.searchParams = searchParams;
    }

    public String getSearchType() {
        String type = this.searchParams != null && this.searchParams.containsKey("type")
                ? searchParams.get("type") : "progression";
        return type;
    }

}
