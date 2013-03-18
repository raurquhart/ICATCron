package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 10:40 AM
 */

public class CronRange {
	private int start;
	private int max;
	private int increment;

	public CronRange(int start, int max, int increment) {
		if (max<start) throw new IllegalArgumentException("max < start");
		if (increment<1) throw new IllegalArgumentException("increment < 1");
		this.start = start;
		this.max = max;
		this.increment = increment;
	}

	public int start() {
		return start;
	}

	public int max() {
		return max;
	}

	public int increment() {
		return increment;
	}

	public boolean matches(int value) {
		if (value<start) return false;
		if (value>max) return false;
		for (int i=start; i<=max; i=i+increment) {
			if (value == i) return true;
			if (i >value) break;
		}
		return false;
	}

	public String toString() {
		if (increment==1) return start + "-" + max;
		return start + "-" + max + "/" + increment;
	}

}
