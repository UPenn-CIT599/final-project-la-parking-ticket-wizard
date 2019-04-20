
import java.util.*;

/***
 * Using the cleanedData, creates different types of HashMap for Data Analysis
 * 
 * @author Chan Woo Yang
 *
 */
public class HashMapCreator {

	// instance variable
	private HashMap<Integer, ParkingTickets> parkingTicketData;

	// constructor
	public HashMapCreator(HashMap<Integer, ParkingTickets> cleanedData) {
		parkingTicketData = cleanedData;
	}

	/***
	 * 
	 * @param rawDataFromCSV
	 * @return
	 */
	public HashMap<Integer, ParkingTickets> cleanRawData(HashMap<Integer, ParkingTickets> rawDataFromCSV) {
		DataCleaner dc = new DataCleaner(rawDataFromCSV);
		HashMap<Integer, ParkingTickets> cleanedData = dc.getCleanedDataRaw();

		return cleanedData;
	}

	public HashMap<Integer, ParkingTickets> getParkingTicketRaw() {
		return parkingTicketData;
	}

}
