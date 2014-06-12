package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.UserGoalDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/13/14
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGoalDataRowMapper implements RowMapper<UserGoalDto> {
    @Override
    public UserGoalDto mapRow(ResultSet p_Rs, int p_RowNum) throws SQLException
    {
        UserGoalDto userGoalDto = new UserGoalDto();
        userGoalDto.setName(p_Rs.getString("name"));
        userGoalDto.setValue(p_Rs.getString("value"));

        userGoalDto.setType(p_Rs.getString("goal_type"));
        userGoalDto.setInterval(p_Rs.getInt("interval"));

        return userGoalDto;
    }
}
