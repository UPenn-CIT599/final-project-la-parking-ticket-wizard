import java.util.*;

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
	public String predict(String currentTime, HashMap<String, Integer> curTicketByTime) {
		int totalTickets = 0;
		int zeroCounter = 0;
		for (String key : curTicketByTime.keySet()) {
			if (curTicketByTime.get(key) == 0) {
				zeroCounter++;
			}
			totalTickets += curTicketByTime.get(key);
		}
		int currentHourTickets = curTicketByTime.get(currentTime);
		if (currentHourTickets == 0) {
			 return "NOT LIKELY";
		//	return "No historical ticket issued at this hour, park with your best judgement!";
		}
		int averageTicket = totalTickets / (24 - zeroCounter);
		double percentile = currentHourTickets * 100 / totalTickets;
		if (currentHourTickets >= averageTicket) {
			return "VERY LIKELY";
		//	return "You are at higher than average likelyhood to get ticketed, your likelyhood percentile is at "
		//			+ String.valueOf(percentile) + " park with extreme caution!";
		} else {
	        return "LIKELY";
		//	return "You are at lower than average likelyhood to get ticketed, your likelyhood percentile is at "
		//			+ String.valueOf(percentile) + " park with caution!";
		}

	}

	
}
