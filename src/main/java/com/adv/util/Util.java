package com.adv.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

	public Util() {
		super();
	}

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static Date getSearchDate(String date) {
		if (Util.isUnix()) {
			return DateUtil.currentTimeZoneDateFromString(date);
		} else {
			return getSearchDateFromString(date);
		}
	}

	public static Date getSearchDateFromString(String date) {
		try {
			SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			return parseFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String convertTimeStampToStringDate(long timeStamp) {
		if (timeStamp != 0L) {
			Date currentDate = new Date(timeStamp);
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			String newDate = df.format(currentDate);
			return newDate;
		} else {
			return "";
		}
	}

	public static String getTrimParam(String param) {
		if (param != null)
			return param.trim();
		else
			return "";
	}

	public static int getSixDigitRandomNumbers() {
		int response = (int) ((Math.random() * 900000) + 100000);
		return response;
	}

	public static String randomPassword(int len) {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%^&*_=+-/.?<>)";

		String values = Capital_chars + Small_chars + numbers + symbols;

		Random rndm_method = new Random();

		char[] password = new char[len];

		for (int i = 0; i < len; i++) {
			password[i] = values.charAt(rndm_method.nextInt(values.length()));

		}
		return String.valueOf(password);
	}

	public static String getFileType(Path path) {
		String fileType = "Undetermined";

		try {
			fileType = Files.probeContentType(path);

			if (fileType.contains("image"))
				return "image";
			else if (fileType.contains("video"))
				return "video";
			else if (fileType.contains("audio"))
				return "audio";
			else
				return "other";
		} catch (IOException ioException) {

			fileType = "other";
		} catch (NullPointerException e) {
			e.printStackTrace();

			fileType = "other";
		}
		return fileType;
	}

	public static int getFourDigitRandomNumbers() {
		return new Random().nextInt((9999 - 100) + 1) + 10;
	}

	public static String returnFileSlash() {
		if (isWindows()) {
			return "\\";
		} else {
			return "/";
		}
	}

	public static boolean isWindows() {
		return OS.contains("win");
	}

	public static boolean isMac() {
		return OS.contains("mac");
	}

	public static boolean isUnix() {
		return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
	}

	public static boolean isSolaris() {
		return OS.contains("sunos");
	}

	public static String ConvertMillisToDayHrMinSec(long timeInMilliSeconds) {
		if (timeInMilliSeconds > 0) {
			long secondsOld = timeInMilliSeconds / 1000;
			long minutesOld = secondsOld / 60;
			long hoursOld = minutesOld / 60;
			long days = hoursOld / 24;
			long minutes = minutesOld % 60;
			long hours = hoursOld % 24;
			long seconds = secondsOld % 60;

			String outputString = "";

			if (days == 0) {
				if (hours == 0 && minutes == 0)
					outputString = (seconds + "s");
				else if (hours == 0 && minutes != 0)
					outputString = (minutes + "m:" + seconds + "s");
				else
					outputString = (hours + "h:" + minutes + "m:" + seconds + "s");
			} else {
				outputString = days + "d:" + hours + "h:" + minutes + "m:" + seconds + "s";
			}
			return outputString;
		} else
			return "";
	}

	public static String getNullPointerExceptionMessage(NullPointerException e) {
		return "Null Pointer Occur at : " + e.getStackTrace()[0].getFileName() + " "
				+ e.getStackTrace()[0].getLineNumber();
	}

	public static String getFileTypeByContentType(String contentType) {

		if (contentType.contains("image"))
			return "image";
		else if (contentType.contains("video"))
			return "video";
		else if (contentType.contains("audio"))
			return "audio";
		else
			return "other";
	}

	public static boolean passwordValid(String trim) {

		return false;
	}
}