package org.jeecg.modules.cq.util;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {
    private static final String[] YMDHMS = {"yyyyMMddHHmmss"};

    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String YMD_HM = "yyyy-MM-dd HH:mm";

    public static final String YMD = "yyyy-MM-dd";

    public static final String YMD_CHN = "yyyy年MM月dd日";

    private static final String HM = "HH:mm";

    private static final String YMDHM = "yyyy-MM-ddHH:mm";

    public static String getCurrentYMDHMS(){
        DateFormat df = new SimpleDateFormat(YMD_HMS);
        return df.format(new Date());
    }

    public static String getCurrentYMDHM(){
        DateFormat df = new SimpleDateFormat(YMD_HM);
        return df.format(new Date());
    }

    public static String getCurrentYMD(){
        DateFormat df = new SimpleDateFormat(YMD);
        return df.format(new Date());
    }

    public static String getYMDFormatString(Date date){
        SimpleDateFormat df = new SimpleDateFormat(YMD);
        return date != null ? df.format(date) : "";
    }

    public static String getYMDHMSFormatString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS);
        return date != null ? sdf.format(date) : "";
    }

    public static Date getCurrentBeginDate(Date curDate){
        SimpleDateFormat sdf = new SimpleDateFormat(YMD);
        Date date = null;
        try {
            date = sdf.parse(sdf.format(curDate));
        } catch (ParseException e) {
            log.error("getCurrentBeginDate error",e);
        }
        return date;
    }

    public static Date getPreDate(Date curDate,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH,-num);
        return calendar.getTime();
    }

    public static Date getPreDay(Date curDate, int num) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH,-num);
        return df.parse(df.format(calendar.getTime()));
    }

    public static Date getAfterDate(Date curDate,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH,num);
        return calendar.getTime();
    }

    public static String getPreDay(Date curDate){
        DateFormat df = new SimpleDateFormat(YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return df.format(calendar.getTime());
    }

    public static String getNextYMD(){
        DateFormat df = new SimpleDateFormat(YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        return df.format(calendar.getTime());
    }

    public static Date getNextDateYMD(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }





    public static String getCurrentHM(){
        DateFormat df = new SimpleDateFormat(HM);
        return df.format(new Date());
    }

    public static String getCurrentHM(Date curDate){
        DateFormat df = new SimpleDateFormat(HM);
        return df.format(curDate);
    }


    public static String timeStampCovert(long timeStamp, String pattern){
        Timestamp ts = new Timestamp(timeStamp);
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(ts).toString();
    }

    public static Date convertStringToDate(String dateStr){
        return convertStringToDate(dateStr,YMD_HMS);
    }

    public static Date convertTimeStampToDate(Long timeStamp,String pattern){
        String dateString = timeStampCovert(timeStamp, pattern);
        Date date = convertStringToDate(dateString, pattern);
        return date;
    }

    public static Date convertStringToDate(String value, String pattern){

        DateFormat sdf = new SimpleDateFormat(pattern);
        if(StringUtils.isEmpty(value)){
            return null;
        }
        try{
            return sdf.parse(value);
        }catch(ParseException e){
            log.error("[convertStringToDate] 解析日期错误, error:{}",e.getMessage());
        }
        return null;
    }

    public static Long getYesterdayBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTimeInMillis();
    }

    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    public static Long getLastMonthFirstDay(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS);
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            return calendar.getTime().getTime();
        }catch (Exception e){
            log.error("getLastWeekDay error,dateStr:" +dateStr,e);
            return null;
        }
    }

    public static Long getLastMonthLastDay(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS);
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH,0);
            return calendar.getTime().getTime();
        }catch (Exception e){
            log.error("getLastWeekDay error,dateStr:" +dateStr,e);
            return null;
        }
    }


    public static Long getTimeStamp(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS);
            return sdf.parse(dateStr).getTime();
        }catch (Exception e){
            log.error("getTimeStamp error,dateStr:" +dateStr,e);
            return null;
        }
    }


    public static Long getLastWeekDay(String dateStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS);
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            return calendar.getTime().getTime();
        }catch (Exception e){
            log.error("getLastWeekDay error,dateStr:" +dateStr,e);
            return null;
        }
    }

    public static Date getStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Long getYesterdayMorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTodayDate());
        calendar.add(Calendar.DATE,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getTodayMorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTodayDate());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,-10);
        return calendar.getTime();
    }

    public static String getTodayYMD() {
        DateFormat df = new SimpleDateFormat(YMD);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,-10);
        return df.format(calendar.getTime());
    }
}
