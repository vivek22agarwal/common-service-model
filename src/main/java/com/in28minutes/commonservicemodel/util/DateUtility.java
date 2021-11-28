package com.in28minutes.commonservicemodel.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	
	public static final String DATE_FORMAT = "YYYY-MM-DD";
	public static Date fromStringToDate(String dt) {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date date = null;
		try {
			date = format.parse(dt);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
