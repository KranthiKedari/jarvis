package com.kk.jarvis.processor.command;

import com.kk.jarvis.dao.UserUnitAssignmentDao;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.dto.UserUnitAssignmentDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 6/9/14
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserUnitAssignmentCommand implements Command {
    private List<String> params;

    private UserInfoDto userInfoDto;

    private JdbcTemplate jdbcTemplate;

    public UserUnitAssignmentCommand(UserInfoDto userInfoDto, List<String> params, JdbcTemplate jdbcTemplate) {
        this.userInfoDto = userInfoDto;
        this.params = params;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> execute() {

        String category = (String)userInfoDto.getUserData("category");

        String subCategory= (String)userInfoDto.getUserData("subCategory");

        String unit = params.get(0);
        if(params.size() == 2) {
           category = params.get(1);
        }
        else if(params.size() == 3) {
            category = params.get(1);
            subCategory = params.get(2);
        }
        UserUnitAssignmentDto userUnitAssignmentDto = new UserUnitAssignmentDto();
        userUnitAssignmentDto.setUserId(userInfoDto.getUserId());
        userUnitAssignmentDto.setCategory(category);
        userUnitAssignmentDto.setSubCategory(subCategory);
        userUnitAssignmentDto.setUnit(unit);
        boolean result = new UserUnitAssignmentDao(jdbcTemplate).assignUnit(userUnitAssignmentDto);

        if(result) {
            return params;
        }

        return null;
    }

    @Override
    public int getType() {
        return CommandProcessor.RESERVED_COMMAND;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
