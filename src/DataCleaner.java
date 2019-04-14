import java.util.HashMap;

public class DataCleaner {
	
	// instance variable
	private HashMap<Integer, ParkingTickets> cleanedDataRaw = new HashMap<Integer, ParkingTickets>();
	
	// constructor
	public DataCleaner(HashMap<Integer, ParkingTickets> rawDataFromCSV) {
		for (Integer ticketNumber : rawDataFromCSV.keySet()) {
			ParkingTickets currentTicket = rawDataFromCSV.get(ticketNumber);
			if (dataValidityChecker(currentTicket)) {
				cleanedDataRaw.put(ticketNumber, currentTicket);
			}
			
		}
	}
	
	private boolean dataValidityChecker(ParkingTickets ticketInfo) {
		
		if (ticketInfo.getIssueDate() == null || ticketInfo.getIssueDate().isEmpty()) {
			return false;
		} else if (ticketInfo.getIssueTime() == 0) {
			return false;
		} else if (ticketInfo.getLatitude() == 0.0 || ticketInfo.getLongitude() == 0.0){
			return false;
		} else {
			return true;
		}
	}

	public HashMap<Integer, ParkingTickets> getCleanedDataRaw() {
		return cleanedDataRaw;
	}
	
}
