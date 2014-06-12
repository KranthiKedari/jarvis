package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserUnitMappingRowMapper;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.dto.UserUnitAssignmentDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 6/9/14
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserUnitAssignmentDao extends JarvisDao {
    private JdbcTemplate jdbcTemplate;

    public UserUnitAssignmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean assignUnit(UserUnitAssignmentDto userUnitAssignmentDto) {
        String sqlQuery = "update user_unit_mappings set category =?, sub_category = ? where user_id=? and unit =?";
        List<Object> args = new ArrayList<>();

        args.add(userUnitAssignmentDto.getCategory());
        args.add(userUnitAssignmentDto.getSubCategory());
        args.add(userUnitAssignmentDto.getUserId());
        args.add(userUnitAssignmentDto.getUnit());

        int result = jdbcTemplate.update(sqlQuery, args.toArray());
        int selectResult = 0;

        if(result == 1) {
            return true;
        }
         sqlQuery = "insert into user_unit_mappings(`category`,`sub_category`,`user_id`,`unit`) values(?,?,?,?)";

         selectResult = jdbcTemplate.update(sqlQuery,
                    args.toArray());


         if(selectResult == 1) {
            return true;
         }

        return false;
    }


    public Map<String, UserUnitAssignmentDto> getUserAssignments(UserInfoDto userInfoDto) {
        Map<String, UserUnitAssignmentDto> response = new HashMap<>();

        List<UserUnitAssignmentDto> assignments = jdbcTemplate.query("select * from user_unit_mappings where user_id= ?",
                    new Object[]{userInfoDto.getUserId()},
                    new UserUnitMappingRowMapper()
                );


        for(UserUnitAssignmentDto userUnitAssignmentDto: assignments) {
            response.put(userUnitAssignmentDto.getUnit(), userUnitAssignmentDto);
        }
        return response;
    }
}
