package com.ironant.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: lsw
 * Created by lsw on 2017/11/20.
 */

public class Dateutils {

    private static Calendar mCalendar = Calendar.getInstance();

    /**
     * 将对比后的时间，格式化为：今天 明天 昨天和日期
     *
     * @param t
     * @return
     */
    public static String getTimeShow(String t) {
        if (t == null) {
            return "";
        } else if (t.isEmpty()) {
            return "";
        }

        long time = Long.parseLong(t) * 1000;

        if (time < 0)
            return String.valueOf(time);

        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
        if (difftime < 86400 && difftime > 0) {
//            if (difftime < 3600) {
//                int min = (int) (difftime / 60);
//                if (min == 0)
//                    return "刚刚";
//                else
//                    return (int) (difftime / 60) + "分钟前";
//            } else {
//                return (int) (difftime / 3600) + "小时前";
//            }
            return  "今日";
        } else {
            Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);

//            以下省去 HH:mm
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && isYesterday(time)) {
                return "昨日";
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && c.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)+1 ) {
                return "明日";
            }  else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            } else {
                return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            }
        }
    }


    public static String getTimeYYMMDD(String t) {
        if (t == null) {
            return "";
        } else if (t.isEmpty()) {
            return "";
        }

        long time = Long.parseLong(t) * 1000;

        if (time < 0)
            return String.valueOf(time);

//        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);

            Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);

          if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("M-d").format(c.getTime());
            } else {
                return new SimpleDateFormat("yyyy-M-d").format(c.getTime());
          }

    }



    /**
     * 判断时间是明天
     * @param time
     * @return
     */
    private static boolean isTomorrowToday(long time) {
        TimeInfo localTimeInfo = getTomorrowStartAndEndTime(time);
        return (time >= localTimeInfo.getStartTime()) && (time < localTimeInfo.getEndTime());
    }


    private static boolean isYesterday(long paramLong) {
        TimeInfo localTimeInfo = getYesterdayStartAndEndTime();
        return (paramLong >= localTimeInfo.getStartTime()) && (paramLong <= localTimeInfo.getEndTime());
    }


    private static TimeInfo getTomorrowStartAndEndTime(long time) {

            long currTime = System.currentTimeMillis();
            mCalendar.setTime(new Date(currTime));

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            mCalendar.set(year, month, day+1, 0, 0, 0);

            TimeInfo localTimeInfo = new TimeInfo();
            localTimeInfo.setStartTime(mCalendar.getTime().getTime());
            mCalendar.set(year, month, day+1, 23, 59, 59);
            localTimeInfo.setEndTime(mCalendar.getTime().getTime());

        return localTimeInfo;
    }


    //获取  昨天开始结束 时间

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);//11
        localCalendar1.set(Calendar.MINUTE, 0);//12
        localCalendar1.set(Calendar.SECOND, 0);//13
        localCalendar1.set(Calendar.MILLISECOND, 0);//Calendar.MILLISECOND
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);//11
        localCalendar2.set(Calendar.MINUTE, 59);//12
        localCalendar2.set(Calendar.SECOND, 59);//13
        localCalendar2.set(Calendar.MILLISECOND, 999);//Calendar.MILLISECOND
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();


        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }

    /**
     * 获取到 距离现在的时间
     * @param
     * @return
     */
    public static String getTimeDiff(String t) {
        long time = Long.parseLong(t) * 1000;

        if (time < 0)
            return String.valueOf(time);

        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
        if (difftime < 86400 && difftime > 0) {
            if (difftime < 3600) {
                int min = (int) (difftime / 60);
                if (min == 0)
                    return "1分钟";
                else
                    return (int) (difftime / 60) + "分钟";
            } else {
                return (int) (difftime / 3600) + "小时";
            }
        }else{
            return difftime / 86400 +"天";
        }

    }

    public static class TimeInfo {
        private long startTime;
        private long endTime;


        public long getStartTime() {
            return this.startTime;
        }


        public void setStartTime(long paramLong) {
            this.startTime = paramLong;
        }


        public long getEndTime() {
            return this.endTime;
        }


        public void setEndTime(long paramLong) {
            this.endTime = paramLong;
        }
    }
}
