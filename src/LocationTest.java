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

}
