/**
 * This class can generate a HeatMap for ticket counts in each city blocks. 
 */
import java.util.*;
import java.io.*;

public class HeatMap {
    //Extreme coordinates values in the dataset
	private final int xMax = 6670000;
	private final int xMin = 6270000;
	private final int yMax = 2100000;
	private final int yMin = 1580000;
	private int increment = 20000;
	private int deltaX = (xMax - xMin) / increment;// Number of increments between xMax and xMin
	private int deltaY = (yMax - yMin) / increment;// Number of increments between yMax and yMin
	// Stores location array of each block
	private ArrayList<Location[]> gridMap = new ArrayList<Location[]>();
	// The key also reference index in gridMap for block location retrieval
	private HashMap<Integer, Integer> heatMap = new HashMap<Integer, Integer>();

	public ArrayList<Location[]> getGridMap() {
		return gridMap;
	}

	public HashMap<Integer, Integer> getHeatMap() {
		return heatMap;
	}

	/**
	 * This method a grid based on max and min values of X and Y returns arraylist
	 * of location array containing four corner coordinates of each grid.
	 * 
	 * @return
	 */
	public ArrayList<Location[]> GridGenerator() {
//Generates grid from bottom left to bottom right then move up		
		for (int j = 0; j < deltaY; j++) {
			for (int i = 0; i < deltaX; i++) {
				Location[] block = new Location[4];
				block[0] = new Location(xMin + i * increment, yMin + j * increment); // LeftBotton
				block[1] = new Location(xMin + (i + 1) * increment, yMin + j * increment); // RightBotton
				block[2] = new Location(xMin + i * increment, yMin + (j + 1) * increment);// LeftTop
				block[3] = new Location(xMin + (i + 1) * increment, yMin + (j + 1) * increment);// RightTop
				gridMap.add(block);
			}

		}
		return gridMap;

	}

	/**
	 * This method checks if a location is within a defined block by checking if the
	 * location's coordinates value is between the maximum and minimum coordinates
	 * value of the four corners of a block.
	 * 
	 * @param A     The Location of interest
	 * @param block a Location Array containing 4 coordinates of four corners of a
	 *              block
	 * @return
	 */
	public boolean inBlock(Location A, Location[] block) {

		if (A.getLatitude() >= block[0].getLatitude() && A.getLatitude() <= block[3].getLatitude()
				&& A.getLongitude() >= block[0].getLongitude() && A.getLongitude() <= block[3].getLongitude()) {
			return true;
		} else
			return false;
	}

	/**
	 * This method takes in a location object and evaluate which block the location
	 * belongs to This method can only be called after GridGenerator is called so
	 * that gridMap is populated.
	 * 
	 * @param A Location
	 * @return Index number of the grid in the instance variable ArrayList GridMap.
	 */
	public int blockMatcher(Location A) {
		int blockIndex = -1;// default value, if a location is not in any of the blocks.
		for (int i = 0; i < gridMap.size(); i++) {

			if (inBlock(A, gridMap.get(i))) {
				blockIndex = i;
			}
		}
		return blockIndex;
	}

	/**
	 * This method takes in parking ticket raw data then populate HashMap of total
	 * tickets for each block in the city. The method also writes the generated HeatMap
	 * into HeatMap.csv for HeatMapReader method to quickly retrieve data
	 *  HashMap of block index as key and number of tickets in that block.
	 */
	public void HeatMapGenerator() {

		ArrayList<Location[]> Grid = GridGenerator();
		FileHandler file = new FileHandler("parking-citations_cleaned.csv");
		HashMap<Integer, ParkingTickets> data = file.getParkingTicketsRaw();
// Initialize heatmap HashMap with index and zero value		
		for (int i = 0; i < Grid.size(); i++) {
			heatMap.put(i, 0);
		}
// For each ticket's location, check which block it belongs to and add to the HeatMap.
		int totalTickets = 0;
		for (Integer key : data.keySet()) {
			Location LC = new Location(data.get(key).getLatitude(), data.get(key).getLongitude());
			for (int i = 0; i < Grid.size(); i++) {
				if (inBlock(LC, Grid.get(i))) {
					Integer previousCount = heatMap.get(i);
					heatMap.put(i, previousCount + 1);
					totalTickets++;
				}
			}

		}

        ArrayList<Integer> emptyBlocks= new ArrayList<Integer>();
		try {
			FileWriter writer = new FileWriter("HeatMap.csv", false);
			PrintWriter printer = new PrintWriter(writer);
			for (Integer key : heatMap.keySet()) {
				int blockTickets = heatMap.get(key);
				double prob = blockTickets*1.0 / totalTickets;					
				if (blockTickets != 0) {
					printer.print(key);
					printer.print(",");
					printer.print(blockTickets);
//					printer.print(",");
//					printer.print(prob);
					printer.print("\n");
					printer.flush();
		
				} else {
					emptyBlocks.add(key);//mark empty blocks for removal
				}

			}
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Integer key: emptyBlocks) {
			heatMap.remove(key);
		}		
	}

	/**
	 * This method reads pre-computed heatMap CSV file and populate HeatMap instance variable in this class
	 * to shorten computation time for better user experience. 
	 */
	public void readHeatMap(){
		File f = new File ("HeatMap.csv");
		Scanner reader;
		try {
			reader = new Scanner(f);
			while(reader.hasNextLine()) {				
					String[] rowData=reader.nextLine().split(",");				
					heatMap.put(Integer.valueOf(rowData[0]),Integer.valueOf(rowData[1]));
				}
				reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		HeatMap theHeatMap = new HeatMap();
//		System.out.println(theHeatMap.GridGenerator().get(3000)[1].getLatitude());
//	System.out.println(theHeatMap.GridGenerator().size());
//		FileHandler file = new FileHandler("parking-citations_cleaned.csv");
//		HashMap<Integer, ParkingTickets> data = file.getParkingTicketsRaw();
//		System.out.println("Raw Data Size is " + data.size());
		theHeatMap.readHeatMap();
		HashMap<Integer, Integer> heatMap = theHeatMap.getHeatMap();
		System.out.println("HeatMap Blocks Count: " + theHeatMap.heatMap.size());
//		int totalTickets = 0;
//		int zeroCounter = 0;
//		for (int i = 0; i < heatMap.size(); i++) {
//			// System.out.println(heatMap.get(i));
//			totalTickets += heatMap.get(i);
//			if (heatMap.get(i) == 0) {
//				zeroCounter++;
//			}
//		}
//		System.out.println("HeatMap Total Tickets:  " + totalTickets);
//		System.out.println("HeatMap Empty Blocks Count: " + zeroCounter);
}

}