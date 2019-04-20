
/***
 * The main file for running the data cleaning codes.
 * 
 * @author Chan Woo Yang
 *
 */

import java.util.*;
import java.time.*;

public class DataCleaningRunner {
	
	public static void main(String[] args) {	
		Clock clock = Clock.systemUTC();
		System.out.println("Read Start: " + clock.instant());
		FileHandler fh = new FileHandler("parking-citations.csv");
		HashMap<Integer, ParkingTickets> rawDataHashMap = fh.getParkingTicketsRaw();
		System.out.println("Read End: " + clock.instant());
		
		DataCleaner dc = new DataCleaner(rawDataHashMap);
		System.out.println("Raw Data Size: " + rawDataHashMap.size());
		System.out.println("Cleaned Data Size" + dc.getCleanedDataRaw().size());
		
		fh.writeToCSVFile(dc.getCleanedDataRaw());
		
		
	}
}