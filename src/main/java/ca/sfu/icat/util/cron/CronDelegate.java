package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:38 PM
 */

import java.net.URL;
import java.util.List;

public interface CronDelegate {
    public boolean cronWillStart();
   	public List<URL> crontabFileURLs();
}
