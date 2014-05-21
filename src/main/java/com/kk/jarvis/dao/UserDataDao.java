package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserDataRowMapper;
import com.kk.jarvis.dto.UserDataDto;
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
public class UserDataDao extends JarvisDao{

   private JdbcTemplate jdbcTemplate;

   public UserDataDao(JdbcTemplate jdbcTemplate) {
       this.jdbcTemplate = jdbcTemplate;
   }

   public List<UserDataDto> getUserData()
   {
       List<UserDataDto> userData = null;

           userData = jdbcTemplate.query("select * from user_data", new Object[]{}, new UserDataRowMapper());

       return userData;
   }


    public String addUserData(UserDataDto userDataDto)
    {
        List<UserDataDto> userData = null;
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(userDataDto.getUserId());
        args.add(userDataDto.getCategory());
        args.add(userDataDto.getSubCategory());
        args.add(userDataDto.getName());
        args.add(userDataDto.getValue());
        args.add(userDataDto.getUnit());

        String sqlQuery = "insert into user_data(`user_id`,`category`,`sub_category`,`name`,`value`,`unit`)" +
                " values(?,?,?,?,?,?)";
        int result = jdbcTemplate.update(sqlQuery, args.toArray());

        if(result > 0) {
            return userDataDto.toString();
        }

        return null;
    }
}
