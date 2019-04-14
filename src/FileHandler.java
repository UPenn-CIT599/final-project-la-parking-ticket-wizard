
/***
 * Read raw data from .csv file and store them in the ArrayList
 * 
 * @author ChanWoo
 *
 */

import java.io.*;
import java.util.*;

public class FileHandler {

	// instance variable
	private String fileName;
	private HashMap<Integer, ParkingTickets> ParkingTicketsRaw;

	// constructor
	public FileHandler(String fileName) {
		this.fileName = fileName;
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
//				System.out.println(flightRowInfo); // for debugging purpose

				ParkingTickets TicketRawData = new ParkingTickets(columnData);
				try {
					ParkingTicketsRaw.put(count, TicketRawData);
				} catch (OutOfMemoryError e) {
					System.out.println(count);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeToCSVFile(HashMap<Integer, ParkingTickets> cleanedData) {
		try {
			FileWriter fw = new FileWriter(fileName + "_cleaned.csv", false);
			PrintWriter pw = new PrintWriter(fw);

			// convert ParkingTickets object to String Array
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
			}

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
