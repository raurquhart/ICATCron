package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-19
 * Time: 9:27 AM
 */

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCron {

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


    /**
     * This test may take up to 60 seconds to complete.
     */
    @Test
    public void testSleepToTopOfMinute() {
        System.out.println("Note: This test may take up to 60 seconds to complete!");
        Cron cron = new Cron("3");
        cron.sleepToTopOfMinute();
        GregorianCalendar myCalendar = new GregorianCalendar();
      	myCalendar.setTime(new Date());
      	assertEquals("Actual minute value should be 0", 0,myCalendar.get(GregorianCalendar.SECOND));
    }

    @Test
    public void testGetCrontabWithXML() {
        CronDelegate mockDelegate = mock(CronDelegate.class);
        when(mockDelegate.getCrontab()).thenReturn(xml);
        Cron cron = new Cron("4");
        cron.setDelegate(mockDelegate);
        CronTab crontab = cron.getCronTab();
        assertNotNull(crontab);
        assertEquals(2, crontab.entries().size());
        CronTabEntry entry = (CronTabEntry) crontab.entries().get(0);
        assertEquals("TestClass",entry.className());
        assertTrue(entry.month().isStar());
    }

    @Test
    public void testGetCrontabWithInputStream() {
        // Set up a mock delegate object that will return an InputStream
        CronDelegate mockDelegate = mock(CronDelegate.class);
        try {
            when(mockDelegate.getCrontab()).thenReturn(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail();
        }
        Cron cron = new Cron("4");
        cron.setDelegate(mockDelegate);
        CronTab crontab = cron.getCronTab();
        assertNotNull(crontab);
        assertEquals(2, crontab.entries().size());
        CronTabEntry entry = (CronTabEntry) crontab.entries().get(0);
        assertEquals("TestClass",entry.className());
        assertTrue(entry.month().isStar());
    }

    /**
     * This will only run correctly on Rob's machine.
     * It has been quarantined on Bamboo so that it doesn't stop the build.
     */
    @Test
    public void testHostname() {
        String hostname = new Cron("test").hostname();
        System.out.println("hostname: " + hostname);
        assertEquals("icat-rob-macpro.its.sfu.ca", new Cron("test").hostname());
    }
}
