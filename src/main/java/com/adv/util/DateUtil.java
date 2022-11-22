package com.adv.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public DateUtil() {
		super();
	}

	public static boolean findMinTimeBetweenTwoDates(long difference) {
		boolean isMinTimeCurrect = false;

		try {
			long difference_In_Minutes = (difference / 1000) / 60;
			int hours = 10;
			long minutes = hours * 60;

			if (difference_In_Minutes >= 30 && difference_In_Minutes <= minutes) {
				isMinTimeCurrect = true;
			} else
				isMinTimeCurrect = false;

		} catch (Exception e) {
			e.printStackTrace();
			isMinTimeCurrect = false;
		}
		return isMinTimeCurrect;
	}
	
	public static Date currentTimeZoneDateFromString(String dateString) {
		try {
			SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date date = parseFormat.parse(dateString);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, -5);
			calendar.add(Calendar.MINUTE, -30);
			
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}
}