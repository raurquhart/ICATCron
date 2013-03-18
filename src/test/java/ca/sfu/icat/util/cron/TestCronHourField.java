package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:42 PM
 */

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestCronHourField {


   	@SuppressWarnings("UnusedDeclaration")
       @Test
   	public void testStarField() {
   		try {
   			CronHourField field = new CronHourField("*");
   		} catch (CronTimeFieldException e) {
   			fail("Couldn't create new CronMinuteField with \"*\" param");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testNullOrEmpty() {
   		try {
   			CronHourField field = new CronHourField();
   			int result = field.validateValue(null);
   			fail("CronHourField validated null param and returned: " + result);
   			result = field.validateValue(" ");
   			fail("CronHourField validated \" \" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testInRange() {
   		try {
   			CronHourField field = new CronHourField();
   			for (int i=0; i<24; i++) {
   				assertEquals(i,field.validateValue(String.valueOf(i)));
   			}
   			assertEquals(0,field.validateValue("00"));
   			assertEquals(23,field.validateValue("23"));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testOutOfRange() {
   		try {
   			CronHourField field = new CronHourField();
   			int result = field.validateValue("60");
   			fail("CronHourField validated \"60\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronHourField field = new CronHourField();
   			int result = field.validateValue("-1");
   			fail("CronHourField validated \"-1\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@SuppressWarnings("UnusedDeclaration")
       @Test
   	public void testNonNumeric() {
   		try {
   			CronHourField field = new CronHourField();
   			int result = field.validateValue("10m");
   			fail("CronHourField validated \"m10\" param and returned: " + result);
   			result = field.validateValue("all");
   			fail("CronHourField validated \"all\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronHourField field = new CronHourField("all");
   			fail("Created CronHourField with \"all\" param");
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testConstructor() {
   		try {
   			CronHourField field = new CronHourField("01");
   			assertEquals("1", field.toString());
   			field = new CronHourField("0,15,23");
   			assertEquals("0,15,23", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertFalse("field matches 32",field.matches(32));

   			field = new CronHourField("0-23");
   			assertEquals("0-23", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertTrue(field.matches(0));
   			assertFalse(field.matches(24));

   			field = new CronHourField("0-23/2");
   			assertEquals("0-23/2", field.toString());
   			assertTrue("field does not match 16",field.matches(16));
   			assertTrue(field.matches(0));
   			assertFalse(field.matches(3));
   			assertFalse(field.matches(32));

   			field = new CronHourField("2-5,7-10");
   			assertEquals("2-5,7-10", field.toString());
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertTrue("field does not match 7",field.matches(7));
   			assertTrue("field does not match 8",field.matches(8));
   			assertTrue("field does not match 9",field.matches(9));
   			assertTrue("field does not match 10",field.matches(10));
   			assertFalse(field.matches(11));

   			field = new CronHourField("*/3");
   			assertEquals("0-23/3", field.toString());
   			assertTrue("field does not match 0",field.matches(0));
   			assertFalse(field.matches(1));
   			assertFalse(field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 6",field.matches(6));
   			assertTrue("field does not match 9",field.matches(9));
   			assertTrue("field does not match 12",field.matches(12));
   			assertTrue("field does not match 15",field.matches(15));
   			assertTrue("field does not match 18",field.matches(18));
   			assertTrue("field does not match 21",field.matches(21));
   			assertFalse(field.matches(23));
   			assertFalse(field.matches(24));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}
}
