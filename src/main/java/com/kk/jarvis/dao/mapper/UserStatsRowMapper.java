package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.UserStatsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserStatsRowMapper implements RowMapper<UserStatsDto> {
    @Override
    public UserStatsDto mapRow(ResultSet resultSet, int p_RowNum) throws SQLException
    {
        UserStatsDto userStatsDto = new UserStatsDto();
        userStatsDto.setUserId(resultSet.getInt("user_id"));
        userStatsDto.setKey(resultSet.getString("key"));
        userStatsDto.setValue(resultSet.getString("value"));
        return userStatsDto;
    }
}
