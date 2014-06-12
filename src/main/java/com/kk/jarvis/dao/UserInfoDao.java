package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserInfoRowMapper;
import com.kk.jarvis.dao.mapper.UserStatsRowMapper;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.dto.UserStatsDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoDao extends JarvisDao{

    private JdbcTemplate jdbcTemplate;

    public UserInfoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserInfoDto getUserInfo(String uname, String pwd)
    {
        List<UserInfoDto> userData = null;
        UserInfoDto userInfoDto =null;

        userData = jdbcTemplate.query("select * from user_details where username = ? and pwd = ?",
                new Object[]{uname, pwd},
                new UserInfoRowMapper());

        if(userData != null && userData.size() > 0) {
            userInfoDto = userData.get(0);
            List<UserStatsDto> userStats = jdbcTemplate.query("select * from user_current_stats where user_id = ?",
                    new Object[]{userInfoDto.getUserId()},
                    new UserStatsRowMapper());

            for(UserStatsDto userStatsDto: userStats) {
                userInfoDto.setUserData(userStatsDto.getKey(), userStatsDto.getValue());
            }
        }

        return userInfoDto;
    }
}
