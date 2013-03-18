package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 6:58 PM
 */

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestCronMinuteField {

    @SuppressWarnings("UnusedDeclaration")
    @Test
   	public void testStarField() {
   		try {
   			CronMinuteField field = new CronMinuteField("*");
   		} catch (CronTimeFieldException e) {
   			fail("Couldn't create new CronMinuteField with \"*\" param");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testNullOrEmpty() {
   		try {
   			CronMinuteField field = new CronMinuteField();
   			int result = field.validateValue(null);
   			fail("CronMinuteField validated null param and returned: " + result);
   			result = field.validateValue(" ");
   			fail("CronMinuteField validated \" \" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testInRange() {
   		try {
   			CronMinuteField field = new CronMinuteField();
   			for (int i=0; i<60; i++) {
   				assertEquals(i,field.validateValue(String.valueOf(i)));
   			}
   			assertEquals(0,field.validateValue("00"));
   			assertEquals(59,field.validateValue("59"));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}

   	@Test
   	public void testOutOfRange() {
   		try {
   			CronMinuteField field = new CronMinuteField();
   			int result = field.validateValue("60");
   			fail("CronMinuteField validated \"60\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronMinuteField field = new CronMinuteField();
   			int result = field.validateValue("-1");
   			fail("CronMinuteField validated \"-1\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@SuppressWarnings("UnusedDeclaration")
    @Test
   	public void testNonNumeric() {
   		try {
   			CronMinuteField field = new CronMinuteField();
   			int result = field.validateValue("10m");
   			fail("CronMinuteField validated \"m10\" param and returned: " + result);
   			result = field.validateValue("all");
   			fail("CronMinuteField validated \"all\" param and returned: " + result);
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   		try {
   			CronMinuteField field = new CronMinuteField("all");
   			fail("Created CronMinuteField with \"all\" param");
   		} catch (CronTimeFieldException e) {
   			// ignore
   		}
   	}

   	@Test
   	public void testConstructor() {
   		try {
   			CronMinuteField field = new CronMinuteField("00");
   			assertEquals("0", field.toString());
   			field = new CronMinuteField("0,15,59");
   			assertEquals("0,15,59", field.toString());
   			assertTrue("field does not match 0",field.matches(0));
   			assertTrue("field does not match 15",field.matches(15));
   			assertTrue("field does not match 59",field.matches(59));
   			assertFalse("field matches 17",field.matches(17));
   			assertFalse("field matches 60",field.matches(60));

   			field = new CronMinuteField("0-23");
   			assertEquals("0-23", field.toString());
   			assertTrue("field does not match 15",field.matches(15));
   			assertTrue(field.matches(0));
   			assertFalse(field.matches(24));

   			field = new CronMinuteField("*");
   			assertTrue(field.isStar());
   			assertTrue("field does not match 15",field.matches(15));
   			assertTrue(field.matches(0));
   			assertFalse(field.matches(60));

   			field = new CronMinuteField("0-59/2");
   			assertEquals("0-59/2", field.toString());
   			assertTrue("field does not match 16",field.matches(16));
   			assertTrue(field.matches(0));
   			assertFalse(field.matches(3));
   			assertFalse(field.matches(33));

   			field = new CronMinuteField("2-5,7-10");
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

   			field = new CronMinuteField("*/10");
   			assertEquals("0-59/10", field.toString());
   			assertTrue("field does not match 0",field.matches(0));
   			assertFalse(field.matches(1));
   			assertFalse(field.matches(2));
   			assertTrue("field does not match 10",field.matches(10));
   			assertTrue("field does not match 20",field.matches(20));
   			assertTrue("field does not match 30",field.matches(30));
   			assertTrue("field does not match 40",field.matches(40));
   			assertTrue("field does not match 50",field.matches(50));
   			assertFalse(field.matches(59));
   			assertFalse(field.matches(60));
   		} catch (CronTimeFieldException e) {
   			fail("Exception thrown checking valid values");
   			e.printStackTrace();
   		}
   	}


}
