package com.kk.jarvis.processor;

import com.kk.jarvis.dao.mysql.MySqlConfiguration;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.processor.command.*;
import com.kk.jarvis.utils.IntervalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            case CommandProcessor.RESERVED_COMMAND :
                return processReservedCommand(command);
            case CommandProcessor.USER_DATA_COMMAND:
                return new UserDataCommand(userInfoDto, getDataCommandParams(command), jdbcTemplate);
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
        String[] st = command.split(" ");
        String commandName = st[0];
        if(CommandProcessor.commandList.contains(commandName)) {
            return CommandProcessor.RESERVED_COMMAND;
        }else if(command.matches("^[A-Za-z]+([0-9]+[.]?[0-9]*[A-za-z$]+)+([ ]{1}[0-9hdmyHDMY/-]+)*$")) {
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


    public Command processReservedCommand(String command) {
        String[] st = command.split(" ");
        if(st.length < 1) {
            return null;
        }
        String commandName = st[0];

        if(commandName.equalsIgnoreCase("goal") || commandName.equalsIgnoreCase("limit")) {

            List<String> params = getAddParams(st[1]);
            params.add(0,commandName);


            if(st.length > 2) {
                String timeStr = st[2];
                int interval = getInterval(timeStr);
                if(interval !=0 ) {
                    params.add(interval+"");
                }
            }

            return new UserGoalCommand(userInfoDto, params, jdbcTemplate);
        } else if(commandName.equals("assign")) {
            List<String> params = new ArrayList<>();

            for(int i =1;i<st.length; i++ ){
                params.add(st[i]);  // Unit Name, Category and SubCategory;
            }

            return new UserUnitAssignmentCommand(userInfoDto, params, jdbcTemplate);

        }

        return null;
    }
    public List<String> getDataCommandParams(String command) {
        String[] tokens = command.split(" ");
        List<String> params = getAddParams(tokens[0]);


        if(tokens.length == 2) {
            Date addTime = IntervalUtils.parseTimeOffset(tokens[1]);
            if(addTime !=null) {
                String dateStr = new SimpleDateFormat().format(addTime);
                params.add(dateStr);
            }

        }
        return params;
    }
    public List<String> getAddParams(String command) {
        Pattern p = Pattern.compile("(\\D*)((\\d+\\.?\\d*\\D*)+)");
        Matcher m = p.matcher(command);
        List<String> params = new ArrayList<String>();
        if(m.find()) {
            params.add(m.group(1));

            String compoundList = m.group(2);

            Pattern p1 = Pattern.compile("(\\d+\\.?\\d*)(\\D*)((\\d+\\.?\\d*\\D*)*)");
            int count = 0;
            while(compoundList.length() > 0) {
                Matcher m1 = p1.matcher(compoundList);
                if(m1.find()) {
                    params.add(m1.group(1));
                    params.add(m1.group(2));
                    count++;
                    compoundList = m1.group(3);
                }
            }
            params.add(1,count+"");
        }
        return params;
    }

    public int getInterval(String timeStr) {
        int interval = 0;
        Pattern p1 = Pattern.compile("(\\D*)(\\d+)(h{1})(.*)");
        Pattern p2 = Pattern.compile("(\\D*)(\\d+)(d{1})(.*)");
        Pattern p3 = Pattern.compile("(\\D*)(\\d+)(m{1})(.*)");
        Pattern p4 = Pattern.compile("(\\D*)(\\d+)(y{1})(.*)");



        Matcher m1 = p1.matcher(timeStr);
        Matcher m2 = p2.matcher(timeStr);
        Matcher m3 = p3.matcher(timeStr);
        Matcher m4 = p4.matcher(timeStr);

        if(m1.find()) {
            interval += Integer.parseInt(m1.group(2));
        }
        if(m2.find()) {
            interval += Integer.parseInt(m2.group(2)) * 24;
        }
        if(m3.find()) {
            interval += Integer.parseInt(m3.group(2)) * 24 * 30;
        }
        if(m4.find()) {
            interval += Integer.parseInt(m3.group(2)) * 24 * 30 * 12;
        }

        return interval;
    }
}
