import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/***
 * JUnit Test for ParkingTickets Class
 * 
 * @author Chan Woo Yang
 *
 */
class ParkingTicketsTest {

	ParkingTickets parkingTicket;

	@Test
	void testGetParkingTicketsArray() {
		String[] columnRawData = { "1106506446", "2015-12-22T00:00:00", "1110", "", "", "CA", "201511", "", "BMW", "PA",
				"BK", "1200 W MIRAMAR", "2A75", "1", "4000A1", "NO EVIDENCE OF REG", "50", "99999", "99999" };
		parkingTicket = new ParkingTickets(columnRawData);
		String[] instanceVariableArray = parkingTicket.getParkingTicketsArray();

		assertEquals("1106506446", instanceVariableArray[0]);
		assertEquals("2015-12-22", instanceVariableArray[1]);
		assertEquals("1110", instanceVariableArray[2]);
		assertEquals("", instanceVariableArray[3]);
		assertEquals("", instanceVariableArray[4]);
		assertEquals("CA", instanceVariableArray[5]);
		assertEquals("201511", instanceVariableArray[6]);
		assertEquals("", instanceVariableArray[7]);
		assertEquals("BMW", instanceVariableArray[8]);
		assertEquals("PA", instanceVariableArray[9]);
		assertEquals("BK", instanceVariableArray[10]);
		assertEquals("", instanceVariableArray[11]);
		assertEquals("", instanceVariableArray[12]);
		assertEquals("", instanceVariableArray[13]);
		assertEquals("4000A1", instanceVariableArray[14]);
		assertEquals("NO EVIDENCE OF REG", instanceVariableArray[15]);
		assertEquals("50", instanceVariableArray[16]);
		assertEquals("99999.0", instanceVariableArray[17]);
		assertEquals("99999.0", instanceVariableArray[18]);
	}

	@Test
	void testEmptyIntCellHandler() {
		String[] columnRawData = { "1106506446", "2015-12-22T00:00:00", "1110", "", "", "CA", "201511", "", "BMW", "PA",
				"BK", "1200 W MIRAMAR", "2A75", "1", "4000A1", "NO EVIDENCE OF REG", "50", "99999", "99999" };
		parkingTicket = new ParkingTickets(columnRawData);
		String wrongInput = "hello";
		String emptyInput = "";
		String correctInput = "32";
		
		assertEquals(0,parkingTicket.emptyIntCellHandler(wrongInput));
		assertEquals(0,parkingTicket.emptyIntCellHandler(emptyInput));
		assertEquals(32,parkingTicket.emptyIntCellHandler(correctInput));
	}
	
	@Test
	void testEmptyDoubleCellHandler() {
		String[] columnRawData = { "1106506446", "2015-12-22T00:00:00", "1110", "", "", "CA", "201511", "", "BMW", "PA",
				"BK", "1200 W MIRAMAR", "2A75", "1", "4000A1", "NO EVIDENCE OF REG", "50", "99999", "99999" };
		parkingTicket = new ParkingTickets(columnRawData);
		
		String wrongInput = "hello";
		String emptyInput = "";
		String correctInput = "123.456";
		
		assertEquals(0.0,parkingTicket.emptyDoubleCellHandler(wrongInput));
		assertEquals(0.0,parkingTicket.emptyDoubleCellHandler(emptyInput));
		assertEquals(123.456,parkingTicket.emptyDoubleCellHandler(correctInput));
	}
	
}
