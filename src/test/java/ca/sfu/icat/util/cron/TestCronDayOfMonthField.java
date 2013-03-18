package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 11:23 AM
 */

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestCronDayOfMonthField {


    @SuppressWarnings("UnusedDeclaration")
    @Test
   	public void testStarField() {
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField("*");
   		} catch (CronTimeFieldException e) {
   			fail("Couldn't create new CronDayOfMonthField with \"*\" param");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testNullOrEmpty() {
   		try {
   			CronMonthField field = new CronMonthField();
   			int result = field.validateValue(null);
   			fail("CronMonthField validated null param and returned: " + result);
   			result = field.validateValue(" ");
   			fail("CronMonthField validated \" \" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testInRange() {
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField();
   			for (int i=1; i<32; i++) {
   				assertEquals(i,field.validateValue(String.valueOf(i)));
   			}
   			assertEquals(1,field.validateValue("01"));
   			assertEquals(31,field.validateValue("31"));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testOutOfRange() {
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField();
   			int result = field.validateValue("32");
   			fail("CronDayOfMonthField validated \"32\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField();
   			int result = field.validateValue("-1");
   			fail("CronDayOfMonthField validated \"-1\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@SuppressWarnings("UnusedDeclaration")
    @Test
   	public void testNonNumeric() {
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField();
   			int result = field.validateValue("10m");
   			fail("CronDayOfMonthField validated \"m10\" param and returned: " + result);
   			result = field.validateValue("all");
   			fail("CronDayOfMonthField validated \"all\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField("all");
   			fail("Created CronDayOfMonthField with \"all\" param");
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testConstructor() {
   		try {
   			CronDayOfMonthField field = new CronDayOfMonthField("01");
   			assertEquals("1", field.toString());
   			field = new CronDayOfMonthField("1,15,31");
   			assertEquals("1,15,31", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertFalse("field does not match 32",field.matches(32));

   			field = new CronDayOfMonthField("1-31");
   			assertEquals("1-31", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(32));

   			field = new CronDayOfMonthField("1-31/2");
   			assertEquals("1-31/2", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(2));
   			assertFalse(field.matches(32));

   			field = new CronDayOfMonthField("2-5,7-10");
   			assertEquals("2-5,7-10", field.toString());
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertFalse(field.matches(11));

   			field = new CronDayOfMonthField("*/3");
   			assertEquals("1-31/3", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 7",field.matches(7));
   			assertTrue("field does not match 10",field.matches(10));
   			assertTrue("field does not match 13",field.matches(13));
   			assertTrue("field does not match 16",field.matches(16));
   			assertTrue("field does not match 19",field.matches(19));
   			assertTrue("field does not match 22",field.matches(22));
   			assertTrue("field does not match 25",field.matches(25));
   			assertTrue("field does not match 28",field.matches(28));
   			assertTrue("field does not match 31",field.matches(31));
   			assertFalse(field.matches(32));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

}
