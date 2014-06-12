package com.kk.jarvis.dao.mapper;

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
public class UserGoalRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet p_Rs, int p_RowNum) throws SQLException
    {
        return p_Rs.getInt("goal_id");
    }
}
