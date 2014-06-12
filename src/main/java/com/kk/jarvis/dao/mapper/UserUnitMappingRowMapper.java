package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.UserUnitAssignmentDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 6/9/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserUnitMappingRowMapper implements RowMapper<UserUnitAssignmentDto> {
    @Override
    public UserUnitAssignmentDto mapRow(ResultSet resultSet, int p_RowNum) throws SQLException
    {
        UserUnitAssignmentDto userUnitAssignmentDto = new UserUnitAssignmentDto();
        userUnitAssignmentDto.setUserId(resultSet.getInt("user_id"));
        userUnitAssignmentDto.setUnit(resultSet.getString("unit"));
        userUnitAssignmentDto.setCategory(resultSet.getString("category"));
        userUnitAssignmentDto.setSubCategory(resultSet.getString("sub_category"));

        return userUnitAssignmentDto;
    }


}
