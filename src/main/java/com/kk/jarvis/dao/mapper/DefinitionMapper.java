package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.DefinitionDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefinitionMapper implements RowMapper<DefinitionDto> {
    @Override
    public DefinitionDto mapRow(ResultSet resultSet, int p_RowNum) throws SQLException
    {
        DefinitionDto definitionDto = new DefinitionDto();
        definitionDto.setUserId(resultSet.getInt("user_id"));
        definitionDto.setKey(resultSet.getString("key"));
        definitionDto.setValue(resultSet.getString("value"));
        return definitionDto;
    }
}
