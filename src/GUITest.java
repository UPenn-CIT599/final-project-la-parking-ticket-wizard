import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GUITest {

	@Test
	void testDayConverter() {
       assertEquals(GUI.dayConverter(1),"Monday");
       assertEquals(GUI.dayConverter(2),"Tuesday");
	}

}
