
import java.util.HashMap;

/***
 * Clean the raw data from the .csv file
 * 
 * @author Chan Woo Yang
 *
 */
public class DataCleaner {

	/**
	 * instance variable
	 */
	private HashMap<Integer, ParkingTickets> cleanedDataRaw = new HashMap<Integer, ParkingTickets>();

	/**
	 * constructor
	 * 
	 * @param rawDataFromCSV
	 */
	public DataCleaner(HashMap<Integer, ParkingTickets> rawDataFromCSV) {
		for (Integer ticketNumber : rawDataFromCSV.keySet()) {
			ParkingTickets currentTicket = rawDataFromCSV.get(ticketNumber);
			if (dataValidityChecker(currentTicket)) {
				cleanedDataRaw.put(ticketNumber, currentTicket);
			}
		}
	}

	/**
	 * Check the validity of input data as the ParkingTickets object. Check if core
	 * data information is neither 0 nor null.
	 * 
	 * @param ticketInfo
	 * @return Boolean of whether the input object has a valid data
	 */
	private boolean dataValidityChecker(ParkingTickets ticketInfo) {

		if (ticketInfo.getIssueDate() == null || ticketInfo.getIssueDate().isEmpty()) {
			return false;
		} else if (ticketInfo.getIssueTime() == 0) {
			return false;
		} else if (ticketInfo.getLatitude() == 0.0 || ticketInfo.getLongitude() == 0.0) {
			return false;
		} else {
			return true;
		}
	}

	public HashMap<Integer, ParkingTickets> getCleanedDataRaw() {
		return cleanedDataRaw;
	}

}
