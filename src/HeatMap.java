import java.util.*;

public class HeatMap {

	private final int xMax = 6670000;
	private final int xMin = 6270000;
	private final int yMax = 2100000;
	private final int yMin = 1580000;
	private int increment = 10000;
	private int deltaX = (xMax-xMin)/increment;// Number of increments between xMax and xMin
	private int deltaY = (yMax-yMin)/increment;// Number of increments between yMax and yMin
	

	private ArrayList<Location[]> gridMap = new ArrayList<Location[]>();

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
	 * This method takes in parking ticket raw data then output HashMap of total
	 * tickets for each block in the city
	 * 
	 * @param data
	 * @return HashMap of block index as key and number of tickets in that block.
	 */
	public HashMap<Integer, Integer> HeatMapGenerator(HashMap<Integer, ParkingTickets> data) {

		HashMap<Integer, Integer> heatMap = new HashMap<Integer, Integer>();
		ArrayList<Location[]> Grid = GridGenerator();
// Initialize heatmap HashMap with index and zero value		
		for (int i = 0; i < Grid.size(); i++) {
			heatMap.put(i, 0);
		}
// For each ticket's location, check which block it belongs to and add to the HeatMap.
		for (Integer key : data.keySet()) {
			Location LC = new Location(data.get(key).getLatitude(), data.get(key).getLongitude());
			for (int i = 0; i < Grid.size(); i++) {
				if (inBlock(LC, Grid.get(i))) {
					Integer previousCount = heatMap.get(i);
					heatMap.put(i, previousCount + 1);
				}
			}

		}
		return heatMap;
	}

	public static void main(String[] args) {
		HeatMap theHeatMap = new HeatMap();
//		System.out.println(theHeatMap.GridGenerator().get(3000)[1].getLatitude());
//	System.out.println(theHeatMap.GridGenerator().size());
		FileHandler file = new FileHandler("parking-citations_cleaned.csv");
		HashMap<Integer, ParkingTickets> data = file.getParkingTicketsRaw();
		System.out.println("Raw Data Size is " + data.size());
		HashMap<Integer, Integer> heatMap = theHeatMap.HeatMapGenerator(data);
		System.out.println("HeatMap Blocks Count: " + heatMap.size());
		int totalTickets = 0;
		int zeroCounter = 0;
		for (int i = 0; i < heatMap.size(); i++) {
			// System.out.println(heatMap.get(i));
			totalTickets += heatMap.get(i);
			if (heatMap.get(i) == 0) {
				zeroCounter++;
			}
		}
		System.out.println("HeatMap Total Tickets:  " + totalTickets);
		System.out.println("HeatMap Empty Blocks Count: " + zeroCounter);
	}

}