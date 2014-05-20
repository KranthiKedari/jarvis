package com.kk.jarvis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/13/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class JarvisDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

}
