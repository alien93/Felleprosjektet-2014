package modelTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import models.AvtaleBokModel;

import org.junit.BeforeClass;
import org.junit.Test;

public class AvtaleBokModelTest {
	
	private static AvtaleBokModel myBok;
	private static GregorianCalendar gc;
	
	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	
	@BeforeClass
	public static void init() {
		myBok = new AvtaleBokModel();
		gc = new GregorianCalendar();
	}
	
	@Test
	public void getSetWeek() {
		myBok.setWeek(-100);
		assertEquals(52, myBok.getWeek());
		myBok.setWeek(55);
		assertEquals(1, myBok.getWeek());
		myBok.setWeek(45);
		assertEquals(45, myBok.getWeek());
	}
	
	public void getMonth() {
		assertEquals(gc.get(GregorianCalendar.MONTH), myBok.getMonth());
	}
	
	public void getYear() {
		assertEquals(gc.get(GregorianCalendar.YEAR), myBok.getYear());
	}
}
