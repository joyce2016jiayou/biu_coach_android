package com.noplugins.keepfit.coachplatform.util.data;

import android.util.Log;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DateUtils {

    private static String mYear; // 当前年
    private static String mMonth; // 月
    private static String mDay;
    private static String mWay;

    private static int get_max_day = 14;

    /**
     * 获取当前日期几月几号
     */
    public static String getDateString() {


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        if (Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (Integer.parseInt(mMonth)))) {
            mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (Integer.parseInt(mMonth))));
        }
        return mMonth + "月" + mDay + "日";
    }


    /**
     * 获取当前年月日
     *  
     *
     * @return
     */
    public static String StringData() {


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        if (Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (Integer.parseInt(mMonth)))) {
            mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (Integer.parseInt(mMonth))));
        }
        return mYear + "-" + (mMonth.length() == 1 ? "0" + mMonth : mMonth) + "-" + (mDay.length() == 1 ? "0" + mDay : mDay);
    }


    /**
     * 根据当前日期获得是星期几
     *  
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {


            c.setTime(format.parse(time));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "周天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "周六";
        }
        return Week;
    }


    private class date_bean {
        String week_str;
        String date_str;
    }


    /**
     * 获取今天往后一段时间日期（几月几号）
     */
    public static List<SelectDateBean> getmoredate() {
        List<SelectDateBean> dates = new ArrayList<SelectDateBean>();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        List<String> weeksList = get7week();
        mYear = String.valueOf(calendar.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);// 获取当前月份
        int max_date_of_month = MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), Integer.parseInt(mMonth));
        int next_max_month = (get_max_day - max_date_of_month) / 31; //获取往后可能有几个月

        for (int k = 0; k <= next_max_month; k++) {//需要循环设置几个月
            for (int i = 0; i < get_max_day; i++) {
                SelectDateBean selectDateBean = new SelectDateBean();
                mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
                if (Integer.parseInt(mDay) > max_date_of_month) {
                    mMonth = String.valueOf(calendar.get(Calendar.MONTH) + (k + 1));// 获取当前月份
                    int day = Integer.valueOf(mDay) - max_date_of_month;
                    mDay = String.valueOf(day);// 获取当前日份的日期号码
                }
                selectDateBean.setYear(mYear);
                selectDateBean.setMonth(mMonth);
                selectDateBean.setDate(mDay);
                if (weeksList.get(i).equals("今天")) {
                    selectDateBean.setIs_check(true);
                }
                selectDateBean.setWeek_str(weeksList.get(i));
//                Log.e("获取到的往后的时间", mMonth + "/" + mDay);
//                Log.e("获取到的往后星期", weeksList.get(i));
                dates.add(selectDateBean);
            }
            get_max_day = get_max_day - max_date_of_month;

        }
        return dates;
    }


    /**
     * 获取今天往后一周的集合
     */
    public static List<String> get7week() {
        String week = "";
        List<String> weeksList = new ArrayList<String>();
        List<String> dateList = get7date();
        for (String s : dateList) {
            if (s.equals(StringData())) {
                week = "今天";
            } else {
                week = getWeek(s);
            }

            weeksList.add(week);
        }
        return weeksList;
    }


    public static List<String> get7date() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        String date = sim.format(c.getTime());
        dates.add(date);
        for (int i = 0; i < get_max_day; i++) {
            c.add(java.util.Calendar.DAY_OF_MONTH, 1);
            date = sim.format(c.getTime());
            dates.add(date);
        }
        return dates;
    }

    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0                 
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }
}
