import java.io.*;

/***
 * 
 * The main class of the program that runs overall back-end part (data cleaning,
 * data analysis, data visualization)
 * 
 * @author Chan Woo Yang
 *
 */

public class ParkingTicketWizard {

	// instance variable
	private final static File rawDataFile = new File("parking-citations.csv");
	private final static File cleanedDataFile = new File("parking-citations_cleaned.csv");
	DataCleaningRunner dcr = new DataCleaningRunner();
	ParkingTicketRunner ptr = new ParkingTicketRunner();

	/**
	 * Check if raw csv file and cleaned csv file are ready in the path. If not,
	 * prompt an user to download the raw csv file, or run the data cleaning method.
	 */
	private void initializeSWEnv() {
		if (!rawDataFile.exists()) {
			System.out.println("Please download \"parking-citations.csv\" raw data file first "
					+ "from the Kaggle website: https://www.kaggle.com/cityofLA/los-angeles-parking-citations");
			System.exit(0);
		} else {
			System.out.println("Raw data file exists.");
		}
		if (!cleanedDataFile.exists()) {
			System.out.println("Cleaned data file does not exist. Data Cleaner is running...");
			dcr.runDataCleaner();
		} else {
			System.out.println("Cleaned data file exists.");
		}

	}

	/**
	 * The Main method running the data cleaner and data analysis/visualization.
	 */
	public void run() {

		System.out.println("\nPreparing files for the Parking Ticket Wizard...\n");

		initializeSWEnv(); // initialize the environment (make csv files ready)

		ptr.run(); // run parking ticket runner; run data analysis and generate charts and graphs

		System.out.println("\nThe environment for the Parking Ticket Wizard is now ready");
	}

	/**
	 * main class running the Parking Ticket Wizard
	 * @param args
	 */
	public static void main(String[] args) {
		ParkingTicketWizard ptw = new ParkingTicketWizard();
		ptw.run();
	}
}
