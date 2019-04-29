
import java.io.*;
import java.time.Clock;
import java.util.*;

/***
 * Read raw data from .csv file and store them in the ArrayList
 * 
 * @author Chan Woo Yang
 *
 */
public class FileHandler {

	// instance variable
	private String fileName;
	private HashMap<Integer, ParkingTickets> ParkingTicketsRaw;

	/**
	 * This is the constructor.
	 * 
	 * @param fileName
	 */
	public FileHandler(String fileName) {
		this.fileName = fileName.split("\\.")[0]; // remove file extension
		File parkingCitationFile = new File(fileName);
		ParkingTicketsRaw = new HashMap<Integer, ParkingTickets>();
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(parkingCitationFile);
			scanner.nextLine(); // skip the first row; column titles

			int count = 0;
			while (scanner.hasNextLine()) {
				count++;
				String ParkingTicketInfo = scanner.nextLine();
				String[] columnData = ParkingTicketInfo.split(",");

				ParkingTickets TicketRawData = new ParkingTickets(columnData);
				try {
					ParkingTicketsRaw.put(count, TicketRawData);
				} catch (OutOfMemoryError e) {
					System.out.println(count);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Please download \"parking-citations.csv\" raw data file first "
					+ "from the Kaggle website: https://www.kaggle.com/cityofLA/los-angeles-parking-citations");
//			e.printStackTrace();
		}
	}
	

	/**
	 * Take parking tickets data in the Hashmap and write them in the new .csv file
	 * 
	 * @param cleanedData - all parking tickets data in the HashMap format
	 */
	public void writeToCSVFile(HashMap<Integer, ParkingTickets> cleanedData) {
		try {
			FileWriter fw = new FileWriter(fileName + "_cleaned.csv", false);
			PrintWriter pw = new PrintWriter(fw);

			// convert ParkingTickets object to String Array
			Clock clock = Clock.systemUTC();
			System.out.println("Write Start: " + clock.instant());
			for (Integer currentData : cleanedData.keySet()) {
				ParkingTickets currentTicket = cleanedData.get(currentData);
				String[] currentDataArray = currentTicket.getParkingTicketsArray();

				for (int i = 0; i < currentDataArray.length; i++) {
					pw.print(currentDataArray[i]);
					if (i < currentDataArray.length - 1) {
						pw.print(",");
					}
				}
				pw.print("\n");
				pw.flush();
				pw.close(); // added to close pw
			}
			System.out.println("Write End: " + clock.instant());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer, ParkingTickets> getParkingTicketsRaw() {
		return ParkingTicketsRaw;
	}

	/**
	 * for debugging and testing purpose
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FileHandler fh = new FileHandler("parking-citations_small.csv");
		System.out.println(fh.getParkingTicketsRaw().get(0).getColor());

	}

}
