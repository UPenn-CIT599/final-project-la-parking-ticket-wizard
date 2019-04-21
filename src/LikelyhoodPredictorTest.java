import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class LikelyhoodPredictorTest {

	@Test
	void testPredict() {
		LikelyhoodPredictor predictor =new LikelyhoodPredictor();
		HashMap<String, Integer> history= new HashMap<String, Integer>();
		for (int i=1; i<=24;i++) {
			history.put(String.valueOf(i), i);
		}
		history.put(String.valueOf(8),0);
		assertEquals(predictor.predict(String.valueOf(3), history), "LIKELY");		
		assertEquals(predictor.predict(String.valueOf(24), history), "VERY LIKELY");
		assertEquals(predictor.predict(String.valueOf(8), history), "NOT LIKELY");
				//fail("Not yet implemented");
	}

}
