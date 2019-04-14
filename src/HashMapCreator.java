import java.util.*;

public class HashMapCreator {

	// instance variable
	HashMap<Integer, ParkingTickets> parkingTicketRaw;
	HashMap<String, ArrayList<ParkingTickets>> groupByIssueDate;

	// constructor
	public HashMapCreator(String filename) {
		FileHandler fh = new FileHandler(filename);
		parkingTicketRaw = fh.getParkingTicketsRaw();
//		createHashMapByIssueDate();
	}

	private void createHashMapByIssueDate() {
		groupByIssueDate = new HashMap<String, ArrayList<ParkingTickets>>();
		for (ParkingTickets parkingTicket : parkingTicketRaw) {
			String currentIssueDate = parkingTicket.getIssueDate();
			if (currentIssueDate != null && !currentIssueDate.isEmpty()) {
				if (!groupByIssueDate.containsKey(currentIssueDate)) {
					ArrayList<ParkingTickets> parkingTicketData = new ArrayList<ParkingTickets>();
					parkingTicketData.add(parkingTicket);
					groupByIssueDate.put(currentIssueDate, parkingTicketData);
				} else {
					ArrayList<ParkingTickets> parkingTicketData = groupByIssueDate.get(currentIssueDate);
					parkingTicketData.add(parkingTicket);
					groupByIssueDate.put(currentIssueDate, parkingTicketData);
				}
			}
		}
	}

	public HashMap<Integer, ParkingTickets> cleanRawData(HashMap<Integer, ParkingTickets> rawDataFromCSV) {
		DataCleaner dc = new DataCleaner(rawDataFromCSV);
		HashMap<Integer, ParkingTickets> cleanedData = dc.getCleanedDataRaw();
		
		return cleanedData;
	}

	public HashMap<Integer, ParkingTickets> getParkingTicketRaw() {
		return parkingTicketRaw;
	}

	public HashMap<String, ArrayList<ParkingTickets>> getGroupByIssueDate() {
		return groupByIssueDate;
	}

}
