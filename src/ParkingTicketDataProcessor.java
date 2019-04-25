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
			System.out.println(key + " : " + decendingSortedTCBT.get(key));
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

		ArrayList<String> sortedKeys = new ArrayList<String>(decendingSortedTCBVD.keySet());

		for (int i = 0; i < 10; i++) {
			String key = sortedKeys.get(i);
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
	 * Method to sort tickets by Fines. Show top 10 most expensive ticket fines and violation descriptions.
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
		
		Map<String, Integer> unSortedMap = ticketByFine;	
		LinkedHashMap<String, Integer> decendingSortedTBF = new LinkedHashMap<>();	
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> decendingSortedTBF.put(x.getKey(), x.getValue()));
	
		ArrayList<String> sortedKeys = new ArrayList<String>(decendingSortedTBF.keySet());

		for (int i = 0; i < 10; i++) {
			String key = sortedKeys.get(i);
			System.out.println((key + " : " + decendingSortedTBF.get(key)));
		}
		/* Printing all descriptions : used initially to understand data trend.
		 * for (String key : decendingSortedTBF.keySet()) {
			if(decendingSortedTBF.get(key) >= 25) {
				System.out.println(key + " : " + decendingSortedTBF.get(key));
			}
		}*/
		
		return decendingSortedTBF;
	
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
			System.out.println(key + " : " + decendingSortedTCBD.get(key));
		}
		
		return decendingSortedTCBD;
		
	}
	
	/**
	 * This method collects number of issued violation tickets by time of the day. This will tell us 
	 * what time
	 * of the day has more issued tickets.
	 * @return
	 */
	public HashMap<String, Integer> ticketsByProbDayTime () {

		ArrayList <String> ticketsByDay = new ArrayList<String>();
		ArrayList <Integer> ticketsByTime = new ArrayList<Integer>();
		ArrayList <String> ticketsByDayTime = new ArrayList<String>();
		HashMap<String, Integer> ticketCountsByDayTime = new HashMap<String, Integer>();

		for (Integer currentTicket : parkingTicketsRaw.keySet()) {	
			Integer ticketTime = this.parkingTicketsRaw.get(currentTicket).getIssueTime();	
			String ticketDate = this.parkingTicketsRaw.get(currentTicket).getIssueDate();
			String ticketDay = dateToDayConversion(ticketDate);
		for (int i = 0; i < 24; i++) {
			String time = i + ":00-" + (i+1) + ":00";
			timeByHour[i] = time;
			//System.out.println(timeByHour[i]);
		}
			ticketsByDay.add(ticketDay);	
			ticketsByTime.add(ticketTime);
		}
		
		//TODO: For each day, sort hours by number of tickets.
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
		
		//System.out.println(ticketsByDayTime);
		
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
        
        //TODO: Below is sample code snippets for Tuesday. Need to create for all 7 days and hashmaps 
        //containing ticket counts and probabilities.
        
		HashMap<String, Integer> ticketStatForTue = new HashMap<String, Integer>();
		int totalTicketCounts = 0;
		int totalTicketCountsForTuesday = 0;
		
		for (String key : sortedKeys) {
			
			System.out.println(key + ": " + ticketCountsByDayTime.get(key));		
			totalTicketCounts = totalTicketCounts + ticketCountsByDayTime.get(key);
					
			if (key.contains("Tuesday")) {
				ticketStatForTue.put(key, ticketCountsByDayTime.get(key));
				totalTicketCountsForTuesday = totalTicketCountsForTuesday + ticketCountsByDayTime.get(key);
			}
		}
		System.out.println("");
		System.out.println("Total Tickets : " + totalTicketCounts);
		System.out.println("**************************************");
		System.out.println("");
		System.out.println("Total Tuesday Tickets : " + totalTicketCountsForTuesday);
		System.out.println("**************************************");
		System.out.println("");
		
		Map<String, Integer> tempMapTue = ticketStatForTue;
        ArrayList<String> sortedKeysTue = new ArrayList<>(tempMapTue.keySet());
        Collections.sort(sortedKeysTue);
	
		for (String key : sortedKeysTue) {
			System.out.println(key + " ticket count is " + ticketStatForTue.get(key) + " and probability is " + (ticketStatForTue.get(key)*1.0/totalTicketCountsForTuesday)*100.0);
			
		}
		//System.out.println(ticketStatForTue);
		
		return ticketCountsByDayTime;
		
	}
	
}
