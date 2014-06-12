package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserStatsRowMapper;
import com.kk.jarvis.dto.UserStatsDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserStatsDao {

    private JdbcTemplate jdbcTemplate;

    public UserStatsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserStatsDto> getUserStats(int userId)
    {
        List<UserStatsDto> userStats = jdbcTemplate.query("select * from user_current_stats where user_id = ?",
                new Object[]{userId},
                new UserStatsRowMapper());

        return userStats;
    }

    public List<UserStatsDto> setUserStats(List<UserStatsDto> userStats)
    {
        String sqlInsertQuery = " insert into user_current_stats(`value`, `user_id`,`key`) values (?,?,?)";
        String sqlUpdateQuery = "update user_current_stats set `value` = ? where `user_id`=? and `key` =?";
        for(UserStatsDto userStatsDto : userStats) {
            int result =  jdbcTemplate.update(sqlUpdateQuery,
                    new Object[]{userStatsDto.getValue() , userStatsDto.getUserId(), userStatsDto.getKey()});

            if(result ==0 ) {
                jdbcTemplate.update(sqlInsertQuery,
                        new Object[]{userStatsDto.getValue() , userStatsDto.getUserId(), userStatsDto.getKey()});
            }
        }


        return userStats;
    }
}
