import java.util.*;
import org.jfree.ui.RefineryUtilities;

public class ParkingTicketRunner {
	public static void main(String[] args) {

		//FileHandler fh = new FileHandler("parking-citations_extrasmall.csv");		
		FileHandler fh = new FileHandler("parking-citations_cleaned.csv");
		HashMap<Integer, ParkingTickets> hashMapPrakingTicketsRaw = fh.getParkingTicketsRaw();
		ParkingTicketDataProcessor ptdp = new ParkingTicketDataProcessor(hashMapPrakingTicketsRaw);
		
		System.out.println("Days that ticket issues are collected");
		System.out.println(hashMapPrakingTicketsRaw.size());
		System.out.println("");
		System.out.println("***********************************");
		// PieChart for Issued Tickets by Hour
		HashMap<String, Integer> ticketsByTime = ptdp.ticketCountsByHour();
		PieChartCreatorUsingJFreeChart pccujfc = new PieChartCreatorUsingJFreeChart("PieChart for Parking Tickets");
		pccujfc.PieChartForTicketsByHour(ticketsByTime);
		RefineryUtilities.centerFrameOnScreen(pccujfc);		
		System.out.println("");
		System.out.println("***********************************");
		HashMap<String, Integer> ticketsByDay = ptdp.ticketsByDay();
		pccujfc.PieChartForTicketsByDay(ticketsByDay);
		RefineryUtilities.centerFrameOnScreen(pccujfc);		
		System.out.println("");
		System.out.println("***********************************");		
		// BarChart for Top 10 Violation Ticket Type Descriptions
		BarChartCreatorUsingJFreeChart bccujfc = new BarChartCreatorUsingJFreeChart();
		HashMap<String, Integer> ticketByVioDes = ptdp.ticketCountsByViolation();
		bccujfc.BarChartForViolationDescription(ticketByVioDes);
		System.out.println("");
		System.out.println("***********************************");
		// BarChart for Top 10 Violation Ticket Type Fines
		HashMap<String, Integer> ticketByFine = ptdp.ticketsByFine();
		ArrayList<String> sortedKeys = ptdp.sortedKeysByVioDesc;
		bccujfc.BarChartForViolationFines(ticketByFine, sortedKeys);
		System.out.println("");
		System.out.println("***********************************");		
		ptdp.ticketsByProbDayTime();
		System.out.println("***********************************");
	}
}
