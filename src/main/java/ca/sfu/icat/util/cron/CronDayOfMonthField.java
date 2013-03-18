package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 11:07 AM
 */

public class CronDayOfMonthField  extends CronTimeField {

	private CronRange validRange = new CronRange(1,31, 1);

	public CronDayOfMonthField() {
		super();
	}

	public CronDayOfMonthField(String value) throws CronTimeFieldException {
		super();
		setCronTimeField(value);
	}


	public int validateValue(String s) throws CronTimeFieldException {
		if (s==null||s.trim().length()==0) throw new CronTimeFieldException("null or empty dayOfMonth value");
		int value;
		try {
			value = Integer.parseInt(s);
			if (validRange.matches(value)) return value;
			throw new CronTimeFieldException("dayOfMonth value out of range: " + validRange.toString() );
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException("Validation of dayOfMonth value failed", e);
		}
	}

	public CronRange validRange() {
		return validRange;
	}
}
