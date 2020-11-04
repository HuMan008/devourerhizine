package com.petroun.devourerhizine.classes.tools;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtils {
    private static ThreadLocal<SimpleDateFormat> threadLocalDateTimeNoWithMilliSecondSymbolFormatter = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }
    };

    private static ThreadLocal<SimpleDateFormat> threadLocalDateTimeNoWithMilliFormatter = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }
    };

    public static final SimpleDateFormat simpleDateTimeWithMilliSecondNoSymbolFormatter() {
        return (SimpleDateFormat)threadLocalDateTimeNoWithMilliSecondSymbolFormatter.get();
    }

    public static final SimpleDateFormat simpleDateTimeWithFormatter() {
        return (SimpleDateFormat)threadLocalDateTimeNoWithMilliFormatter.get();
    }

    public static final Date strToDate(String date){
        try {
            Date dt = threadLocalDateTimeNoWithMilliFormatter.get().parse(date);
            return dt;
        }catch (Exception e){

        }
        return null;
    }

    public static final Date DateAddSed(String date, int sed){
        try {
            Date dt = threadLocalDateTimeNoWithMilliFormatter.get().parse(date);
            Calendar newTime = Calendar.getInstance();
            newTime.setTime(dt);
            newTime.add(Calendar.SECOND,sed);

            return newTime.getTime();
        }catch (Exception e){

        }
        return null;
    }

    public static final Date DateAddSed(Date date, int sed){
        try {
            Date dt = date;
            Calendar newTime = Calendar.getInstance();
            newTime.setTime(dt);
            newTime.add(Calendar.SECOND,sed);

            return newTime.getTime();
        }catch (Exception e){

        }
        return null;
    }
}
