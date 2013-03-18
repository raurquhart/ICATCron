package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:46 PM
 */

import java.util.List;

public class CronDefaultDelegate implements CronDelegate {

    public boolean cronWillStart() {
        return true;
    }

    public List crontabFileURLs() {
        return null;
    }
}
