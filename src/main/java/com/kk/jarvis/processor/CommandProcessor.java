package com.kk.jarvis.processor;

import com.kk.jarvis.dto.UserInfoDto;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/17/14
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommandProcessor {

    public static final int USER_DATA_COMMAND = 0;
    public static final int TAG_COMMAND = 1;
    public static final int DEFINITION_COMMAND = 2;
    public static final int RESERVED_COMMAND = 3;


    public static final int INVALID_COMMAND = -1;

    public static final List<String> commandList = Arrays.asList("goal", "limit","assign");

    public Command parseCommand(String command);

    public void processCommand();

    public List<Command> parseCommands(List<String> commands);

    public void setUserInfo(UserInfoDto userInfoDto);

}
