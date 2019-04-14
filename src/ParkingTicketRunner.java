import java.util.*;
import java.time.*;

public class ParkingTicketRunner {
	public static void main(String[] args) {
		FileHandler fh = new FileHandler("parking-citations_small.csv");
		
		Clock clock = Clock.systemUTC();
		System.out.println("Start: " + clock.instant());
		HashMapCreator hmc = new HashMapCreator("parking-citations_small.csv");
		HashMap<Integer, ParkingTickets> hashMapIssueDate = hmc.getParkingTicketRaw();
		System.out.println("End: " + clock.instant());
		
		fh.writeToCSVFile(hashMapIssueDate);
		
		
	}
}
