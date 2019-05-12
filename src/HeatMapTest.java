import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class HeatMapTest {

	@Test
	void testGridGenerator() {
		HeatMap theHeatMap = new HeatMap();
		Location[] block= new Location[4];
		Location[] block2= new Location[4];
		block[0]=new Location(6270000,1580000);//LB
		block[1]=new Location(6290000,1580000);//RB
		block[2]=new Location(6270000,1600000);//LT
		block[3]=new Location(6290000,1600000);//RT
		block2=theHeatMap.GridGenerator().get(0);
		for(int i=0;i<4;i++) {
			System.out.println("X- "+block2[i].getLatitude()+" Y- "+block2[i].getLongitude());
			assertEquals(block2[i].getLatitude()==block[i].getLatitude(),true);
			assertEquals(block2[i].getLongitude()==block[i].getLongitude(),true);
		}

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
		assertEquals(theHeatMap.inBlock(a, block),true);
		assertEquals(theHeatMap.inBlock(b, block),true);
		assertEquals(theHeatMap.inBlock(c, block),false);
		
	}

	
	@Test
	void testBlockMatcher() {
		HeatMap theHeatMap = new HeatMap();
		ArrayList<Location[]> gridMap=theHeatMap.GridGenerator();
		Location a= new Location(6270001,1580001);
		Location b= new Location(0,0);
		assertEquals(theHeatMap.blockMatcher(a),0);
		assertEquals(theHeatMap.blockMatcher(b),-1);
		
	}
	@Test
	void testHeatMapGenerator() {
		HeatMap theHeatMap = new HeatMap();

		theHeatMap.HeatMapGenerator();
		// Checks if HeatMap instance variable has been populated after calling the method
		assertEquals(theHeatMap.getHeatMap().equals(null),false);
	}

}
