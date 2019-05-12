import java.util.*;
import java.util.concurrent.TimeUnit;

//import org.jfree.ui.RefineryUtilities;

/**
 * Parking Ticket Data Analysis Runner Class. Runs various data analysis and
 * calls for JFree Chart Pie and Bar Chart creation classes for each analysis.
 * 
 * @author lukeshin, Chan Woo Yang
 *
 */
public class ParkingTicketRunner {

	public void run() {

		FileHandler fh = new FileHandler("parking-citations_cleaned.csv");
		HashMap<Integer, ParkingTickets> hashMapPrakingTicketsRaw = fh.getParkingTicketsRaw();
		ParkingTicketDataProcessor ptdp = new ParkingTicketDataProcessor(hashMapPrakingTicketsRaw);
		
		System.out.println("***************************************************");
		System.out.println("Total ticket counts processed for Big-Data Analysis");
		System.out.println(hashMapPrakingTicketsRaw.size());
		System.out.println("***************************************************");
		System.out.println("");
		System.out.println("************************************");
		System.out.println("Parking Tickect Distribution by Hour");
		ptdp.ticketCountsByHour();
		System.out.println("************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("Parking Tickect Distribution by Day");
		HashMap<String, Integer> ticketsByDay = ptdp.ticketsByDay();
		System.out.println("***********************************");
		System.out.println("");
		System.out.println("");
		System.out.println("*************************************");
		System.out.println("Top 10 Most Issued Ticket Description");
		HashMap<String, Integer> ticketByVioDes = ptdp.ticketCountsByViolation();
		System.out.println("*************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("******************************************");
		System.out.println("Top 10 Most Issued Ticket Fine Information");
		HashMap<String, Integer> ticketByFine = ptdp.ticketsByFine();
		ArrayList<String> sortedKeys = ptdp.getSortedKeysByVioDesc();
		System.out.println("******************************************");
		System.out.println("");
		System.out.println("************************************************************************************************************");
		System.out.println("Printing Charts for Ticket Information, this will take ~15mins to avoid JFreeChart's random throws exception.");
		System.out.println("************************************************************************************************************");
		// BarChart Creation Steps
		// Add delay to avoid any ConcurrentModificationException
		BarChartForTicketsByTop10Violations bcftvio = new BarChartForTicketsByTop10Violations(ticketByVioDes);
		try {
			TimeUnit.MINUTES.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// BarChart for Top 10 Violation Ticket Type Descriptions
		bcftvio.BarChartForViolationDescription();
		// Add delay to avoid any ConcurrentModificationException
		try {
			TimeUnit.MINUTES.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// BarChart for Top 10 Violation Ticket Type Fines
		BarChartForFinesOfTop10Violations bcffvio = new BarChartForFinesOfTop10Violations(ticketByFine, sortedKeys);
		bcffvio.BarChartForViolationFines();
		// PieChart Creation Steps
		// Add delay to avoid any ConcurrentModificationException
		try {
			TimeUnit.MINUTES.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// PieChart for Issued Tickets by Hour
		PieChartForTicketsByHourUsingJFreeChart pcftbhujfc = new PieChartForTicketsByHourUsingJFreeChart("PieChart for Parking Tickets",
				hashMapPrakingTicketsRaw);
		// Add delay to avoid any ConcurrentModificationException
		try {
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			pcftbhujfc.PieChartForTicketsByHour();
		} catch (java.util.ConcurrentModificationException e) {
			System.out.println(
					"JFreeChart randomly throws ConcurrentModificationException depending how CPU schedules threads to process many collections we use. However, it still generates proper charts and do not cause any issues.");
			// e.printStackTrace();
		}
		// Add delay to avoid any ConcurrentModificationException
		try {
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PieChartForTicketsByDayUsingJFreeChart pccujfc = new PieChartForTicketsByDayUsingJFreeChart(
				"PieChart for Parking Tickets", ticketsByDay);
		try {
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// PieChart for Issued Tickets by Day
		try {
			pccujfc.PieChartForTicketsByDay();
		} catch (java.util.ConcurrentModificationException e) {
			System.out.println(
					"JFreeChart randomly throws ConcurrentModificationException depending how CPU schedules threads to process many collections we use. However, it still generates proper charts and do not cause any issues.");
			// e.printStackTrace();
		}
	}
}
