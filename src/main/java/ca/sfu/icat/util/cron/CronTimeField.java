package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 10:29 AM
 */

import java.util.ArrayList;

public abstract class CronTimeField {

	private ArrayList<Object> timeSpecArray;
	private boolean isStar = false;

	public CronTimeField() {
		super();
	}

	public void setCronTimeField(String cronTimeField) throws CronTimeFieldException {
		parseTimeSpecification(cronTimeField);
	}

	public boolean matches(int timeValue) {
		for (Object spec : timeSpecArray) {
			if (spec instanceof Integer) {
				if (((Integer)spec).equals(timeValue)) return true;
			} else if (spec instanceof CronRange) {
				if (((CronRange)spec).matches(timeValue)) return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Object spec : timeSpecArray) {
			buf.append(spec.toString()).append(",");
		}
		buf.deleteCharAt(buf.length()-1);
		return buf.toString();
	}

	public boolean isStar() {
		return isStar;
	}

	public abstract int validateValue(String s) throws CronTimeFieldException;
	public abstract CronRange validRange();

	private void parseTimeSpecification(String spec) throws CronTimeFieldException {
		if (spec==null) throw new CronTimeFieldException("Timefield specification is null");
		timeSpecArray = new ArrayList<Object>();
		if (spec.trim().equals("*")) {
			isStar = true;
		}
		if (spec.contains("*")) {
			spec = spec.trim().replace("*",validRange().toString());
		}
		try {
			String [] specs = spec.split(",");
			for (String s : specs) {
				Object timespec;
				if (s.contains("-")) timespec = parseRange(s);
				else {
					timespec = new Integer(validateValue(s));
				}
				timeSpecArray.add(timespec);
			}
		} catch (CronTimeFieldException e) {
			throw e;
		} catch (NumberFormatException e) {
			throw new CronTimeFieldException(e);
		}
	}

	private CronRange parseRange(String s) throws CronTimeFieldException {
		int increment = 1;
		if (s.contains("/")) {
			String [] vals = s.split("/");
			if (vals.length!=2) throw new CronTimeFieldException("Invalid range format: " + s);
			try {
				increment = Integer.parseInt(vals[1]);
			} catch (NumberFormatException e) {
				throw new CronTimeFieldException("Invalid range format: " + s);
			}
			s = vals[0];
		}
		String [] vals = s.split("-");
		if (vals.length!=2) throw new CronTimeFieldException("Invalid range format: " + s);
		int start = validateValue(vals[0]);
		int max = validateValue(vals[1]);
		//if (val0>=val1) throw new ERXCronTimeFieldException("Invalid range format: " + s);
		return new CronRange(start,max,increment);
	}

}
