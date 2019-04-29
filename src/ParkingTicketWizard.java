import java.io.*;

/***
 * 
 * The main class of the software that describes the workflow of the software
 * 
 * @author ChanWoo
 *
 */

public class ParkingTicketWizard {

	// instance variable
	private File rawDataFile = new File("parking-citations.csv");
	private File cleanedDataFile = new File("parking-citations_cleaned.csv");
	DataCleaningRunner dcr = new DataCleaningRunner();

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

		System.out.println("\nThe environment for the Parking Ticket Wizard is now ready");
	}

	/**
	 * 
	 */
	public void run() {
		User user = new User(); // introduce user

		System.out.println("\nWelcome to the Parking Ticket Wizard!\n");

		initializeSWEnv(); // initialize the environment (make csv files ready)

		boolean isRunning = true;
		while (isRunning) {
			System.out.println("\nPlease select one of the followings:");
			System.out.println("1. Overall LA Data Analysis Result");
			System.out.println("2. Data Analysis Result Around My Area");
			System.out.println("3. Quit The Program");

			int userOption = user.chooseOption();

			switch (userOption) {
			case 1:
				System.out.println("\nOverall LA Data Analysis Result");
			case 2:
				System.out.println("\nData Analysis Result Around My Area");
			case 3:
				System.out.println("\nClosing the program...");
				System.exit(0);
			}

		}

		System.out.println("printed");
	}

	public static void main(String[] args) {
		ParkingTicketWizard ptw = new ParkingTicketWizard();
		ptw.run();
	}
}
