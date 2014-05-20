package com.kk.jarvis.processor.command;

import com.kk.jarvis.dao.UserDataDao;
import com.kk.jarvis.dto.UserDataDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/18/14
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataCommand implements Command {

    private List<String> params;

    private UserInfoDto userInfoDto;

    private JdbcTemplate jdbcTemplate;

    public UserDataCommand(UserInfoDto userInfoDto, List<String> params, JdbcTemplate jdbcTemplate) {
        this.params = params;
        this.userInfoDto = userInfoDto;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> execute() {

        if(params.size() < 3) {
            return null;
        }
        String name = params.get(0);

        String value = params.get(1);

        String unit = params.get(2);

        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setName(params.get(0));
        userDataDto.setValue(params.get(1));
        userDataDto.setUnit(params.get(2));
        userDataDto.setUserId(userInfoDto.getUserId());
        userDataDto.setCategory(userInfoDto.getUserData("category"));
        userDataDto.setSubCategory(userInfoDto.getUserData("subcategory"));
        userDataDto.setAddTime(new Date().getTime()/1000);
        UserDataDao userDataDao = new UserDataDao(jdbcTemplate);

        String response = userDataDao.addUserData(userDataDto);

        if(response != null )
        {
            return params;
        }

        return null;

    }

    @Override
    public int getType() {
        return CommandProcessor.USER_DATA_COMMAND;
    }
}
