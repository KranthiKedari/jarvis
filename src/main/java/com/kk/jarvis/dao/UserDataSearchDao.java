package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.UserSearchAggregationRowMapper;
import com.kk.jarvis.dto.UserDataSearchDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/28/14
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDataSearchDao {

    private JdbcTemplate jdbcTemplate;

    public UserDataSearchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, String>> getData(UserDataSearchDto userDataSearchDto) {

        String type = userDataSearchDto.getSearchType();

        if(type.equals("aggregation")) {
            return aggregateData(userDataSearchDto);
        } else if(type.equals("progession")) {

        }


        return null;
    }
    private List<Map<String,String>> getProgressiveData(UserDataSearchDto userDataSearchDto) {

        StringBuffer sqlQuery = new StringBuffer("select * from user_data");
        StringBuffer  sqlWhere = new StringBuffer();

        boolean andFlag = false;
        List<Object> args = new ArrayList<Object>();
        if(userDataSearchDto.getStartDate() != 0) {
            if(andFlag)
            {
                sqlWhere.append(" and ");
            }
            sqlWhere.append(" add_time >= from_unixtime(?) ");
            andFlag = true;
            args.add(userDataSearchDto.getStartDate());
        }
        if(userDataSearchDto.getStartDate() != 0) {
            if(andFlag)
            {
                sqlWhere.append(" and ");
            }
            sqlWhere.append(" add_time <= from_unixtime(?) ");
            args.add(userDataSearchDto.getEndDate());
        }

        return null;
    }
    private List<Map<String,String>> aggregateData(UserDataSearchDto userDataSearchDto) {
        StringBuffer  sqlSelect = new StringBuffer();
        StringBuffer  sqlWhere = new StringBuffer();
        StringBuffer  sqlGroupBy = new StringBuffer();

        sqlSelect.append("select name ,count(value) as value, unit as unit, ");

        Map<String,String> searchOptions = userDataSearchDto.getSearchParams();

        String type = searchOptions.containsKey("type") ? searchOptions.get("type") : "aggregation";

        String intervalType = searchOptions.get("interval_type");
        int interval = Integer.parseInt(searchOptions.get("interval"));
        if(intervalType.equalsIgnoreCase("year")) {
            sqlSelect.append(" FLOOR(YEAR(add_time)/" + interval + ") as yearValue ");
            sqlGroupBy.append(" group by name,yearValue ");
        }
        else if(intervalType.equalsIgnoreCase("month")) {
            sqlSelect.append(" FLOOR(YEAR(add_time)) as yearValue, ");
            sqlSelect.append(" FLOOR(MONTH(add_time)/" + interval + ") as monthValue ");
            sqlGroupBy.append(" group by  name,yearValue,monthValue ");
        }
        else if(intervalType.equalsIgnoreCase("day")) {
            sqlSelect.append(" FLOOR(YEAR(add_time)) as yearValue, ");
            sqlSelect.append(" FLOOR(MONTH(add_time)) as monthValue, ");
            sqlSelect.append(" FLOOR(DAY(add_time)/" + interval + ") as dayValue ");
            sqlGroupBy.append(" group by  name,yearValue,monthValue,dayValue ");
        } else if(intervalType.equalsIgnoreCase("hour")) {
            sqlSelect.append(" FLOOR(YEAR(add_time)) as yearValue, ");
            sqlSelect.append(" FLOOR(MONTH(add_time)) as monthValue, ");
            sqlSelect.append(" FLOOR(DAY(add_time)) as dayValue, ");
            sqlSelect.append(" FLOOR(HOUR(add_time)/" + interval + ") as hourValue ");
            sqlGroupBy.append(" group by  name,yearValue,monthValue,dayValue,hourValue ");
        }

        sqlSelect.append(" from user_data ");
        List<Object> args = new ArrayList<Object>();
        boolean andFlag = false;

        if(userDataSearchDto.getStartDate() != 0) {
            sqlWhere.append(" add_time >= from_unixtime(?) ");
            args.add(userDataSearchDto.getStartDate());
            andFlag = true;
        }
        if(userDataSearchDto.getStartDate() != 0) {
            if(andFlag)
            {
                sqlWhere.append(" and ");
            }
            sqlWhere.append(" add_time <= from_unixtime(?) ");
            args.add(userDataSearchDto.getEndDate());
            andFlag = true;
        }

        if(userDataSearchDto.getName() != null) {
            if(andFlag)
            {
                sqlWhere.append(" and ");
            }
            sqlWhere.append(" name = ?");
            args.add(userDataSearchDto.getName());
            andFlag = true;
        }
        if(sqlWhere.length() > 0) {
            sqlSelect.append(" where ").append(sqlWhere);
        }

        sqlSelect.append(sqlGroupBy);
        List<Map<String,String>> response = jdbcTemplate.query(sqlSelect.toString(),
                args.toArray(), new UserSearchAggregationRowMapper());

        return response;
    }
}
