package com.ironant.common.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 普�?工具�?源码反编译处理的
 * @author asdc
 *
 */

public class CommonUtil {

	public static String getNowTime() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		Date localDate = Calendar.getInstance().getTime();
		return localSimpleDateFormat.format(localDate);
	}
	
	public static String getTime() {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"HH:mm:ss");
		Date localDate = Calendar.getInstance().getTime();
		return localSimpleDateFormat.format(localDate);
	}

	public static String getNowTime(String paramString) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString);
		Date localDate = Calendar.getInstance().getTime();
		return localSimpleDateFormat.format(localDate);
	}


	public static boolean hasSDCard() {
		String str = Environment.getExternalStorageState();
		return "mounted".equals(str);
	}



	public static boolean isEmpty(String paramString) {
		if (paramString != null) {
			String str = paramString.trim();
			if (!"".equals(str))
				return true;
		}
		return false;
	}


	public static boolean isNotEmpty(String paramString) {
		if ((paramString == null) || ("".equals(paramString)))
			return true;
		return false;
		
	
	}

	public static boolean isNumeric(String paramString) {
		return Pattern.compile("[0-9]*").matcher(paramString).matches();
	}

	public static boolean isString(String paramString) {
		return Pattern.compile("[a-zA-Z]*").matcher(paramString).matches();
	}

	public static boolean isValidEmail(String paramString) {
		if (isEmpty(paramString))
			;
		for (boolean bool = false;; bool = Pattern
				.matches(
						"[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}",
						paramString))
			return bool;
	}

	public static boolean isValidMobiNumber(String paramString) {
		if (isEmpty(paramString))
			;
		for (boolean bool = false;; bool = Pattern.matches("^1\\d{10}$",
				paramString))
			return bool;
	}
	
	public static boolean isValidPhoneNumber(String paramString) {
		if (!isEmpty(paramString)){
			
			if ((Pattern.matches("[0]{1}[0-9]{2,3}-[0-9]{7,8}", paramString))
					&& (Pattern.matches(
							"[0]{1}[0-9]{2,3}-[0-9]{7,8}-[0-9]{1,4}",
							paramString))){
				
				return true;
			}
			
		}
		return false;
	}


	private static String printErrorLog(Context paramContext) {
		return "";
	}


	public static void setListViewHeightBasedOnChildren(ListView paramListView) {
		ListAdapter localListAdapter = paramListView.getAdapter();
		if (localListAdapter == null)
			return;
		int i = 0;
		int j = 0;
		while (true) {
			int k = localListAdapter.getCount();
			if (j >= k) {
				ViewGroup.LayoutParams localLayoutParams = paramListView
						.getLayoutParams();
				int m = paramListView.getDividerHeight();
				int n = localListAdapter.getCount() + -1;
				int i1 = m * n + i;
				localLayoutParams.height = i1;
				paramListView.setLayoutParams(localLayoutParams);
				return;
			}
			View localView = localListAdapter.getView(j, null, paramListView);
			localView.measure(0, 0);
			int i2 = localView.getMeasuredHeight();
			i += i2;
			j += 1;
		}
	}

	public static String stringFilterNull(String paramString) {
		if (paramString == null)
			paramString = "";
		return paramString;
	}

	public static void showToast(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	public static Toast showToast(Context context, String str, int i) {
		Toast makeText = Toast.makeText(context, str, i);
		makeText.show();
		return makeText;

	}


	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getYearMouthDayTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");//设置日期格式
		// new Date()为获取当前系统时间
		return df.format(new Date());
	}
	static String action = "com.elephant.notice.broadcast";
	/**
	 * 设置闹钟
	 * @param hourOfDay
	 * @param minute
	 * @return
	 */
	public static String setAlam(int hourOfDay, int minute, Context context, int id) {
		// TODO
//		Intent intent = new Intent(context, PenaltyAlarmActivity.class);

		Intent intent = new Intent(action);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
//		PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
		
		Date date = null;
		try {
			date = df.parse(CommonUtil.getYearMouthDayTime()+hourOfDay+"："+minute+"：00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);

		String alarm_text = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(new Date(c
				.getTimeInMillis()));

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		if(hourOfDay < CommonUtil.getHourTime() || (hourOfDay == CommonUtil.getHourTime() && minute < CommonUtil.getMinutesTime())){
			alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+24*60*60*1000, pi);
		}else{
			alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
		}
		
		LogUtil.e("设置闹钟=" +df.format(new Date(c.getTimeInMillis())));
		
		return alarm_text;
	}

	/**
	 * 得到当前是几点
	 * @return
	 */
	public static int getHourTime(){
		SimpleDateFormat df = new SimpleDateFormat("HH");//设置日期格式
		// new Date()为获取当前系统时间
		return Integer.parseInt(df.format(new Date()));
	}



	/**
	 * 得到当前是几分
	 * @return
	 */
	public static int getMinutesTime(){
		SimpleDateFormat df = new SimpleDateFormat("mm");//设置日期格式
		// new Date()为获取当前系统时间
		return Integer.parseInt(df.format(new Date()));
	}


	/**
	 * 设置 消息 闹钟 
	 * @param hourOfDay
	 * @param minute
	 * @return
	 */
//	public static String setAlamMessage(int hourOfDay, int minute,Context context,int id) {
//		// TODO
//		Intent intent = new Intent(context, K8Activity.class);
////		Intent intent = new Intent(context, PenaltyAlarmActivity.class);
//
//		PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
//
//		Date date = null;
//		try {
//			date = df.parse(CommonUtil.getYearMouthDayTime()+hourOfDay+"："+minute+"：00");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(System.currentTimeMillis());
//		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//		c.set(Calendar.MINUTE, minute);
//
//		String alarm_text = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(new Date(c
//				.getTimeInMillis()));
//
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//		if(hourOfDay < CommonUtil.getHourTime() || (hourOfDay == CommonUtil.getHourTime() && minute < CommonUtil.getMinutesTime())){
//			alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+24*60*60*1000, pi);
//		}else{
//			alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
//		}
//
//		LogUtil.e("设置K8闹钟=" +df.format(new Date(c.getTimeInMillis())));
//
//		return alarm_text;
//	}
	
	
//
//	public static String setAlamMessageTomo(int hourOfDay, int minute,Context context,int id) {
//		// TODO
//		Intent intent = new Intent(context, K8Activity.class);
////		Intent intent = new Intent(context, PenaltyAlarmActivity.class);
//
//		PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
//
//		Date date = null;
//		try {
//			date = df.parse(CommonUtil.getYearMouthDayTime()+hourOfDay+"："+minute+"：00");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(System.currentTimeMillis());
//		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//		c.set(Calendar.MINUTE, minute);
//
//		String alarm_text = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(new Date(c
//				.getTimeInMillis()));
//
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//		alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+24*60*60*1000, pi);
//
////		LogUtil.e("设置K8闹钟=" +df.format(new Date(c.getTimeInMillis())));
//
//		return alarm_text;
//	}
	
	/**
	 * @return 返回包含所有包名的字符串列表
	 */
	public static List<String> isRunning(Context ctx) {
		List<String> names = new ArrayList<String>() ;
		ActivityManager mActivityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
                .getRunningAppProcesses();  
  
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList) {
        	
            String processName = appProcess.processName; // 进程名
//            LogUtil.e(processName);
//            String[] pkgNameList = appProcess.pkgList; // 获得运行在该进程里的所有应用程序包  
//  
//            // 输出所有应用程序的包名  
//            for (int i = 0; i < pkgNameList.length; i++) {  
//                String pkgName = pkgNameList[i];  
//            }  
            names.add(processName);
        }  
		return names;
	}
	
	
	public static void closeBoard(Context mcontext) {
		  InputMethodManager imm = (InputMethodManager) mcontext
		    .getSystemService(Context.INPUT_METHOD_SERVICE);
		  // imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		  if (imm.isActive())  //一直是true
		   imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
		     InputMethodManager.HIDE_NOT_ALWAYS);
	}
//
//	public static void CancleAlarm(Context context,int id){
//		Intent intent = new Intent(context, PenaltyAlarmActivity.class);
//		PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		alarmManager.cancel(pi);
//	}
//
//	public static void CancleAlarmK8(Context context,int id){
//		Intent intent = new Intent(context, K8Activity.class);
//		PendingIntent pi = PendingIntent.getActivity(context, id, intent, 0);
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		alarmManager.cancel(pi);
//	}

	/**
	 * 获取到服务端的cookie
	 * @return
	 * @throws Exception
	 */
	public static String getLoadCookieUrlConnection() throws Exception {
		
		URL loginUrl = new URL("http://122.225.104.46:8080/upload/loginop.php");
		
		HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(false);
		
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Connection", "keep-alive");
//		connection.setRequestProperty("Cookie", "PHPSESSID=22sq9belbd8fddaudj5hkse351");
		
		connection.connect();
		
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		
		String content = URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode("qinyi365","UTF-8");
		
		content += "&"+ URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode("qinyi365","UTF-8");
		content += "&"+ URLEncoder.encode("submit01","UTF-8")+"="+ URLEncoder.encode("%CC%E1%BD%BB","UTF-8");
		
		out.writeBytes(content);
		
		out.flush();
		out.close();
		
		String key = "";
		
//		Map<String, List<String>> headerFields = connection.getHeaderFields();
//		sessionId = headerFields.get("Set-Cookie");
		
		String sessionId = null;
		if(connection != null){
			for(int i = 1;(key = connection.getHeaderFieldKey(i))!= null;i++ ){
				
				if(key.equalsIgnoreCase("Set-Cookie")){
					sessionId  = connection.getHeaderField(key);
					sessionId = sessionId.substring(0,sessionId.indexOf(";"));
				}
			}
		}
		connection.disconnect();
		return sessionId;
	}

	/**
	 *
	 * @param tvGmTimeType
	 * @param textBackColor
	 * @param roundRadius 圆角半径
	 */
	public static void setTextBackColor(TextView tvGmTimeType, int textBackColor, int roundRadius) {
//		int roundRadius = 15; // 8dp
//		int fillColor = Color.parseColor();//内部填充颜色
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(textBackColor);
		gd.setCornerRadius(roundRadius);

		tvGmTimeType.setBackgroundDrawable(gd);
	}
}
