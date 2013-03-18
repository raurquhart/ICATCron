package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:38 PM
 */

public class CronHourField extends CronTimeField {

	private CronRange validRange = new CronRange(0,23,1);

	public CronHourField() {
		super();
	}

	public CronHourField(String value) throws CronTimeFieldException {
		super();
		setCronTimeField(value);
	}

	public int validateValue(String s) throws CronTimeFieldException {
		if (s==null||s.trim().length()==0) throw new CronTimeFieldException("null or empty hour value");
		int value;
		try {
			value = Integer.parseInt(s);
			if (validRange.matches(value)) return value;
			throw new CronTimeFieldException("hour value out of range: " + validRange.toString() );
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException("Validation of hour value failed", e);
		}
	}

	public CronRange validRange() {
		return validRange;
	}}
