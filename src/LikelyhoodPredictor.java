import java.util.*;
/**
 * This class will take in data from ParkingTicketDataProcessor class and 
 * perform predictive analysis on the data.
 * We have implemented likelyhood prediction using location filtered historical parking ticket
 * distribution by hour of the day 
 * We are currently looking into using Weka to do train a machine learning model for prediction, addtional 
 * methods may be added to this class.
 * However we are not sure if this particular dataset is going to suitable for machine learning application
 * due to lack of labeling as we no data for people who violated parking regulation but didn't get ticketed.
 *
 * @author Weiwen Zhao
 *
 */
public class LikelyhoodPredictor {
	/**
	 * This method takes in hashmap historical tickets issued by hour, then evaluate
	 * where current hour stands in the historical distribution. Then the method
	 * will return a message with likelyhood percentile to user.
	 * 
	 * @param currentTime
	 * @param curTicketByTime
	 * @return message prompt + likelyhood percentile
	 */
	public String predict(int currentTimeHour, HashMap<Integer, Integer> curTicketByTime) {
		int totalTickets = 0;
		int zeroCounter = 0;
		for (Integer timeHour : curTicketByTime.keySet()) {
			if (curTicketByTime.get(timeHour) == 0) {
				zeroCounter++;
			}
			totalTickets += curTicketByTime.get(timeHour);
		}
		int currentHourTickets = curTicketByTime.get(currentTimeHour);
		if (currentHourTickets == 0) {
	
         return "No historical ticket issued at this hour, park with your best judgement!";
		}
		int averageTicket = totalTickets / (24 - zeroCounter);
		double percentile = currentHourTickets * 100 / totalTickets;
		if (currentHourTickets >= averageTicket) {

			return "You are at higher than average likelyhood to get ticketed, park with extreme caution!";
		} else {

			return "You are at lower than average likelyhood to get ticketed, park with caution!";
		}

	}

	
}
