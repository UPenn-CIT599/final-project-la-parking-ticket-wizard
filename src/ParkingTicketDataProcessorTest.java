import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class ParkingTicketDataProcessorTest {
	
	FileHandler fh = new FileHandler("parking-citations_cleaned_extrasmall.csv");
	HashMap<Integer, ParkingTickets> hashMapParkingTicketsRaw = fh.getParkingTicketsRaw();
	ParkingTicketDataProcessor ptdp = new ParkingTicketDataProcessor(hashMapParkingTicketsRaw);

	@Test
	void testParkingTicketDataProcessor() {
		assertEquals("2015-12-21", hashMapParkingTicketsRaw.get(1).getIssueDate());
	}

	@Test
	void testTicketCountsByHourGetKey() {
		
		Boolean key = ptdp.ticketCountsByHour().containsKey("20:00-21:00");
		assertEquals(true, key);
	}
	
	@Test
	void testTicketCountsByHour() {
		
		int count = ptdp.ticketCountsByHour().get("20:00-21:00");
		assertEquals(5, count);
	}

	@Test
	void testDateToDayConversion() {
		assertEquals("Monday", ptdp.dateToDayConversion("2015-12-21"));
	}
	
	@Test
	void testTicketsByDay() {
		int count = ptdp.ticketsByDay().get("Monday");
		assertEquals(5, count);
	}

}
