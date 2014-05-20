package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.UserDataDto;
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
public class UserDataRowMapper implements RowMapper<UserDataDto> {
    @Override
    public UserDataDto mapRow(ResultSet p_Rs, int p_RowNum) throws SQLException
    {
        UserDataDto l_Result = new UserDataDto();

        String name = p_Rs.getString("name");
        String value = p_Rs.getString("value");

        l_Result.setName(name);
        l_Result.setValue(value);

        return l_Result;

    }
}
