package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:56 PM
 */

public class CronMinuteField extends CronTimeField {

	private CronRange validRange = new CronRange(0,59,1);

	public CronMinuteField() {
		super();
	}

	public CronMinuteField(String value) throws CronTimeFieldException {
		super();
		setCronTimeField(value);
	}

	public int validateValue(String s) throws CronTimeFieldException {
		if (s==null||s.trim().length()==0) throw new CronTimeFieldException("null or empty minute value");
		int value;
		try {
			value = Integer.parseInt(s);
			if (validRange.matches(value)) return value;
			throw new CronTimeFieldException("minute value out of range: " + validRange.toString() );
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException("Validation of minute value failed", e);
		}
	}

	public CronRange validRange() {
		return validRange;
	}
}
