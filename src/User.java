import java.util.Scanner;

/***
 * 
 * @author Chan Woo Yang
 *
 */

public class User {
	
	// instance variable
	private Scanner userInput;
	
	private boolean userOptionValidityChecker(String userOption, int numberOfOption) {

		// user option input always needs to be a single character
		if (userOption.length() > 1) {
			return false;
		}

		// character 0 ~ 9 --> ASCII value 48 - 57
		if ((int) userOption.charAt(0) >= 48 && (int) userOption.charAt(0) <= 57) {
			if (Integer.valueOf(userOption) < 1 || Integer.valueOf(userOption) > numberOfOption) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public int chooseOption(int numberOfOption) {
		String userOption;
		userInput = new Scanner(System.in);
		userOption = userInput.next();

		while (!userOptionValidityChecker(userOption, numberOfOption)) {
			System.out.println("Invalid input. Please type it, again.");
			userOption = userInput.next();
		}
		int userOptionInt = Integer.valueOf(userOption);

		return userOptionInt;
	}
}
