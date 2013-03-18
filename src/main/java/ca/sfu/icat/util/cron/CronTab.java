package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:05 PM
 */

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ToStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CronTab {

    @SuppressWarnings("UnusedDeclaration")
    private List entries;

   	@SuppressWarnings("UnusedDeclaration")
    public static CronTab fromURL(URL url) throws IOException {
   		return fromInputStream(url.openStream());
   	}

   	public static CronTab fromInputStream(InputStream is) throws IOException {
   		XStream xstream = getXStream();
   		return (CronTab)xstream.fromXML(is);
   	}

   	public static CronTab fromXML(String xml) throws IOException {
   		XStream xstream = getXStream();
   		return (CronTab)xstream.fromXML(xml);
   	}

   	private static XStream getXStream() {
   		XStream xstream = new XStream();
   		xstream.alias("CronTab", CronTab.class);
   		xstream.alias("CronTabEntry", CronTabEntry.class);
   		xstream.addImplicitCollection(CronTab.class, "entries", CronTabEntry.class);
   		try {
   			xstream.registerConverter(new ToStringConverter(CronMinuteField.class));
   			xstream.registerConverter(new ToStringConverter(CronHourField.class));
   			xstream.registerConverter(new ToStringConverter(CronDayOfWeekField.class));
   			xstream.registerConverter(new ToStringConverter(CronDayOfMonthField.class));
   			xstream.registerConverter(new ToStringConverter(CronMonthField.class));
   		} catch (NoSuchMethodException e) {
   			e.printStackTrace();
   		}
   		return xstream;
   	}

   	public List entries() {
   		return entries;
   	}

   	public List<CronTabEntry> entriesMatching(String hostname, String port, Date ts) {
       ArrayList<CronTabEntry> matched = new ArrayList<CronTabEntry>();
       for (Object entry1 : entries) {
           CronTabEntry entry = (CronTabEntry) entry1;
           if (!hostname.equalsIgnoreCase(entry.hostname())) continue;
           if (!port.equalsIgnoreCase(entry.port())) continue;
           if (!entry.matches(ts)) continue;
           matched.add(entry);
       }
       return matched;
    }
}
