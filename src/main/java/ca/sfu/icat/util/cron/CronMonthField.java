package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:17 PM
 */

import java.util.ArrayList;
import java.util.Arrays;

public class CronMonthField extends CronTimeField {

	private CronRange validRange = new CronRange(1,12,1);
	private ArrayList<String> monthNames = new ArrayList<String>(Arrays.asList(new String [] {"jan", "feb", "mar",
            "apr", "may", "jun","jul","aug","sep","oct","nov","dec"}));


	public CronMonthField() {
		super();
	}

	public CronMonthField(String value) throws CronTimeFieldException {
		super();
		setCronTimeField(value);
	}


	public int validateValue(String s) throws CronTimeFieldException {
		if (s==null||s.trim().length()==0) throw new CronTimeFieldException("null or empty month value");
		int value = 1 + monthNames.indexOf(s.trim().toLowerCase());
		if (value>0) return value;
		try {
			value = Integer.parseInt(s);
			if (validRange.matches(value)) return value;
			throw new CronTimeFieldException("month value out of range: " + validRange.toString() );
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException("Validation of month value failed", e);
		}
	}

	public CronRange validRange() {
		return validRange;
	}}
