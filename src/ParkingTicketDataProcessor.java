import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data Processor class contains methods for analyzing violation data.
 * From this analysis, we will collect the data trend of 
 * i. Rank day of the week by number of issued tickets. Calculate probability of issued tickets for each day.
 * ii. Rank time of the day by number of issued tickets. Calculate probability of issued tickets for each hour.
 * iii. Rank area (zone) of LA by number of issued tickets. Calculate probability of issued tickets for each zone.
 * 
 * We will combine all three parameters and rank them by order of issued tickets. Then, compare user's input of 
 * address, day and time of parking information and predict likelihood of getting tickets if he/she commits 
 * parking violation.
 *
 * We can add analysis of type of violations as well.
 * 
 * @author lukeshin
 *
 */

public class ParkingTicketDataProcessor {
	
	private HashMap<Integer, ParkingTickets> parkingTicketsRaw;
	String[] timeByHour = new String[24];
	HashMap<String, Integer> sortedMonTickets;
	HashMap<String, Integer> sortedTueTickets;
	HashMap<String, Integer> sortedWedTickets;
	HashMap<String, Integer> sortedThuTickets;
	HashMap<String, Integer> sortedFriTickets;
	HashMap<String, Integer> sortedSatTickets;
	HashMap<String, Integer> sortedSunTickets;
	int totalTicketCounts = 0;
	int totalTicketCountsForMonday = 0;
	int totalTicketCountsForTuesday = 0;
	int totalTicketCountsForWednesday = 0;
	int totalTicketCountsForThursday = 0;
	int totalTicketCountsForFriday = 0;
	int totalTicketCountsForSaturday = 0;
	int totalTicketCountsForSunday = 0;
	ArrayList<String> sortedKeysByVioDesc;


	public ParkingTicketDataProcessor (HashMap<Integer, ParkingTickets> curParkingTicketsData) {		
		this.parkingTicketsRaw = curParkingTicketsData;
		//System.out.println(this.parkingTicketsRaw.size());
		for (int i = 0; i < 24; i++) {
			String time = i + ":00-" + (i + 1) + ":00";
			timeByHour[i] = time;
			// System.out.println(timeByHour[i]);
		}
	}

	/**
	 * Analyzing violation tickets by time of the day. Collect issued ticket counts by hour.
	 * @return
	 */
	
	public HashMap<String, Integer> ticketCountsByHour() {

		HashMap<String, Integer> ticketCountsByTime = new HashMap<String, Integer>();
		
		for (Integer currentTicket : parkingTicketsRaw.keySet()) {
			int ticketTime = this.parkingTicketsRaw.get(currentTicket).getIssueTime();
			for (int i = 0; i < 24; i++) {
				if ((ticketTime >= i * 100) && (ticketTime < (i + 1) * 100)) {
					int ticketCount = ticketCountsByTime.containsKey(timeByHour[i])
							? ticketCountsByTime.get(timeByHour[i]) : 0;
					ticketCount = ticketCount + 1;
					ticketCountsByTime.put(timeByHour[i], ticketCount);
				}
			}
		}
		// Using Map interface and java stream to sort by values of HashMap
		Map<String, Integer> unSortedMap = ticketCountsByTime;
		LinkedHashMap<String, Integer> decendingSortedTCBT = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> decendingSortedTCBT.put(x.getKey(), x.getValue()));
		for (String key : decendingSortedTCBT.keySet()) {
			System.out.print(key + "," + decendingSortedTCBT.get(key) + ",");
			System.out.printf("%4.2f", (decendingSortedTCBT.get(key)*1.0/this.parkingTicketsRaw.size())*100.0);
			System.out.println("%");
		}
		return decendingSortedTCBT;
	}

	/**
	 * Method to show ticket counts by violation description. Method sorts by
	 * violation counts and show top 10 violation categories.
	 * 
	 * @return HashMap of Violation description and its corresponding number of
	 *         issued tickets.
	 */
	public HashMap<String, Integer> ticketCountsByViolation() {

		HashMap<String, Integer> ticketCountsByViolationDescription = new HashMap<String, Integer>();

		for (Integer currentTicket : parkingTicketsRaw.keySet()) {
			String ticketVioDescription = this.parkingTicketsRaw.get(currentTicket).getViolationDescription();
			int tVioDesCount = ticketCountsByViolationDescription.containsKey(ticketVioDescription)
					? ticketCountsByViolationDescription.get(ticketVioDescription) : 0;
			tVioDesCount = tVioDesCount + 1;
			ticketCountsByViolationDescription.put(ticketVioDescription, tVioDesCount);
		}
		
		Map<String, Integer> unSortedMap = ticketCountsByViolationDescription;
		LinkedHashMap<String, Integer> decendingSortedTCBVD = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> decendingSortedTCBVD.put(x.getKey(), x.getValue()));

		sortedKeysByVioDesc = new ArrayList<String>(decendingSortedTCBVD.keySet());

		for (int i = 0; i < 10; i++) {
			String key = sortedKeysByVioDesc.get(i);
			System.out.println((key + " : " + decendingSortedTCBVD.get(key)));
		}
		
		/* Printing all descriptions : used initially to understand data trend.
		 * for (String key : decendingSortedTCBVD.keySet()) {
			if (decendingSortedTCBVD.get(key) > 1) {
				System.out.println(key + " : " + decendingSortedTCBVD.get(key));
			}
		}*/		 
		return decendingSortedTCBVD;
	}
	
	/**
	 * Method to sort tickets by Fines. Show top 10 most violation description fines.
	 * @return HashMap of ticket violation and corresponding fine.
	 */
	public HashMap<String, Integer> ticketsByFine () {
		
		HashMap<String, Integer> ticketByFine = new HashMap<String, Integer>();
		
		for (Integer currentTicket : parkingTicketsRaw.keySet()) {			
			String ticketVioDescription = this.parkingTicketsRaw.get(currentTicket).getViolationDescription();
			Integer ticketFine = this.parkingTicketsRaw.get(currentTicket).getFine();	
			if (!ticketByFine.containsKey(ticketVioDescription)) {
				ticketByFine.put(ticketVioDescription, ticketFine);
			}
		}
		
		/* Finding Top 10 most expensive ticket fines.
		Map<String, Integer> unSortedMap = ticketByFine;	
		LinkedHashMap<String, Integer> decendingSortedTBF = new LinkedHashMap<>();	
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> decendingSortedTBF.put(x.getKey(), x.getValue()));
	
		ArrayList<String> sortedKeys = new ArrayList<String>(decendingSortedTBF.keySet());
		for (int i = 0; i < 10; i++) {
			String key = sortedKeysByVioDesc.get(i);
			System.out.println((key + " : " + decendingSortedTBF.get(key)));
		}*/
		
		// Printing fines for top 10 ticket violations
		for (int i = 0; i < 10; i++) {
			String key = sortedKeysByVioDesc.get(i);
			System.out.println((key + " : $" + ticketByFine.get(key)));
		}		
		return ticketByFine;	
	}
	
	/**
	 * Helper Method to convert issue dates by day of the week. Date to day conversion 
	 * using SimpleDataFormat.
	 * @param curDate
	 * @return
	 */
	public String dateToDayConversion(String curDate) {
		
		String input_date = curDate;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = null;
		String ticketDay = null;
		try {
			dt1 = format1.parse(input_date);
			DateFormat format2 = new SimpleDateFormat("EEEE");
			ticketDay = format2.format(dt1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return ticketDay;		
	}
	
	/**
	 * This method will tell which day of the week has the most number of issued tickets.
	 * @return HashMap contains day of the week and number of violations per day of the week.
	 */
	public HashMap<String, Integer> ticketsByDay() {
		
		HashMap<String, Integer> ticketsByDay = new HashMap<String, Integer>();
		
		for (Integer currentTicket : parkingTicketsRaw.keySet()) {
			String ticketDate = this.parkingTicketsRaw.get(currentTicket).getIssueDate();			
			String ticketDay = dateToDayConversion(ticketDate);
			int ticketCountsPerDay = ticketsByDay.containsKey(ticketDay) ? ticketsByDay.get(ticketDay) : 0;
			ticketCountsPerDay = ticketCountsPerDay + 1;
			ticketsByDay.put(ticketDay, ticketCountsPerDay);
		}
		
		Map<String, Integer> unSortedMap = ticketsByDay;
		LinkedHashMap<String, Integer> decendingSortedTCBD = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> decendingSortedTCBD.put(x.getKey(), x.getValue()));
		for (String key : decendingSortedTCBD.keySet()) {
			System.out.print(key + "," + decendingSortedTCBD.get(key) + ",");
			System.out.printf("%4.2f", (decendingSortedTCBD.get(key)*1.0/parkingTicketsRaw.size()*100.0));
			System.out.println("%");
		}
		return decendingSortedTCBD;		
	}
	
	/**
	 * Helper method to sort by ticket counts 
	 * @param curHashMap
	 * @return
	 */
	public HashMap<String, Integer> hashMapSorting (HashMap<String, Integer> curHashMap) {
		
		Map<String, Integer> unSortedMap = curHashMap;
		LinkedHashMap<String, Integer> sortedByValueHashMap = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> sortedByValueHashMap.put(x.getKey(), x.getValue()));
		
		return sortedByValueHashMap;
	}
		
	/**
	 * This method collects number of issued violation tickets by time of the day. This will tell us 
	 * what time
	 * of the day has more issued tickets.
	 * @return
	 */
	public void ticketsByProbDayTime () {

		ArrayList <String> ticketsByDay = new ArrayList<String>();
		ArrayList <Integer> ticketsByTime = new ArrayList<Integer>();
		ArrayList <String> ticketsByDayTime = new ArrayList<String>();
		HashMap<String, Integer> ticketCountsByDayTime = new HashMap<String, Integer>();

		for (Integer currentTicket : parkingTicketsRaw.keySet()) {	
			Integer ticketTime = this.parkingTicketsRaw.get(currentTicket).getIssueTime();	
			String ticketDate = this.parkingTicketsRaw.get(currentTicket).getIssueDate();
			String ticketDay = dateToDayConversion(ticketDate);

			ticketsByDay.add(ticketDay);	
			ticketsByTime.add(ticketTime);
		}
		
		for (int i = 0; i < ticketsByDay.size(); i++) {
			Integer ticketTime = ticketsByTime.get(i);
			String ticketDay = ticketsByDay.get(i);	
			String ticketDayTime = null;
			if ((!ticketTime.equals(null)) || !ticketDay.contentEquals(null)) {
				for (int j = 0; j < 24; j++) {
					if ((ticketTime >= j * 100) && (ticketTime < (j + 1) * 100)) {
						ticketDayTime = ticketDay + "," + timeByHour[j];
					}
				}
				ticketsByDayTime.add(ticketDayTime);	
			}					
		}
				
		for (int i = 0; i < ticketsByDayTime.size(); i++) {			
			String ticketStat = ticketsByDayTime.get(i);
			int ticketCount = ticketCountsByDayTime.containsKey(ticketStat) ? ticketCountsByDayTime.get(ticketStat) : 0;
			ticketCount = ticketCount + 1;
			ticketCountsByDayTime.put(ticketStat, ticketCount);
		}
		
		Map<String, Integer> tempMap = ticketCountsByDayTime;
        ArrayList<String> sortedKeys = new ArrayList<>(tempMap.keySet());
        Collections.sort(sortedKeys);
        System.out.println("");
        
		HashMap<String, Integer> ticketStatForMon = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForTue = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForWed = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForThu = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForFri = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForSat = new HashMap<String, Integer>();
		HashMap<String, Integer> ticketStatForSun = new HashMap<String, Integer>();
		
		for (String key : sortedKeys) {			
			//System.out.println(key + ": " + ticketCountsByDayTime.get(key));		
			totalTicketCounts = totalTicketCounts + ticketCountsByDayTime.get(key);
			
			if (key.contains("Monday")) {
				ticketStatForMon.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForMonday = totalTicketCountsForMonday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Tuesday")) {
				ticketStatForTue.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForTuesday = totalTicketCountsForTuesday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Wednesday")) {
				ticketStatForWed.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForWednesday = totalTicketCountsForWednesday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Thursday")) {
				ticketStatForThu.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForThursday = totalTicketCountsForThursday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Friday")) {
				ticketStatForFri.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForFriday = totalTicketCountsForFriday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Saturday")) {
				ticketStatForSat.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForSaturday = totalTicketCountsForSaturday + ticketCountsByDayTime.get(key);
			}
			else if (key.contains("Sunday")) {
				ticketStatForSun.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForSunday = totalTicketCountsForSunday + ticketCountsByDayTime.get(key);
			}
		}
		
		HashMap<String, Integer> sortedMonTickets = hashMapSorting(ticketStatForMon);
		HashMap<String, Integer> sortedTueTickets = hashMapSorting(ticketStatForTue);
		HashMap<String, Integer> sortedWedTickets = hashMapSorting(ticketStatForWed);
		HashMap<String, Integer> sortedThuTickets = hashMapSorting(ticketStatForThu);
		HashMap<String, Integer> sortedFriTickets = hashMapSorting(ticketStatForFri);
		HashMap<String, Integer> sortedSatTickets = hashMapSorting(ticketStatForSat);
		HashMap<String, Integer> sortedSunTickets = hashMapSorting(ticketStatForSun);
		
		for (String key : sortedMonTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedMonTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedMonTickets.get(key)*1.0/totalTicketCountsForMonday)*100.0);
			System.out.println("%");		
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedTueTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedTueTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedTueTickets.get(key)*1.0/totalTicketCountsForTuesday)*100.0);
			System.out.println("%");
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedWedTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedWedTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedWedTickets.get(key)*1.0/totalTicketCountsForWednesday)*100.0);
			System.out.println("%");
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedThuTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedThuTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedThuTickets.get(key)*1.0/totalTicketCountsForThursday)*100.0);
			System.out.println("%");
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedFriTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedFriTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedFriTickets.get(key)*1.0/totalTicketCountsForFriday)*100.0);
			System.out.println("%");
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedSatTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedSatTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedSatTickets.get(key)*1.0/totalTicketCountsForSaturday)*100.0);
			System.out.println("%");
		}
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("");
		for (String key : sortedSunTickets.keySet()) {
			System.out.print(key + " ticket count is " + sortedSunTickets.get(key) + " and probability is ");
			System.out.printf("%4.2f", (sortedSunTickets.get(key)*1.0/totalTicketCountsForSunday)*100.0);
			System.out.println("%");
		}
	}
	
	public HashMap<String, Integer> getSortedMonTickets() {
		return sortedMonTickets;
	}

	public HashMap<String, Integer> getSortedTueTickets() {
		return sortedTueTickets;
	}

	public HashMap<String, Integer> getSortedWedTickets() {
		return sortedWedTickets;
	}

	public HashMap<String, Integer> getSortedThuTickets() {
		return sortedThuTickets;
	}

	public HashMap<String, Integer> getSortedFriTickets() {
		return sortedFriTickets;
	}

	public HashMap<String, Integer> getSortedSatTickets() {
		return sortedSatTickets;
	}

	public HashMap<String, Integer> getSortedSunTickets() {
		return sortedSunTickets;
	}
}
