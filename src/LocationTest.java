import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LocationTest {

	@Test
	void testCalculateDistance() {
		Location A=new Location(100.0,100.0);
		Location B=new Location (103.0, 104.0);
		Location C= new Location (100.0, 120.0);
		
		assertEquals(A.calculateDistance(A, B),5.0);
		assertEquals(A.calculateDistance(A, C),20.0);
		
	}
	
	@Test
	void testEquals() {
		Location A=new Location(100.0,100.0);
		Location B=new Location (103.0, 104.0);
		Location C= new Location (100.0, 100.0);
		assertEquals(A.equals(B), false);
		assertEquals(A.equals(C), true);

	}
	
	@Test
	void testToString() {
		Location A=new Location(1.0,1.0);
		String a="X:1.0Y:1.0";
		assertTrue(A.toString().equals(a));
	}
	
	
	@Test
	void testZoneCheck() {
		Location a=new Location(1.0,1.0);
		Location b=new Location(1.0,2.0);
		Location c=new Location(1.0,3.0);
		assertTrue(a.zoneCheck(a, b, 2));
		assertTrue(a.zoneCheck(a, c, 3));
		assertEquals(a.zoneCheck(a, b, 0.1),false);
	}
	
	
}
