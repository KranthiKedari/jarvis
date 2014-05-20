package com.kk.jarvis.controller;

import com.kk.jarvis.auth.JarvisAuthToken;
import com.kk.jarvis.dao.UserDataDao;
import com.kk.jarvis.dto.UserDataDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import com.kk.jarvis.processor.command.CategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private UserInfoDto userInfoDto;


    @RequestMapping("/jarvis/update")
    public @ResponseBody String greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "Hellow World";
    }

    @RequestMapping("/jarvis/get")
    public @ResponseBody
    List<UserDataDto> getUserData(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        UserDataDao userDao = new UserDataDao(jdbcTemplate);
        List<UserDataDto> response =  userDao.getUserData();
        return response;
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
    ){
        return "jarvis";

    }
    @RequestMapping("/jarvis/add")
    public @ResponseBody String addUserData(
            @RequestHeader("X-Jarvis-Authentication-Provider") final String providerw,
            @RequestHeader("X-Jarvis-Auth-Token") final JarvisAuthToken authToken,
            @RequestBody(required = true) String command) {
        if(userInfoDto == null) {
             userInfoDto = new UserInfoDto();
        }
        userInfoDto.setUserId(1);
        if(userInfoDto.getUserData("category") == null) {
            userInfoDto.setUserData("category", "food");
        }

        if(userInfoDto.getUserData("subcategory") == null) {
            userInfoDto.setUserData("subcategory", "food");
        }
        commandProcessor.setUserInfo(userInfoDto);
        Command commandObj = commandProcessor.parseCommand(command);

        List<String> response =  commandObj.execute();
        if(commandObj instanceof CategoryCommand) {
            userInfoDto.setUserData("category", response.get(0));
            if(response.size() > 1) {
                userInfoDto.setUserData("subcategory", response.get(1));
            }
        }
        return  response.toString();
    }

}
