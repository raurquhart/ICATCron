package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:48 PM
 */

import java.util.ArrayList;
import java.util.Arrays;

public class CronDayOfWeekField extends CronTimeField {

	private CronRange validRange = new CronRange(1,7,1);
	private ArrayList<String> weekdayNames = new ArrayList<String>(Arrays.asList( new String [] {"mon", "tue", "wed", "thu", "fri", "sat","sun"}));

	public CronDayOfWeekField() {
		super();
	}

	public CronDayOfWeekField(String value) throws CronTimeFieldException {
		super();
		setCronTimeField(value);
	}


	public int validateValue(String s) throws CronTimeFieldException {
		if (s==null||s.trim().length()==0) throw new CronTimeFieldException("null or empty month value");
		int value = 1 + weekdayNames.indexOf(s.trim().toLowerCase());
		if (value>0) return value;
		try {
			value = Integer.parseInt(s);
			if (validRange.matches(value)) return value;
			throw new CronTimeFieldException("dayOfWeek value out of range: " + validRange.toString() );
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException("Validation of dayOfWeek value failed", e);
		}
	}

	public CronRange validRange() {
        return validRange;
    }
}
