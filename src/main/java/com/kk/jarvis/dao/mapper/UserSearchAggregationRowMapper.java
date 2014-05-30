package com.kk.jarvis.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/29/14
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserSearchAggregationRowMapper implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int rowNo) throws SQLException {
        Map<String, String> values = new HashMap<String, String>();

        ResultSetMetaData resultSetMetaData =  resultSet.getMetaData();
        int count = resultSetMetaData.getColumnCount();

        for(int i =1;i<=count;i++ ) {
            values.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
        }
        return values;
    }
}
