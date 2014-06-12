package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserGoalDataRowMapper;
import com.kk.jarvis.dto.UserDataDto;
import com.kk.jarvis.dto.UserDataSearchDto;
import com.kk.jarvis.dto.UserGoalDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/13/14
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGoalDao extends JarvisDao{

    private JdbcTemplate jdbcTemplate;

    public UserGoalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public String addUserGoal(UserGoalDto userGoalDto)
    {
        String sqlQuery = "update user_goals set `value` = ?, `unit` = ?, `interval` =? where goal_type = ? and category = ? and sub_category =? and user_id = ? and name = ? ";


        List<UserDataDto> userData = null;
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(userGoalDto.getValue());
        args.add(userGoalDto.getUnit());
        args.add(userGoalDto.getInterval());
        args.add(userGoalDto.getType());
        args.add(userGoalDto.getCategory());
        args.add(userGoalDto.getSubCategory());
        args.add(userGoalDto.getUserId());
        args.add(userGoalDto.getName());


        int result = jdbcTemplate.update(sqlQuery, args.toArray());

        if(result > 0) {
            return userGoalDto.toString();
        }


        int insertResult = 0;
        if(result == 0) {
            sqlQuery = "insert into user_goals(`value`,`unit`,`interval`,`goal_type`,`category`,`sub_category`,`user_id`,`name`)" +
                    " values(?,?,?,?,?,?,?,?)";
            insertResult = jdbcTemplate.update(sqlQuery, args.toArray());
        }
        if(insertResult > 0) {
            return userGoalDto.toString();
        }
        return null;
    }


    public List<UserGoalDto> getUserGoals(UserDataSearchDto userDataSearchDto) {

        StringBuffer sqlQuery = new StringBuffer();

        sqlQuery.append("select * from user_goals where name=? and category=? ");
        List<Object> args = new ArrayList<>();
        args.add(userDataSearchDto.getName());
        args.add(userDataSearchDto.getCategory());
        if(userDataSearchDto.getSubCategory() != null) {
            sqlQuery = sqlQuery.append(" and sub_category=?");
            args.add( userDataSearchDto.getSubCategory());
        }

        List<UserGoalDto> response = jdbcTemplate.query(sqlQuery.toString(),
                    args.toArray(),
                    new UserGoalDataRowMapper()
                );

        return response;
    }
}
