package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.UserInfoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoRowMapper implements RowMapper<UserInfoDto> {
    @Override
    public UserInfoDto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setUserId(resultSet.getInt("id"));

        return userInfoDto;

    }
}
