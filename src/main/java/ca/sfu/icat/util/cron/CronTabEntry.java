package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:02 PM
 */

import java.util.Date;
import java.util.GregorianCalendar;

public class CronTabEntry {

    private String name;
   	private String descr;
   	private CronMinuteField minute;
   	private CronHourField hour;
   	private CronDayOfMonthField dayOfMonth;
   	private CronMonthField month;
   	private CronDayOfWeekField dayOfWeek;
   	private String className;
   	private String methodName;
   	private String hostname;
   	private String port;

   	public boolean matches(Date ts) {
   		GregorianCalendar cal = new GregorianCalendar();
   		cal.setTime(ts);
   		int minute = cal.get(GregorianCalendar.MINUTE);
   		int hourOfDay = cal.get(GregorianCalendar.HOUR_OF_DAY);
   		int dayOfMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
   		int dayOfWeek  = cal.get(GregorianCalendar.DAY_OF_WEEK) - 1;
   		int month = 1 + cal.get(GregorianCalendar.MONTH);

   		if (this.minute().matches(minute) && this.hour().matches(hourOfDay) && this.month().matches(month)) {
   			if (this.dayOfMonth().isStar()) {
   				return this.dayOfWeek().matches(dayOfWeek);
   			} else if (this.dayOfWeek().isStar()) {
   				return this.dayOfMonth().matches(dayOfMonth);
   			} else {
   				return this.dayOfWeek().matches(dayOfWeek) || this.dayOfMonth().matches(dayOfMonth);
   			}
   		}
   		return false;
   	}

   	public String name() {
   		return name;
   	}

   	public String descr() {
   		return descr;
   	}

   	public CronMinuteField minute() {
   		return minute;
   	}

   	public CronHourField hour() {
   		return hour;
   	}

   	public CronDayOfMonthField dayOfMonth() {
   		return dayOfMonth;
   	}

   	public CronMonthField month() {
   		return month;
   	}

   	public CronDayOfWeekField dayOfWeek() {
   		return dayOfWeek;
   	}

   	public String className() {
   		return className;
   	}

   	public String methodName() {
   		return methodName;
   	}

   	public String hostname() {
   		return hostname;
   	}

   	public String port() {
   		return port;
   	}
}
