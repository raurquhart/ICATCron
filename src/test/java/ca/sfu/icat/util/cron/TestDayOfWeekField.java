package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:53 PM
 */

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestDayOfWeekField {

    @SuppressWarnings("UnusedDeclaration")
    @Test
   	public void testStarField() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField("*");
   		} catch (CronTimeFieldException e) {
   			fail("Couldn't create new CronDayOfWeekField with \"*\" param");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testNullOrEmpty() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			int result = field.validateValue(null);
   			fail("CronDayOfWeekField validated null param and returned: " + result);
   			result = field.validateValue(" ");
   			fail("CronDayOfWeekField validated \" \" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testInRange() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			for (int i=1; i<8; i++) {
   				assertEquals(i,field.validateValue(String.valueOf(i)));
   			}
   			assertEquals(1,field.validateValue("01"));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testOutOfRange() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			int result = field.validateValue("8");
   			fail("CronDayOfWeekField validated \"8\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			int result = field.validateValue("0");
   			fail("CronDayOfWeekField validated \"0\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			int result = field.validateValue("-1");
   			fail("CronDayOfWeekField validated \"-1\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@SuppressWarnings("UnusedDeclaration")
       @Test
   	public void testNonNumeric() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			int result = field.validateValue("10m");
   			fail("CronDayOfWeekField validated \"m10\" param and returned: " + result);
   			result = field.validateValue("all");
   			fail("CronDayOfWeekField validated \"all\" param and returned: " + result);
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
   	public void testValidNonNumeric() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField();
   			assertEquals(1,field.validateValue("Mon"));
   			assertEquals(1,field.validateValue("mon"));
   			assertEquals(2,field.validateValue("Tue"));
   			assertEquals(3,field.validateValue("Wed"));
   			assertEquals(4,field.validateValue("thu"));
   			assertEquals(5,field.validateValue("fri"));
   			assertEquals(6,field.validateValue("Sat"));
   			assertEquals(7,field.validateValue("Sun"));
   		} catch (CronTimeFieldException e) {
   			e.printStackTrace();
   			fail();
   		}
   	}

   	@Test
   	public void testConstructor() {
   		try {
   			CronDayOfWeekField field = new CronDayOfWeekField("01");
   			assertEquals("1", field.toString());
   			field = new CronDayOfWeekField("1,4,7");
   			assertEquals("1,4,7", field.toString());
   			assertTrue("field does not match 4",field.matches(4));
   			assertFalse("field matches 0",field.matches(0));
   			assertFalse("field matches 8",field.matches(8));

   			field = new CronDayOfWeekField("1-7");
   			assertEquals("1-7", field.toString());
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue(field.matches(1));
   			assertFalse(field.matches(24));

   			field = new CronDayOfWeekField("1-7/2");
   			assertEquals("1-7/2", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 5",field.matches(5));
   			assertTrue("field does not match 7",field.matches(7));
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(2));
   			assertFalse(field.matches(4));
   			assertFalse(field.matches(6));

   			field = new CronDayOfWeekField("2-4,6-7");
   			assertEquals("2-4,6-7", field.toString());
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertFalse(field.matches(5));
   			assertTrue("field does not match 6",field.matches(6));
   			assertTrue("field does not match 7",field.matches(7));
   			assertFalse(field.matches(8));

   			field = new CronDayOfWeekField("*/3");
   			assertEquals("1-7/3", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertFalse(field.matches(2));
   			assertFalse(field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 7",field.matches(7));

   			field = new CronDayOfWeekField("Mon-Fri");
   			assertEquals("1-5", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertFalse(field.matches(7));

   			field = new CronDayOfWeekField("Tue-Fri,Sun");
   			assertEquals("2-5,7", field.toString());
   			assertFalse(field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertTrue("field does not match 7",field.matches(7));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

}
