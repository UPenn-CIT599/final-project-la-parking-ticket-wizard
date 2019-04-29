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
 * Data Processor class contains methods for analyzing violation data. From this
 * analysis, we will collect the data trend of i. Rank day of the week by number
 * of issued tickets. Calculate probability of issued tickets for each day. ii.
 * Rank time of the day by number of issued tickets. Calculate probability of
 * issued tickets for each hour. iii. Rank area (zone) of LA by number of issued
 * tickets. Calculate probability of issued tickets for each zone.
 * 
 * We will combine all three parameters and rank them by order of issued
 * tickets. Then, compare user's input of address, day and time of parking
 * information and predict likelihood of getting tickets if he/she commits
 * parking violation.
 *
 * We can add analysis of type of violations as well.
 * 
 * @author lukeshin, Chan Woo Yang
 *
 */

public class ParkingTicketDataProcessor {

	// instance variable
	private HashMap<Integer, ParkingTickets> parkingTicketsRaw;
	private String[] timeByHour = new String[24];
	private ArrayList<String> sortedKeysByVioDesc;

	// constructor
	public ParkingTicketDataProcessor(HashMap<Integer, ParkingTickets> curParkingTicketsData) {
		this.parkingTicketsRaw = curParkingTicketsData;
		// System.out.println(this.parkingTicketsRaw.size());
		for (int i = 0; i < 24; i++) {
			String time = i + ":00-" + (i + 1) + ":00";
			timeByHour[i] = time;
			// System.out.println(timeByHour[i]);
		}
	}

	/**
	 * Analyzing violation tickets by time of the day. Collect issued ticket counts
	 * by hour.
	 * 
	 * @return
	 */

	public HashMap<String, Integer> ticketCountsByHour() {

		HashMap<String, Integer> ticketCountsByTime = new HashMap<String, Integer>();

		for (Integer currentTicket : parkingTicketsRaw.keySet()) {
			int ticketTime = this.parkingTicketsRaw.get(currentTicket).getIssueTime();
			for (int i = 0; i < 24; i++) {
				if ((ticketTime >= i * 100) && (ticketTime < (i + 1) * 100)) {
					int ticketCount = ticketCountsByTime.containsKey(timeByHour[i])
							? ticketCountsByTime.get(timeByHour[i])
							: 0;
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
			System.out.printf("%4.2f", (decendingSortedTCBT.get(key) * 1.0 / this.parkingTicketsRaw.size()) * 100.0);
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
					? ticketCountsByViolationDescription.get(ticketVioDescription)
					: 0;
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

		/*
		 * Printing all descriptions : used initially to understand data trend. for
		 * (String key : decendingSortedTCBVD.keySet()) { if
		 * (decendingSortedTCBVD.get(key) > 1) { System.out.println(key + " : " +
		 * decendingSortedTCBVD.get(key)); } }
		 */
		return decendingSortedTCBVD;
	}

	/**
	 * Method to sort tickets by Fines. Show top 10 most violation description
	 * fines.
	 * 
	 * @return HashMap of ticket violation and corresponding fine.
	 */
	public HashMap<String, Integer> ticketsByFine() {

		HashMap<String, Integer> ticketByFine = new HashMap<String, Integer>();

		for (Integer currentTicket : parkingTicketsRaw.keySet()) {
			String ticketVioDescription = this.parkingTicketsRaw.get(currentTicket).getViolationDescription();
			Integer ticketFine = this.parkingTicketsRaw.get(currentTicket).getFine();
			if (!ticketByFine.containsKey(ticketVioDescription)) {
				ticketByFine.put(ticketVioDescription, ticketFine);
			}
		}

		/*
		 * Finding Top 10 most expensive ticket fines. Map<String, Integer> unSortedMap
		 * = ticketByFine; LinkedHashMap<String, Integer> decendingSortedTBF = new
		 * LinkedHashMap<>();
		 * unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.
		 * reverseOrder())) .forEachOrdered(x -> decendingSortedTBF.put(x.getKey(),
		 * x.getValue()));
		 * 
		 * ArrayList<String> sortedKeys = new
		 * ArrayList<String>(decendingSortedTBF.keySet()); for (int i = 0; i < 10; i++)
		 * { String key = sortedKeysByVioDesc.get(i); System.out.println((key + " : " +
		 * decendingSortedTBF.get(key))); }
		 */

		// Printing fines for top 10 ticket violations
		for (int i = 0; i < 10; i++) {
			String key = sortedKeysByVioDesc.get(i);
			System.out.println((key + " : $" + ticketByFine.get(key)));
		}
		return ticketByFine;
	}

	/**
	 * Helper Method to convert issue dates by day of the week. Date to day
	 * conversion using SimpleDataFormat.
	 * 
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
	 * This method will tell which day of the week has the most number of issued
	 * tickets.
	 * 
	 * @return HashMap contains day of the week and number of violations per day of
	 *         the week.
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
			System.out.printf("%4.2f", (decendingSortedTCBD.get(key) * 1.0 / parkingTicketsRaw.size() * 100.0));
			System.out.println("%");
		}
		return decendingSortedTCBD;
	}

	/**
	 * Helper method to sort by ticket counts
	 * 
	 * @param curHashMap
	 * @return
	 */
	public HashMap<String, Integer> hashMapSorting(HashMap<String, Integer> curHashMap) {

		Map<String, Integer> unSortedMap = curHashMap;
		LinkedHashMap<String, Integer> sortedByValueHashMap = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> sortedByValueHashMap.put(x.getKey(), x.getValue()));

		return sortedByValueHashMap;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, HashMap<Integer, Integer>> ticketsCountsByDayTime() {

		// HashMap<`Day`, HashMap<`TimeHour`, `ticketCount`>>
		HashMap<String, HashMap<Integer, Integer>> ticketByDayTime = new HashMap<String, HashMap<Integer, Integer>>();

		// Initialize nested HashMap pt.1
		ticketByDayTime.put("Monday", null);
		ticketByDayTime.put("Tuesday", null);
		ticketByDayTime.put("Wednesday", null);
		ticketByDayTime.put("Thursday", null);
		ticketByDayTime.put("Friday", null);
		ticketByDayTime.put("Saturday", null);
		ticketByDayTime.put("Sunday", null);

		// Initialize nested HashMap pt2.
		for (String today : ticketByDayTime.keySet()) {
			HashMap<Integer, Integer> currentDayTicket = new HashMap<Integer, Integer>();
			for (int timeHour = 0; timeHour < 24; timeHour++) {
				currentDayTicket.put(timeHour, 0);
			}
			ticketByDayTime.put(today, currentDayTicket);
		}

		// sort tickets by day and hourly time
		for (Integer currentTicketIdx : parkingTicketsRaw.keySet()) {
			String currentTicketDate = parkingTicketsRaw.get(currentTicketIdx).getIssueDate();
			String currentTicketDay = dateToDayConversion(currentTicketDate);
			int currentTicketTime = parkingTicketsRaw.get(currentTicketIdx).getIssueTime();
			int currentTicketTimeHour = currentTicketTime / 100;

			// find the right day hourly time hashmap
			HashMap<Integer, Integer> currentDayHourlyTimeHashMap = ticketByDayTime.get(currentTicketDay);
			// get the hourly time count
			int currentTimeHourCount = currentDayHourlyTimeHashMap.get(currentTicketTimeHour);
			// update the hourly time count
			currentTimeHourCount++;
			// update the HashMap
			currentDayHourlyTimeHashMap.put(currentTicketTimeHour, currentTimeHourCount);
			ticketByDayTime.put(currentTicketDay, currentDayHourlyTimeHashMap);
		}

//		System.out.println(ticketByDayTime);

		// print each day hourly ticket count distribution
		for (String today : ticketByDayTime.keySet()) {
			HashMap<Integer, Integer> todayCountDist = ticketByDayTime.get(today);
			System.out.println(today + " Hourly Ticket Count Distribution");
			for (Integer hourTime : todayCountDist.keySet()) {
				System.out.println(hourTime + ":00 - " + (hourTime + 1) + ":00 " + todayCountDist.get(hourTime));
			}
		}

		return ticketByDayTime;
	}

	/**
	 * 
	 * @param day
	 * @param ticketByDayTime
	 * @return
	 */
	public HashMap<String, HashMap<Integer, Double>> ticketsProbDistByDayTime() {
		HashMap<String, HashMap<Integer, Double>> ticketDayHourlyProbDist = new HashMap<String, HashMap<Integer, Double>>();

		HashMap<String, HashMap<Integer, Integer>> ticketByDayTime = ticketsCountsByDayTime();

		for (String today : ticketByDayTime.keySet()) {
			double totalDayTicketCount = 0.0;
			ticketDayHourlyProbDist.put(today, null);
			HashMap<Integer, Integer> todayCountDist = ticketByDayTime.get(today);
			// Compute total ticket counts for given input day
			for (Integer hourTime : todayCountDist.keySet()) {
				totalDayTicketCount += todayCountDist.get(hourTime);
			}
			
			// Compute hourly ticket distribution in percent
			HashMap<Integer, Double> ticketHourlyProbDist = new HashMap<Integer, Double>();
			for (Integer hourTime : todayCountDist.keySet()) {
				double ticketDistPcnt = (todayCountDist.get(hourTime) / totalDayTicketCount) * 100.0;
				ticketHourlyProbDist.put(hourTime, ticketDistPcnt);
			}
			
			ticketDayHourlyProbDist.put(today, ticketHourlyProbDist);
		}
		
		// print each day hourly ticket probability distribution
		for (String today : ticketDayHourlyProbDist.keySet()) {
			HashMap<Integer, Double> todayProbDist = ticketDayHourlyProbDist.get(today);
			System.out.println(today + " Hourly Ticket Probability Distribution");
			for (Integer hourTime : todayProbDist.keySet()) {
				System.out.println(hourTime + ":00 - " + (hourTime + 1) + ":00 " + todayProbDist.get(hourTime) + " %");
			}
		}

		return ticketDayHourlyProbDist;
	}
}
