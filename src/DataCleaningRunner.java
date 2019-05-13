
import java.util.*;
import java.time.*;

/***
 * The main file for running the data cleaning codes.
 * 
 * @author Chan Woo Yang
 *
 */
public class DataCleaningRunner {

	/**
	 * Takes the raw data csv file and runs through the data cleaning process. Then,
	 * the cleaned data sets are written in the new csv file.
	 */
	public void runDataCleaner() {
		Clock clock = Clock.systemUTC();
		System.out.println("Read Start: " + clock.instant());
		FileHandler fh = new FileHandler("parking-citations.csv");
		HashMap<Integer, ParkingTickets> rawDataHashMap = fh.getParkingTicketsRaw();
		System.out.println("Read End: " + clock.instant());

		DataCleaner dc = new DataCleaner(rawDataHashMap);
		System.out.println("Raw Data Size: " + rawDataHashMap.size());
		System.out.println("Cleaned Data Size: " + dc.getCleanedDataRaw().size());

		fh.writeToCSVFile(dc.getCleanedDataRaw());
	}
}