package com.kk.jarvis.dao.mysql;

import com.kk.jarvis.dao.JarvisDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserAuthDao extends JarvisDao {

    private JdbcTemplate jdbcTemplate;

    public  UserAuthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String authenticateUser(String userName,  String pwd) {
        return null;
    }
}
