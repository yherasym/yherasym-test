package test.softassertions;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class testing_softassertion {

	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();
	

@Test
public void testHardAndSoftAssert() {
	softAssert.assertEquals(1, 1, "integer incorrect asserssion");
	softAssert. assertTrue(Boolean.TRUE, "boolean1 incorrect asserssion");
	softAssert.assertTrue(Boolean.FALSE, "boolean2 incorrect asserssion");
	softAssert.assertTrue(Boolean.TRUE,  "boolean3 incorrect asserssion");
	softAssert.assertTrue(Boolean.FALSE, "boolean4 incorrect asserssion");
	softAssert.assertAll();
}

}