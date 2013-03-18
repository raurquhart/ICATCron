package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 12:41 AM
 */

public class CronTimeFieldException extends Exception {

	public CronTimeFieldException(String s) {
		super(s);
	}

	public CronTimeFieldException(Exception e) {
		super(e);
	}

	public CronTimeFieldException(String msg, Exception e) {
		super(msg, e);
	}
}
