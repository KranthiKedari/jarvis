package com.kk.jarvis.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/18/14
 * Time: 2:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoDto {
    private int userId;

    public UserInfoDto() {
        userData = new HashMap<String, Object>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Map<String, Object> userData;

    public Object getUserData(String key) {
        return userData.containsKey(key) ? userData.get(key) : null;
    }

    public void setUserData(String key, Object value) {
        userData.put(key, value);
    }

    public String getUserDataString(String key) {
        return userData.containsKey(key) ? (String) userData.get(key) : null;
    }

    public void setUserDataString(String key, String value) {
        userData.put(key, value);
    }

}
