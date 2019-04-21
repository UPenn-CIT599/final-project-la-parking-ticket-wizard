import java.util.*;

/**
 * This class defines location object based on Latitude and Longitude data from
 * the dataset. Note that latitude / longitude (XY) are represented in US Feet
 * coordinates according to the NAD_1983_StatePlane_California_V_FIPS_0405_Feet
 * projection. This class contains methods that can perform calculations on
 * location data.
 * 
 * @author Weiwenz33
 *
 */
public class Location {
	private double Latitude, Longitude;

	public Location(double X, double Y) {
		this.Latitude = X;
		this.Longitude = Y;
	}

	public double getLatitude() {
		return Latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	/**
	 * This method takes in two location object and returns distance between them in
	 * U.S. feet.
	 * 
	 * @param A First Location
	 * @param B Second Location
	 * @return Distance between A and B
	 */

	public double calculateDistance(Location A, Location B) {
		double distance;
		distance = Math.sqrt(
				Math.pow((A.getLatitude() - B.getLatitude()), 2) + Math.pow(A.getLongitude() - B.getLongitude(), 2));

		return distance;

	}

	/**
	 * This method checks if the distance between two locations is within a defined
	 * radius
	 * 
	 * @param A
	 * @param B
	 * @param radius
	 * @return boolean
	 */
	public boolean zoneCheck(Location A, Location B, double radius) {
		if (calculateDistance(A, B) <= radius) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method takes in cleaned parking tickets hashmap data, then evaluate each
	 * ticket to check if the location of the ticket is within a defined radius of
	 * the position of interest. Then the mothod will return a filtered hashmap
	 * containing tickets that are within the radius
	 * 
	 * @param CleanedData
	 * @param Radius
	 * @param currentLocation
	 * @return
	 */
	public HashMap<Integer, ParkingTickets> locationFilter(HashMap<Integer, ParkingTickets> CleanedData, double Radius,
			Location currentLocation) {
		HashMap<Integer, ParkingTickets> locationParkingTickets = new HashMap<Integer, ParkingTickets>();

//		int nullLocationCounter = 0;

		for (Integer key : CleanedData.keySet()) {
			try {
				Location targetLocation = new Location(CleanedData.get(key).getLatitude(),
						CleanedData.get(key).getLongitude());
				if (calculateDistance(currentLocation, targetLocation) <= Radius) {
					locationParkingTickets.put(key, CleanedData.get(key));
				}

			} catch (NullPointerException e) {
				// nullLocationCounter++;
			}
		}
		// System.out.println("Null Locations " + nullLocationCounter);

		return locationParkingTickets;

	}

// This main method is for testing purpose only
//	public static void main(String[] args) {
//		Location a = new Location(6439997.9, 1802686.4);
//		 Location b = new Location(6415432.1, 1898663.3);
//		 System.out.println(a.calculateDistance(a, b));
//		 System.out.println(b.calculateDistance(a, b));
//		FileHandler file = new FileHandler("parking-citations.csv");
//		HashMap<Integer, ParkingTickets> data= file.getParkingTicketsRaw();
//		int nullCounter=0;
//		for(Integer key: data.keySet()) {
//			if(data.get(key).getLatitude()==0||data.get(key).getLongitude()==0) {
//				nullCounter++;
//			}
//		}
//		System.out.println(nullCounter);
	// System.out.println(data.get(10).getLongitude());

	// for (int i=0; i<15; i++) {
//			System.out.println(file.getParkingTicketsRaw().get(i).getLatitude()+"  "+file.getParkingTicketsRaw().get(i).getLatitude());
	// }

//	System.out.println(a.locationFilter(file.getParkingTicketsRaw(), 100.0, a).size());
// System.out.println(file.getParkingTicketsRaw().size());

//	}

}
