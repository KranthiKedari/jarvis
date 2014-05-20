package com.kk.jarvis.processor.command;

import com.kk.jarvis.dao.DefinitionDao;
import com.kk.jarvis.dto.DefinitionDto;
import com.kk.jarvis.dto.UserInfoDto;
import com.kk.jarvis.processor.Command;
import com.kk.jarvis.processor.CommandProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefinitionCommand implements Command {

    private List<String> params;

    private JdbcTemplate jdbcTemplate;

    private UserInfoDto userInfoDto;

    private int type;

    public  DefinitionCommand(UserInfoDto userInfoDto, List<String> params, JdbcTemplate jdbcTemplate) {
        this.params = params;
        this.userInfoDto = userInfoDto;
        this.jdbcTemplate = jdbcTemplate;
        this.type = CommandProcessor.DEFINITION_COMMAND;
    }
    @Override
    public List<String> execute() {
        DefinitionDto definitionDto = new DefinitionDto();
        definitionDto.setUserId(userInfoDto.getUserId());
        definitionDto.setKey(params.get(0));
        definitionDto.setValue(params.get(1));

        DefinitionDao definitionDao = new DefinitionDao(jdbcTemplate);
        DefinitionDto response = definitionDao.addDefinition(definitionDto);

        if(response !=null) {
            return params;
        }

        return null;
    }

    @Override
    public int getType() {
        return type;
    }
}
