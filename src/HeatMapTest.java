import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HeatMapTest {

	@Test
	void testGridGenerator() {
		HeatMap theHeatMap = new HeatMap();
		Location[] block= new Location[4];
		Location[] block2= new Location[4];
		block[0]=new Location(6660000,2090000);//LB
		block[1]=new Location(6670000,2090000);//RB
		block[2]=new Location(6660000,2100000);//LT
		block[3]=new Location(6670000,2100000);//RT
	//	assertEquals(theHeatMap.GridGenerator().get(199167).equals(obj),true);
	//	block2=theHeatMap.GridGenerator().get(199167);
		block2=theHeatMap.GridGenerator().get(2079);
		for(int i=0;i<4;i++) {
			System.out.println("X- "+block2[i].getLatitude()+" Y- "+block2[i].getLongitude());
			assertEquals(block2[i].getLatitude()==block[i].getLatitude(),true);
			assertEquals(block2[i].getLongitude()==block[i].getLongitude(),true);
		}
		//fail("Not yet implemented");
	}

	@Test
	void testInBlock() {
		HeatMap theHeatMap = new HeatMap();
		Location[] block= new Location[4];
		block[0]=new Location(6660000,2090000);//LB
		block[1]=new Location(6670000,2090000);//RB
		block[2]=new Location(6660000,2100000);//LT
		block[3]=new Location(6680000,2100000);//RT
		Location a = new Location(6660010,2090010);
		Location b= new Location(6660000,2090000);
		Location c= new Location(6270000,1590000);
		System.out.println(a.calculateDistance(a, block[0]));
		System.out.println(a.calculateDistance(a, block[1]));
		System.out.println(a.calculateDistance(a, block[2]));
		System.out.println(a.calculateDistance(a, block[3]));
		System.out.println("Diagnal Distance "+a.calculateDistance(block[0], block[3]));
		assertEquals(theHeatMap.inBlock(a, block),true);
		assertEquals(theHeatMap.inBlock(b, block),true);
		assertEquals(theHeatMap.inBlock(c, block),false);
		//fail("Not yet implemented");
	}

	@Test
	void testHeatMapGenerator() {
	//	fail("Not yet implemented");
	}

}
