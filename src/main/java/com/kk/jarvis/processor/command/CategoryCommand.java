package com.kk.jarvis.processor.command;

import com.kk.jarvis.dao.CategoryDao;
import com.kk.jarvis.dao.UserStatsDao;
import com.kk.jarvis.dto.CategoryDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.dto.UserStatsDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/17/14
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryCommand implements Command {
    private List<String> params;

    private UserInfoDto userInfoDto;

    private JdbcTemplate jdbcTemplate;

    public CategoryCommand(UserInfoDto userInfoDto, List<String> params, JdbcTemplate jdbcTemplate) {
        this.params = params;
        this.userInfoDto = userInfoDto;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> execute() {

        String category = params.get(0);

        String subCategory = params.size() > 1 ? params.get(1) : "";

        CategoryDto categoryDto = new CategoryDto(userInfoDto.getUserId(),category, subCategory);

        CategoryDao categoryDao = new CategoryDao(jdbcTemplate);
        CategoryDto response = categoryDao.addCategory(categoryDto);
        List<UserStatsDto> statsList = new ArrayList<UserStatsDto>();

        statsList.add(new UserStatsDto(userInfoDto.getUserId(),"category", category));

        statsList.add(new UserStatsDto(userInfoDto.getUserId(),"subcategory", subCategory));

        UserStatsDao userStatsDao = new UserStatsDao(jdbcTemplate);
        userStatsDao.setUserStats(statsList);

        if(response != null) {
            return params;
        }

        return null;

    }

    @Override
    public int getType() {
        return CommandProcessor.TAG_COMMAND;
    }

}
