import java.util.*;

import org.jfree.ui.RefineryUtilities;

public class ParkingTicketRunner {
	public static void main(String[] args) {
		//HashMapCreator hmc = new HashMapCreator("parking-citations.csv_cleaned_no_empty_column.csv");
		FileHandler fh = new FileHandler("parking-citations_cleaned.csv");

		HashMap<Integer, ParkingTickets> parkingTicketRaw = fh.getParkingTicketsRaw();
		
		System.out.println("Days that ticket issues are collected");
		System.out.println(parkingTicketRaw.size());
		System.out.println("");
		
		ParkingTicketDataProcessor ptdp = new ParkingTicketDataProcessor(parkingTicketRaw);

		System.out.println("***********************************");
		HashMap<String, Integer> ticketsByTime = ptdp.ticketCountsByHour();
		GraphTicketsByHour gtbh = new GraphTicketsByHour("Tickets By 3 Hours", ticketsByTime);
		gtbh.setSize(560, 367);
		RefineryUtilities.centerFrameOnScreen(gtbh);
		gtbh.setVisible(true);
		
		//ptdp.ticketCountsByHour();
		System.out.println("");

		//ParkingTicketByViolationDescription ptbv = new ParkingTicketByViolationDescription(hmc.parkingTicketRaw);
		System.out.println("***********************************");
		HashMap<String, Integer> ticketByVioDes = ptdp.ticketCountsByViolation();
		BarChartForViolationDescription bcfvd = new BarChartForViolationDescription(ticketByVioDes);
		bcfvd.setVisible(true);
		
		
		System.out.println("");
		System.out.println("***********************************");
		ptdp.ticketsByFine();
		System.out.println("");
		System.out.println("***********************************");
		ptdp.ticketsByDay();
		System.out.println("***********************************");
		ptdp.ticketsByProbDayTime();
		
	}
}
