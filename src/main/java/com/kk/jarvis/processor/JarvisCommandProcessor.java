package com.kk.jarvis.processor;

import com.kk.jarvis.dao.mysql.MySqlConfiguration;
import com.kk.jarvis.dto.UserDataDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.processor.command.CategoryCommand;
import com.kk.jarvis.processor.command.DefinitionCommand;
import com.kk.jarvis.processor.command.UserDataCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/17/14
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(basePackageClasses=MySqlConfiguration.class)
public class JarvisCommandProcessor implements CommandProcessor {

    private UserInfoDto userInfoDto;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Command parseCommand(String command) {

        int commandType = getCommandType(command);
        Pattern p;
        Matcher m;
        List<String> params;
        switch(commandType) {
            case CommandProcessor.USER_DATA_COMMAND:
                p = Pattern.compile("(\\D*)(\\d+)(\\D*)");
                m = p.matcher(command);
                UserDataDto userDataDto = new UserDataDto();
                params = new ArrayList<String>();
                if (m.find( )) {
                    params.add(m.group(1));
                    params.add(m.group(2));
                    params.add(m.group(3));
                    return  new UserDataCommand(userInfoDto, params, jdbcTemplate);

                } else {
                    System.out.println("NO MATCH");
                }
                break;
            case CommandProcessor.TAG_COMMAND:
                p = Pattern.compile("(\\S+)(\\s*)(.*)");
                m = p.matcher(command);
                params = new ArrayList<String>();
                if (m.find( )) {
                    params.add(m.group(1));
                    params.add(m.group(3));
                    return  new CategoryCommand(userInfoDto, params, jdbcTemplate);

                } else {
                    System.out.println("NO MATCH");
                }
                break;
            case CommandProcessor.DEFINITION_COMMAND:
                p = Pattern.compile("(.*)(\\={1})(.*)");
                m = p.matcher(command);
                params = new ArrayList<String>();
                if (m.find( )) {
                    params.add(m.group(1));
                    params.add(m.group(3));
                    return  new DefinitionCommand(userInfoDto, params, jdbcTemplate);

                } else {
                    System.out.println("NO MATCH");
                }
                break;

        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Command> parseCommands(List<String> commands) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void processCommand() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private int getCommandType(String command) {
        if(command.matches("^[A-Za-z]+[0-9]+[A-za-z]+$")) {
          return CommandProcessor.USER_DATA_COMMAND;
        }  else if(command.matches("^[A-Za-z ]+$")) {
          return CommandProcessor.TAG_COMMAND;
        } else if(command.matches("^[A-Za-z]+[=]{1}[A-za-z]+$")) {
          return CommandProcessor.DEFINITION_COMMAND;
        }

        return CommandProcessor.INVALID_COMMAND;
    }

    public void setUserInfo(UserInfoDto userInfoDto) {
      this.userInfoDto = userInfoDto;
    }
}
