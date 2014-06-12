package com.kk.jarvis.processor.command;

import com.kk.jarvis.dao.UserGoalDao;
import com.kk.jarvis.dto.UserGoalDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.dto.UserUnitAssignmentDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/18/14
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserGoalCommand implements Command {

    private List<String> params;

    private UserInfoDto userInfoDto;

    private JdbcTemplate jdbcTemplate;

    public UserGoalCommand(UserInfoDto userInfoDto, List<String> params, JdbcTemplate jdbcTemplate) {
        this.params = params;
        this.userInfoDto = userInfoDto;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> execute() {

        if(params.size() < 3) {
            return null;
        }
        String type = params.get(0);
        String name = params.get(1);
        int noOfCommands = Integer.parseInt(params.get(2));
        int interval = 0;

        if(params.size() > (noOfCommands * 2) +3){
            interval = Integer.parseInt(params.get(params.size() - 1));
        }else {
            interval = 24;
        }

        for(int i=1;i<= noOfCommands;i++) {
            String value = params.get(i*2+1);

            String unit = params.get(i*2 +2);

            UserGoalDto userGoalDto = new UserGoalDto();
            userGoalDto.setName(name);
            userGoalDto.setValue(value);
            userGoalDto.setUnit(unit);
            userGoalDto.setType(type);

            userGoalDto.setInterval(interval);

            String category = userInfoDto.getUserDataString("category");
            String subCategory = userInfoDto.getUserDataString("subcategory");
            Map<String, UserUnitAssignmentDto> assignmentDtoMap = null;
            if(userInfoDto.getUserData("assignments") != null) {
                assignmentDtoMap =  ( Map<String, UserUnitAssignmentDto>)userInfoDto.getUserData("assignments");
                if(assignmentDtoMap.containsKey(unit)) {
                    UserUnitAssignmentDto userUnitAssignmentDto = assignmentDtoMap.get(unit);
                    category = userUnitAssignmentDto.getCategory();
                    subCategory = userUnitAssignmentDto.getSubCategory() != null ? userUnitAssignmentDto.getSubCategory() : "";
                }
            }

            userGoalDto.setUserId(userInfoDto.getUserId());
            userGoalDto.setCategory(category);
            userGoalDto.setSubCategory(subCategory);
            userGoalDto.setAddTime(new Date().getTime()/1000);
            UserGoalDao userGoalDao = new UserGoalDao(jdbcTemplate);

            String response = userGoalDao.addUserGoal(userGoalDto);

            if(response == null )
            {
                return null;
            }
        }
        return params;

    }

    @Override
    public int getType() {
        return CommandProcessor.USER_DATA_COMMAND;
    }
}
