package com.kk.jarvis.charts;

import com.kk.jarvis.dto.UserDataSearchDto;
import com.kk.jarvis.dto.UserGoalDto;
import com.kk.jarvis.utils.IntervalUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/29/14
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleCharts {

    private UserDataSearchDto userDataSearchDto;

    public GoogleCharts(UserDataSearchDto userDataSearchDto) {
        this.userDataSearchDto = userDataSearchDto;
    }
    public String getGoogleChartsDataForAggregation(List<Map<String, String>> aggregatedDataFromDao, List<UserGoalDto> goals) {
        List<String> namesList = new ArrayList<String>();
        List<String> header = new ArrayList<String>();
        Map<String, Map<String,String>>  data = new TreeMap<>();
        StringBuffer response = new StringBuffer();
        header.add("TIME");

        for(Map<String,String> record : aggregatedDataFromDao) {
            String name = record.get("name");
            if(!namesList.contains(name)){
                namesList.add(name);
                header.add(name);
            }


            Map<String,String> dataValues = data.get(getKeyName(record));
            if(dataValues == null) {
                dataValues = new HashMap<>();
            }

            dataValues.put(name, record.get("value"));
            data.put(getKeyName(record), dataValues);
        }


        //check for the goal and limit targets
        for(UserGoalDto userGoalDto : goals) {
            double intervalAdjustingMultiplier = IntervalUtils.adjustInterval(userGoalDto.getInterval(), userDataSearchDto.getSearchParams());
            header.add(userGoalDto.getType());
            namesList.add(userGoalDto.getType());

            for(String yAxis : data.keySet()) {
                Map<String,String> valueSet = data.get(yAxis);
                valueSet.put(userGoalDto.getType(), (Integer.parseInt(userGoalDto.getValue()) * intervalAdjustingMultiplier) +"");
                data.put(yAxis, valueSet);
            }
        }
        response.append("[[");

        for(String headerElement: header) {
            response.append("\""+ headerElement+"\",");
        }

        response = response.replace(response.length() - 1, response.length(), "");

        response.append("]");

        for(String keyName : data.keySet()) {
            Map<String,String> recordData = data.get(keyName);
            response.append(",[");
            response.append("\"" + keyName + "\"");
            for(String name : namesList) {
                if(recordData.containsKey(name)) {
                    response.append("," + recordData.get(name));
                }
                else {
                    response.append(",0");
                }
            }
            response.append("]") ;

        }
        response.append("]") ;

        return response.toString();
    }

    private String getKeyName(Map<String,String> dataRecord) {
        String[] fieldSet = {"monthValue", "dayValue", "yearValue"};
        StringBuffer response = new StringBuffer();
        for(String field : fieldSet) {
            if(dataRecord.containsKey(field)) {
                response.append(String.format("%02d", Integer.parseInt(dataRecord.get(field))) + "/");
            }
        }
        response = response.replace(response.length() - 1, response.length(), "");

        int interval = userDataSearchDto.getSearchParams().containsKey("interval")
                ? Integer.parseInt(userDataSearchDto.getSearchParams().get("interval")) : 0;

        if(dataRecord.containsKey("hourValue")) {
            response.append(" "+ interval* Integer.parseInt(dataRecord.get("hourValue")) + ":00:00");
        }

        return response.toString();
    }
}
