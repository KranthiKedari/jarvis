package com.kk.jarvis.controller;

import com.kk.jarvis.auth.JarvisAuthToken;
import com.kk.jarvis.auth.JarvisTokenDecoder;
import com.kk.jarvis.charts.GoogleCharts;
import com.kk.jarvis.dao.*;
import com.kk.jarvis.dto.*;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/12/14
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class JarvisController {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public CommandProcessor commandProcessor;

    private Map<Integer, UserInfoDto> userInfo = new HashMap<Integer, UserInfoDto>();


    @RequestMapping("/jarvis/update")
    public @ResponseBody String greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "Hellow World";
    }

    @RequestMapping("/jarvis/get")
    public @ResponseBody
    List<Map<String,String>> getUserData(
            @RequestHeader("X-Jarvis-Authentication-Provider") final String provider,
            @RequestHeader("X-Jarvis-Auth-Token") final JarvisAuthToken authToken,
            @RequestBody(required=true) UserDataSearchDto userDataSearchDto) {

        // set the user ID
        userDataSearchDto.setUserId(authToken.getUserId());

        if(userDataSearchDto.getCategory() == null ) {
            userDataSearchDto.setCategory("food");
        }

        if(userDataSearchDto.getSearchParams() == null ) {
            Map<String, String> searchParams = new HashMap<String, String>();
            searchParams.put("type", "aggregation");
            searchParams.put("interval_type", "year");
            searchParams.put("interval", "1");
            userDataSearchDto.setSearchParams(searchParams);
        }

        UserDataSearchDao userDataSearchDao = new UserDataSearchDao(jdbcTemplate);

        return userDataSearchDao.getData(userDataSearchDto);
    }

    @RequestMapping("/jarvis/google/get")
    public @ResponseBody
    String getUserDataGorGoogleCharts(
            @RequestHeader("X-Jarvis-Authentication-Provider") final String provider,
            @RequestHeader("X-Jarvis-Auth-Token") final JarvisAuthToken authToken,
            @RequestBody(required=true) UserDataSearchDto userDataSearchDto) {

        // set the user ID
        userDataSearchDto.setUserId(authToken.getUserId());

        if(userDataSearchDto.getCategory() == null ) {
            userDataSearchDto.setCategory("food");
        }

        if(userDataSearchDto.getSearchParams() == null ) {
            Map<String, String> searchParams = new HashMap<String, String>();
            searchParams.put("type", "aggregation");
            searchParams.put("interval_type", "year");
            searchParams.put("interval", "1");
            userDataSearchDto.setSearchParams(searchParams);
        }

        UserDataSearchDao userDataSearchDao = new UserDataSearchDao(jdbcTemplate);
        List<UserGoalDto> goals = new ArrayList<>();
        if(userDataSearchDto.getName() != null) {
            goals = new UserGoalDao(jdbcTemplate).getUserGoals(userDataSearchDto);

        }
        List<Map<String, String>> result = userDataSearchDao.getData(userDataSearchDto);
        return new GoogleCharts(userDataSearchDto).getGoogleChartsDataForAggregation(result, goals);

    }

    @RequestMapping("/jarvis/addMulti")
    public @ResponseBody String addMultiUserData(
            @RequestBody(required = true) List<String> commandList) {
       return null;
    }

    @RequestMapping("/jarvis/authenticate")
    public @ResponseBody String authenticateUser(
            @RequestParam(required = true) String userName,
            @RequestParam(required = true)  String pwd
    ) throws Exception {
        UserInfoDto userInfoDto = new UserInfoDao(jdbcTemplate).getUserInfo(userName, pwd);

        userInfo.put(userInfoDto.getUserId(), userInfoDto);

        String payload = "{" + "\"user_id\":" + userInfoDto.getUserId() +
                ",\"algorithm\":\"HmacSHA256\"}";

        return new JarvisTokenDecoder().signToken(payload, "testSecret");


    }
    @RequestMapping("/jarvis/add")
    public @ResponseBody String addUserData(
            @RequestHeader("X-Jarvis-Authentication-Provider") final String provider,
            @RequestHeader("X-Jarvis-Auth-Token") final JarvisAuthToken authToken,
            @RequestBody(required = true) String command) {
        UserInfoDto userInfoDto = null;
        int userId = authToken.getUserId();
        command = command.toLowerCase();
        if(userInfo.containsKey(userId)) {
            userInfoDto = userInfo.get(userId);
        }
        if(userInfoDto == null) {

            UserInfoDto info = new UserInfoDto();
            info.setUserId(userId);
             List<UserStatsDto> userStats = new UserStatsDao(jdbcTemplate).getUserStats(userId);
            for(UserStatsDto userStatsDto: userStats) {
                info.setUserData(userStatsDto.getKey(), userStatsDto.getValue());
            }

            userInfoDto = info;
            if(userInfoDto.getUserData("category") == null) {
                userInfoDto.setUserData("category", "expenditure");
            }

            Map<String, UserUnitAssignmentDto> assignments = new UserUnitAssignmentDao(jdbcTemplate).getUserAssignments(userInfoDto);
            userInfoDto.setUserData("assignments", assignments);
            userInfo.put(userId, info);
        }
        if(userInfoDto.getUserData("category") == null) {
            userInfoDto.setUserData("category", "expenditure");
        }

        commandProcessor.setUserInfo(userInfoDto);
        Command commandObj = commandProcessor.parseCommand(command);

        List<String> response =  commandObj.execute();
        if(commandObj.getType() == CommandProcessor.TAG_COMMAND && response !=null) {
            userInfoDto.setUserData("category", response.get(0));
            if(response.size() > 1) {
                userInfoDto.setUserData("subcategory", response.get(1));
            }

            userInfo.put(userId, userInfoDto);
        }
        return  response.toString();
    }

}
