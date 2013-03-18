package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:44 PM
 */

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class Cron extends Thread {
    public static final Logger logger = Logger.getLogger(Cron.class);
   	public static boolean started = false;
    public static boolean isTerminating = false;

   	public String hostname;
   	public String instanceIdentifier;
   	public CronDelegate delegate = new CronDefaultDelegate();

    public Cron(String instanceIdentifier) {
        this.instanceIdentifier = instanceIdentifier;
    }

   	public static void startCronDaemon(String instanceIdentifier) {
   		Cron cron = new Cron(instanceIdentifier);
   		cron.start();
   	}

    private class CronShutdownHook extends Thread {
        public void run() {
            isTerminating = true;
        }
    }

    public void run() {
        // Exit if cron already started
        if (Cron.started) {
            logger.info("Cron already running. Exiting.");
            return;
        }

        // Get the hostname
        hostname = hostname();
        if (hostname() == null) {
            logger.info("Cron could not determine hostname. Exiting.");
            return;
        }
        if (!delegate.cronWillStart()) return;
        logger.info("Cron starting for instance " + instanceIdentifier + " on host " + hostname);
        Runtime.getRuntime().addShutdownHook(new CronShutdownHook());
        Cron.started = true;

        while (true) {
            if (!sleepToTopOfMinute()) {
                if (Cron.isTerminating) return;
                continue;
            }
            Date now = new Date();

            // exit if app is terminating
            if (Cron.isTerminating) return;

            List<URL> crontabs = delegate.crontabFileURLs();
            if (crontabs == null) continue;
            for (URL url : crontabs) {
                // get crontab
                CronTab crontab = null;
                try {
                    crontab = CronTab.fromURL(url);
                } catch (IOException e) {
                    logger.error("Error getting crontab from resource: " + e.getMessage());
                    e.printStackTrace();
                }
                if (crontab == null) continue;
                // for each crontab entry:
                //   if it matches the current timestamp:
                //   then run the class.method in a new thread, and log it
                List<CronTabEntry> entries = crontab.entriesMatching(hostname, instanceIdentifier, now);
                for (CronTabEntry entry : entries) {
                    logger.info("Running thread for " + entry.name());
                    new CronThread(entry.className(), entry.methodName()).start();
                }
            }
        }
    }

    public CronDelegate delegate() {
   		return delegate;
   	}

   	public void setDelegate(CronDelegate delegate) {
   		this.delegate = delegate;
   	}

   	private boolean sleepToTopOfMinute() {
   		// Calculate delay til top of minute
   		GregorianCalendar myCalendar = new GregorianCalendar();
   		myCalendar.setTime(new Date());
   		int secOffset = 59 - myCalendar.get(GregorianCalendar.SECOND);
   		int milliOffset = 1000 - myCalendar.get(GregorianCalendar.MILLISECOND);
   		int delay = (1000 * secOffset) + milliOffset;
   		// Sleep for delay
   		try {
   			Thread.sleep(delay);
   		} catch (InterruptedException e) {
   			logger.info("Thread.sleep() threw InterruptedException: " + e.getMessage());
   			return false;
   		}
   		return true;
   	}

    private String hostname() {
        try {
            InetAddress iAddr = InetAddress.getLocalHost();
            return iAddr.getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            Process process = Runtime.getRuntime().exec("/usr/bin/hostname");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return stdInput.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
