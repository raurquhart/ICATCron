package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 7:14 PM
 */

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static junit.framework.TestCase.*;

/**
 * Tests for CronTab and CronTabEntry.
 * This tests both classes because they are so closely related, and the only way to
 * really test CronTab included testing CronTabEntry.
 */
@SuppressWarnings("UnusedDeclaration")
public class TestCronTab {

    private static Logger logger = Logger.getLogger(TestCronTab.class);

   	private static final String xml = "<CronTab>" +
   									  "<CronTabEntry>" +
   									  " <hostname>garibaldi1</hostname>" +
   									  " <port>3</port>" +
   									  " <name>test1</name>" +
   									  " <descr>test entry 1</descr>" +
   									  " <minute>1</minute>" +
   									  " <hour>*</hour>" +
   									  " <dayOfWeek>Mon</dayOfWeek>" +
   									  " <month>*</month>" +
   									  " <dayOfMonth>*</dayOfMonth>" +
   									  " <className>TestClass</className>" +
   									  " <methodName>theMethod</methodName>" +
   									  "</CronTabEntry>" +
   									  "<CronTabEntry>" +
   									  " <hostname>garibaldi2</hostname>" +
   									  " <port>4</port>" +
   									  " <name>test2</name>" +
   									  " <descr>test entry 2</descr>" +
   									  " <minute>0</minute>" +
   									  " <hour>*/2</hour>" +
   									  " <dayOfWeek>Mon-Fri</dayOfWeek>" +
   									  " <month>*</month>" +
   									  " <dayOfMonth>*</dayOfMonth>" +
   									  " <className>TestClass2</className>" +
   									  " <methodName>otherMethod</methodName>" +
   									  "</CronTabEntry>" +
   									  "</CronTab>";

    @Test
   	public void testParseXML() {
   		try {
   			CronTab crontab = CronTab.fromInput(xml);
   			List entries = crontab.entries();
   			assertEquals("Incorrect number of entries",2,entries.size());
   			assertTrue("Not an CronTabEntry", (entries.get(0) instanceof CronTabEntry) );
   			CronTabEntry entry = (CronTabEntry)entries.get(0);
   			assertEquals("Incorrect hostname", "garibaldi1", entry.hostname());
   			assertEquals("Incorrect port", "3", entry.port());
   			assertEquals("Incorrect name", "test1", entry.name());
   			assertEquals("Incorrect description", "test entry 1", entry.descr());
   			assertEquals("Incorrect minute value", "1", entry.minute().toString());
   			assertTrue( entry.hour().isStar());
   			assertEquals( "Incorrect dayOfWeek value","1", entry.dayOfWeek().toString());
   			assertTrue( entry.month().isStar());
   			assertTrue( entry.dayOfMonth().isStar());
   			assertEquals("Incorrect className", "TestClass", entry.className());
   			assertEquals("Incorrect methodName", "theMethod", entry.methodName());

   			entry = (CronTabEntry)entries.get(1);
   			assertEquals("Incorrect hostname", "garibaldi2", entry.hostname());
   			assertEquals("Incorrect port", "4", entry.port());
   			assertTrue(entry.minute().matches(0));
   			assertFalse(entry.minute().matches(59));
   			assertTrue(entry.hour().matches(0));
   			assertFalse(entry.hour().matches(1));
   			assertTrue(entry.dayOfWeek().matches(1));
   			assertTrue(entry.dayOfWeek().matches(2));
   			assertTrue(entry.dayOfWeek().matches(3));
   			assertTrue(entry.dayOfWeek().matches(4));
   			assertTrue(entry.dayOfWeek().matches(5));
   			assertFalse(entry.dayOfWeek().matches(6));
   			assertFalse(entry.dayOfWeek().matches(7));
   			assertTrue( entry.month().isStar());
   			assertTrue(entry.month().matches(1));
   			assertTrue(entry.month().matches(12));
   		} catch (IOException e) {
   			e.printStackTrace();
   			fail();
   		}
   	}

   	public void testMatch() {
   		try {
   			CronTab crontab = CronTab.fromInput(xml);
               Date ts = getCal().getTime();
   			ArrayList<CronTabEntry> entries = new ArrayList<CronTabEntry>(crontab.entriesMatching("garibaldi1","3", ts));
   			assertNotNull(entries);
   			assertEquals(1,entries.size());
   			CronTabEntry entry = entries.get(0);
   			assertEquals("Incorrect hostname", "garibaldi1", entry.hostname());
   			assertEquals("Incorrect port", "3", entry.port());
   			assertEquals("Incorrect name", "test1", entry.name());
   		} catch (IOException e) {
   			e.printStackTrace();
   			fail();
   		}

   	}

   	public void testGregCal() {
   		String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
   		// create a Pacific Standard Time time zone
   		SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

   		// set up rules for daylight savings time
   		pdt.setStartRule(Calendar.MARCH, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
   		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

   		// create a GregorianCalendar with the Pacific Daylight time zone
   		// and the current date and time
   		GregorianCalendar cal = new GregorianCalendar(pdt);
   		cal.setTime(new Date());
   		int minute = cal.get(GregorianCalendar.MINUTE);
   		int hourOfDay = cal.get(GregorianCalendar.HOUR_OF_DAY);
   		int dayOfMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
   		int dayOfWeek  = cal.get(GregorianCalendar.DAY_OF_WEEK);
   		int month = cal.get(GregorianCalendar.MONTH);
   		logger.info("minute: " + minute);
   		logger.info("hour:   " + hourOfDay);
   		logger.info("dayOfMonth: " + dayOfMonth);
   		logger.info("dayOfWeek: " + dayOfWeek);
   		logger.info("month:   " + month);

   	}

   	private GregorianCalendar getCal() {
   		String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
   		// create a Pacific Standard Time time zone
   		SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

   		// set up rules for daylight savings time
   		pdt.setStartRule(Calendar.MARCH, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
   		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

   		// create a GregorianCalendar with the Pacific Daylight time zone
   		// and the current date and time
   		GregorianCalendar calendar = new GregorianCalendar(pdt);
   		Date trialTime = new Date();
   		calendar.setTime(trialTime);
   		calendar.set(GregorianCalendar.MINUTE,1);
   		//calendar.set(GregorianCalendar.HOUR_OF_DAY,1);
   		//calendar.set(GregorianCalendar.DAY_OF_MONTH,11);
   		//calendar.set(GregorianCalendar.MONTH,Calendar.MARCH);
   		return calendar;
   	}
}
