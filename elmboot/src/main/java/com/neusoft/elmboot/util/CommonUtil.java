package com.neusoft.elmboot.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
	
	public static long validDate = 3600000;

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm::ss");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	public static long dateToStamp(String str) {
		long ts = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm::ss");
			Date date = simpleDateFormat.parse(str);
			ts = date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts/1000;
	}
}
