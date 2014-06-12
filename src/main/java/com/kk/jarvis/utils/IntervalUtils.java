package com.kk.jarvis.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 6/6/14
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntervalUtils {

    public static double adjustInterval(int interval, Map<String, String> searchParams) {
        String intervalType = searchParams.containsKey("interval_type")
                ? searchParams.get("interval_type") : "";
        double intervalAdjusted = 0;
        double searchInterval = searchParams.containsKey("interval")
                ? Double.parseDouble(searchParams.get("interval")) : 0;

        if(intervalType.equals("year")) {
            intervalAdjusted = (searchInterval * 24.0 * 365) /(double)(interval);
        }   else if(intervalType.equals("month")) {
            intervalAdjusted = (searchInterval * 24.0 * 30.0) /(double)(interval);
        }   else if(intervalType.equals("day")) {
            intervalAdjusted = (searchInterval * 24.0) /(double)(interval);
        }   else if(intervalType.equals("hour")) {
            intervalAdjusted = searchInterval/(double)interval;
        }
        return intervalAdjusted;
    }

    public static Date parseTimeOffset(String timeOffset) {
        int multiplier = 1;
        Calendar cal = Calendar.getInstance();

        if(timeOffset.matches("/d{1,2}///d{1,2}///d{4}")){

        }  else if(timeOffset.matches("^(.*)(/-?/d+[hH]{1})*(.*)$") || timeOffset.matches("^(.*)(/-?/d+[dD]{1})*(.*)$") || timeOffset.matches("^(.*)(/-?/d+[dD]{1})*(.*)$") || timeOffset.matches("^(.*)(/-?/d+[yY]{1})*(.*)$")) {
            int interval = 0;
            Pattern p0 = Pattern.compile("([-+]?)(.*)");
            Pattern p1 = Pattern.compile("(\\D*)(\\d+)(h{1})(.*)");
            Pattern p2 = Pattern.compile("(\\D*)(\\d+)(d{1})(.*)");
            Pattern p3 = Pattern.compile("(\\D*)(\\d+)(m{1})(.*)");
            Pattern p4 = Pattern.compile("(\\D*)(\\d+)(y{1})(.*)");

            Matcher m0 = p1.matcher(timeOffset);
            if(m0.find()) {
                String type = m0.group(1);
                if(type.equals("-")) {
                    multiplier = -1;
                }
            }

            Matcher m1 = p1.matcher(timeOffset);
            Matcher m2 = p2.matcher(timeOffset);
            Matcher m3 = p3.matcher(timeOffset);
            Matcher m4 = p4.matcher(timeOffset);

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


            cal.add(Calendar.HOUR, multiplier * interval);
            return cal.getTime();
        } else if(timeOffset.equals("yesterday")) {
             cal.add(Calendar.DATE, -1);
              return cal.getTime();
        }
        return null;
    }
}
