package com.ironant.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 系统闹钟设置
 */
public class AlarmOperator {

    Context context;

    public AlarmOperator(Context context) {
        this.context = context;
    }

    public void setAlarm(String content, int hour, int minute) {
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//周一
        testDays.add(Calendar.TUESDAY);//周二
        testDays.add(Calendar.WEDNESDAY);//周三
        testDays.add(Calendar.THURSDAY);
        testDays.add(Calendar.FRIDAY);//
        testDays.add(Calendar.SATURDAY);
        testDays.add(Calendar.SUNDAY);//


        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, testDays); // 时间
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, content); // 标题
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour); // 小时
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute); // 分钟
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);//设置闹钟时不显示系统闹钟界面
        alarmIntent.putExtra(AlarmClock.EXTRA_VIBRATE, true);//设置闹钟响时震动
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }

    public void dismissAlarm(String content) {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(context, "手机版本过低,需手动取消闹钟",
                    Toast.LENGTH_SHORT).show();
            Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
            context.startActivity(alarmIntent);
        } else {
            Intent alarmIntent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
            alarmIntent.putExtra(AlarmClock.ALARM_SEARCH_MODE_LABEL, content);
            context.startActivity(alarmIntent);
        }
    }
}
