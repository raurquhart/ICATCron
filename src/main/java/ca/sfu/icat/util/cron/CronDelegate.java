package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:38 PM
 */

public interface CronDelegate {
    public boolean cronWillStart();
    public Object getCrontab();
}
