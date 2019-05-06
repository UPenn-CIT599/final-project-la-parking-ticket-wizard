import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class LikelyhoodPredictorTest {

	@Test
	void testPredict() {
		LikelyhoodPredictor predictor =new LikelyhoodPredictor();
		HashMap<Integer, Integer> history= new HashMap<Integer, Integer>();
		for (int i=1; i<=24;i++) {
			history.put(i, i);
		}
		history.put(8,0);
		assertEquals(predictor.predict(3, history), "You are at lower than average likelyhood to get ticketed, park with caution!");		
		assertEquals(predictor.predict(24, history), "You are at higher than average likelyhood to get ticketed, park with extreme caution!");
		assertEquals(predictor.predict(8, history), "No historical ticket issued at this hour, park with your best judgement!");
				//fail("Not yet implemented");
	}

}
