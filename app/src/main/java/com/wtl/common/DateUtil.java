package com.wtl.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYYMMDDWITHBACKSLASH= "yyyy/MM/dd";
	public static final String FORMAT_YYYYMMDDSUB = "yyyy-MM-dd";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDDHHMMSSss = "yyyy-MM-dd HH:mm:ss:SS";


	public static String getCurrentDateyyyyMMddHHmmssSS() {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSSss);
		return sf.format(new Date());
	}

	public static String getCurrentDateyyyyMMddHHmmss() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	public static String getCurrentDateYYYYMMDD() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(new Date());
	}

	public static String getDateYYYYMMDD() {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return sf.format(new Date());
	}

	public static Date paseDateyyyyMMddHHmmss(String strDate)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sf.parse(strDate);
		return date;
	}

	public static Date paseDateyyyyMMddHHmmss(String strDate, String format)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		sf.setLenient(false);
		Date date = sf.parse(strDate);
		return date;
	}

	public static String formatDate(String strDate, String formatBefore, String formatAfter)
			throws ParseException {
		if (strDate == null) {
			return "";
		}
		SimpleDateFormat sfBefore = new SimpleDateFormat(formatBefore);
		SimpleDateFormat sfAfter = new SimpleDateFormat(formatAfter);
		Date date = sfBefore.parse(strDate);
		return sfAfter.format(date);
	}
	public static String paseDateYYYYMMToDateYYYYMMDD(String time) {
		return time.substring(0, 10).replaceAll("-", "");
	}

	public static String paseDateYYYYMMToDateYYYYMMDDHHMMSS(String time) throws ParseException{
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sf2.format(sf1.parse(time+"000000"));
	}


	public static String paseDateYYYYMMDDHHMMSS(String time) throws ParseException{
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sf2 =new SimpleDateFormat("yyyyMMddHHmmss");

		return sf2.format(sf1.parse(time));
	}

	public static String paseDateYYYYMMDDHHmmssToDateYYYYMMDD(String time) {
		return time.substring(0, 10).replaceAll("-", "");
	}


	public static boolean compareDate(String strDate) throws ParseException {
		if (StringUtil.isNotEmpty(strDate)) {
			Date date = paseDateyyyyMMddHHmmss(strDate);
			Date currDate = new Date();
			if (((currDate.getTime() / 1000) - (date.getTime() / 1000)) < 60) {
				return false;
			}
		}
		return true;
	}

	public static boolean compareDate(String strDate, int seconds) throws ParseException {
		if (StringUtil.isNotEmpty(strDate)) {
			Date date = paseDateyyyyMMddHHmmss(strDate);
			Date currDate = new Date();
			if (((currDate.getTime() / 1000) - (date.getTime() / 1000)) < seconds) {
				return false;
			}
		}
		return true;
	}

	public static boolean compareInpAndCurr(String strDate)
			throws ParseException {
		Date date = paseDateyyyyMMddHHmmss(strDate);
		Date currDate = new Date();
		if (date.getTime() > currDate.getTime()) {
			return true;
		}
		return false;
	}

	public static boolean checkInputTimeThanCurrDate(String strDate)
			throws ParseException {
		Date date = paseDateyyyyMMddHHmmss(strDate);
		Date currDate = new Date();
		if (date.getTime() < currDate.getTime()) {
			return true;
		}
		return false;
	}

	public static boolean compareBiggerInpDates(String strDate, String strDateCompared, String format)
			throws ParseException {
		if (strDate == null || strDateCompared == null) {
			return false;
		}
		Date date = paseDateyyyyMMddHHmmss(strDate, format);
		Date dateCompared;
		if (strDateCompared.length() == 19) {
			dateCompared = paseDateyyyyMMddHHmmss(strDateCompared);
		} else {
			dateCompared = paseDateyyyyMMddHHmmss(strDateCompared, format);
		}
		if (date.getTime() > dateCompared.getTime()) {
			return true;
		}
		return false;
	}

	public static boolean compareLesserInpDates(String strDate, String strDateCompared, String format)
			throws ParseException {
		if (strDate == null || strDateCompared == null) {
			return false;
		}
		Date date = paseDateyyyyMMddHHmmss(strDate, format);
		Date dateCompared = paseDateyyyyMMddHHmmss(strDateCompared, format);
		if (date.getTime() < dateCompared.getTime()) {
			return true;
		}
		return false;
	}

	public static String getDateByFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String getDateByFormat(String format1, String format2,
										 String strDate) {
		try {
			if (StringUtil.isNotEmpty(format1)
					&& StringUtil.isNotEmpty(format2)
					&& StringUtil.isNotEmpty(strDate)) {

				SimpleDateFormat sdf = new SimpleDateFormat(format1);
				Date date = sdf.parse(strDate);
				return getDateByFormat(format2, date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getCurrentHHmmss() {
		return getDateByFormat("HHmmss", new Date());
	}
	public static long getQuot(String objectDt) {
		if (null == objectDt) {
			return 0;
		}
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date today = new Date();
			today= ft.parse(ft.format(today));
			Date date2 = ft.parse(objectDt);
			quot = (today.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static String getYearMonthDay(String str){

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sf.parse(str);
			if(null!=date){
				return sf.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}



	public static String converDate1(String dateStr) {
		String string = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtil.isNotBlank(dateStr)) {
				string = format1.format(format2.parse(dateStr));
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return string;
	}


	public static String converDate2(String dateStr) {
		String string = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtil.isNotBlank(dateStr)){
				string = format2.format(format1.parse(dateStr));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return string;
	}

	public static Date converDate3(String dateStr){
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}


	public static String getCurrentDateWeeHours(){
		String currentDate="";
		try {
			String startDateTmp=DateUtil.getCurrentDateYYYYMMDD();
			currentDate=DateUtil.paseDateYYYYMMToDateYYYYMMDDHHMMSS(startDateTmp);
		} catch (Exception e) {

		}
		return currentDate;

	}


	public static String getNewDateByAddDays(String originalDate,int days,int seconds){
		String targetDate="";
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(DateUtil.paseDateyyyyMMddHHmmss(originalDate));
			cal.add(Calendar.DAY_OF_MONTH, days);
			cal.add(Calendar.SECOND, seconds);
			targetDate=DateUtil.getDateByFormat("yyyy-MM-dd HH:mm:ss",cal.getTime());
		} catch (Exception e) {
		}
		return targetDate;
	}

	public static Integer discrepantDays(String startDate,String endDate,String format){
		try{
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			Date beginDate=sdf.parse(startDate);
			Date endDat=sdf.parse(endDate);
			long begin=beginDate.getTime();
			long end=endDat.getTime();
			long between_days=(end-begin)/(1000*3600*24);
			return Integer.valueOf(String.valueOf(between_days));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static String getRedeemNextDayDate(String format){
		Date date =new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1);
		date=calendar.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date) ;
	}

	public static boolean isValidDate(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {

			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {

			convertSuccess=false;
		}
		return convertSuccess;
	}


	public static boolean isDate(String date) {

		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(date);
		return mat.matches();
	}



	public static Date getDateAddN(Date date, int n) {
		if(date==null){
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);
		return calendar.getTime();
	}


	public static String getMonthFirstDay(Date date){
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		cDay.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}


	public static String getMonthLastDay(Date date){
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}

	public static String getSeasonFirstDay(Date date){
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.JANUARY);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.APRIL);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
			cDay.set(Calendar.MONTH, Calendar.JULY);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.OCTOBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));

		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}


	public static String getSeasonLastDay(Date date){
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){
			cDay.set(Calendar.MONTH, Calendar.MARCH);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){
			cDay.set(Calendar.MONTH, Calendar.JUNE);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
			cDay.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));

		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}

	public static String getYearFirstDay(Date date){
		Calendar cDay=Calendar.getInstance();
		cDay.setTime(date);
		int currentYear = cDay.get(Calendar.YEAR);
		cDay.clear();
		cDay.set(Calendar.YEAR, currentYear);
		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}


	public static String getYearLastDay(Date date){
		Calendar cDay=Calendar.getInstance();
		cDay.setTime(date);
		int currentYear = cDay.get(Calendar.YEAR);
		cDay.clear();
		cDay.set(Calendar.YEAR, currentYear);
		cDay.roll(Calendar.DAY_OF_YEAR, -1);

		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_YYYYMMDDSUB);
		return fomat.format(cDay.getTime());
	}

}

