package com.kk.jarvis.dto;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserStatsDto {
    private int userId;

    private String key;

    public UserStatsDto() {
    }

    public UserStatsDto(int userId, String key, String value) {
        this.userId = userId;
        this.key = key;
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}