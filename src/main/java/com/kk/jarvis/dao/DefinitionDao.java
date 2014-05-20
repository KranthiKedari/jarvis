package com.kk.jarvis.dao;

import com.kk.jarvis.dto.DefinitionDto;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefinitionDao extends JarvisDao {
    private JdbcTemplate jdbcTemplate;

    public DefinitionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DefinitionDto addDefinition(DefinitionDto definitionDto) {
        //try to do a update
        String sqlQuery = "update user_definitions set `value` = ? where `key` = ? and `user_id` = ?";
        int result = jdbcTemplate.update(sqlQuery,
                new Object[]{definitionDto.getValue(), definitionDto.getKey(),definitionDto.getUserId()});

        if(result == 1) {
            return definitionDto;
        }

        sqlQuery = "insert into user_definitions(`user_id`,`key`,`value`) values(?,?,?)";

        result = jdbcTemplate.update(sqlQuery,
                new Object[]{definitionDto.getUserId(),definitionDto.getKey(), definitionDto.getValue()}
        );

        if(result ==1) {
            return definitionDto;
        }
        return null;
    }
}
