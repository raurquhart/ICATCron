package ca.sfu.icat.util.cron;

import org.junit.Test;
import static junit.framework.TestCase.*;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:35 PM
 */

public class TestCronMonthField {

    @Test
   	public void testStarField() {
   		try {
   			CronMonthField field = new CronMonthField("*");
   		} catch (CronTimeFieldException e) {
   			fail("Couldn't create new CronMonthField with \"*\" param");
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
   			CronMonthField field = new CronMonthField();
   			for (int i=1; i<13; i++) {
   				assertEquals(i,field.validateValue(String.valueOf(i)));
   			}
   			assertEquals(1,field.validateValue("01"));
   			assertEquals(12,field.validateValue("12"));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testOutOfRange() {
   		try {
   			CronMonthField field = new CronMonthField();
   			int result = field.validateValue("60");
   			fail("CronMonthField validated \"60\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronMonthField field = new CronMonthField();
   			int result = field.validateValue("-1");
   			fail("CronMonthField validated \"-1\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testNonNumeric() {
   		try {
   			CronMonthField field = new CronMonthField();
   			int result = field.validateValue("10m");
   			fail("CronMonthField validated \"m10\" param and returned: " + result);
   			result = field.validateValue("all");
   			fail("CronMonthField validated \"all\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronHourField field = new CronHourField("all");
   			fail("Created ERXCronHourField with \"all\" param");
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testValidNonNumeric() {
   		try {
   			CronMonthField field = new CronMonthField();
   			assertEquals(1,field.validateValue("Jan"));
   			assertEquals(1,field.validateValue("jan"));
   			assertEquals(2,field.validateValue("Feb"));
   			assertEquals(3,field.validateValue("Mar"));
   			assertEquals(4,field.validateValue("Apr"));
   			assertEquals(5,field.validateValue("May"));
   			assertEquals(6,field.validateValue("Jun"));
   			assertEquals(7,field.validateValue("Jul"));
   			assertEquals(8,field.validateValue("Aug"));
   			assertEquals(9,field.validateValue("Sep"));
   			assertEquals(10,field.validateValue("Oct"));
   			assertEquals(11,field.validateValue("Nov"));
   			assertEquals(12,field.validateValue("Dec"));
   		} catch (CronTimeFieldException e) {
   			e.printStackTrace();
   			fail();
   		}
   	}

   	@Test
   	public void testConstructor() {
   		try {
   			CronMonthField field = new CronMonthField("01");
   			assertEquals("1", field.toString());
   			field = new CronMonthField("1,6,12");
   			assertEquals("1,6,12", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 6",field.matches(6));
   			assertTrue("field does not match 12",field.matches(12));
   			assertFalse("field matches 0",field.matches(0));
   			assertFalse("field matches 8",field.matches(8));

   			field = new CronMonthField("1-12");
   			assertEquals("1-12", field.toString());
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue(field.matches(1));
   			assertFalse(field.matches(24));

   			field = new CronMonthField("1-12/2");
   			assertEquals("1-12/2", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 5",field.matches(5));
   			assertTrue("field does not match 7",field.matches(7));
   			assertTrue("field does not match 9",field.matches(9));
   			assertTrue("field does not match 11",field.matches(11));
   			assertFalse(field.matches(0));
   			assertFalse(field.matches(2));
   			assertFalse(field.matches(4));
   			assertFalse(field.matches(6));
   			assertFalse(field.matches(12));

   			field = new CronMonthField("2-4,6-7");
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

   			field = new CronMonthField("*/3");
   			assertEquals("1-12/3", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertFalse(field.matches(2));
   			assertFalse(field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 7",field.matches(7));
   			assertTrue("field does not match 10",field.matches(10));

   			field = new CronMonthField("Jan-May");
   			assertEquals("1-5", field.toString());
   			assertTrue("field does not match 1",field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertFalse(field.matches(7));

   			field = new CronMonthField("Feb-May,Jul,Nov");
   			assertEquals("2-5,7,11", field.toString());
   			assertFalse(field.matches(1));
   			assertTrue("field does not match 2",field.matches(2));
   			assertTrue("field does not match 3",field.matches(3));
   			assertTrue("field does not match 4",field.matches(4));
   			assertTrue("field does not match 5",field.matches(5));
   			assertFalse(field.matches(6));
   			assertTrue("field does not match 7",field.matches(7));
   			assertTrue("field does not match 11",field.matches(11));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

}
