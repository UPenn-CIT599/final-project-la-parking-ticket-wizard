/***
 * This class is the user interaction class utilizing JavaFX
 * It interacts with user by predicting likelyhood of getting
 * a parking tickets based on user input location and time.
 * There is also a big data view for user to see big data analysis results
 * of the dataset.
 * 
 * @author Weiwenz33
 */
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {
	private static double X, Y;
	private static int DAY, HOUR;
	Stage window;
	String message;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set up title of Stage
		window = primaryStage;
		window.setTitle("Parking Wizard Los Angeles");

		// Define the GridPane for first scene
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		// Define the GridPane for second scene
		GridPane BigDataPane = new GridPane();
		BigDataPane.setPadding(new Insets(10, 10, 10, 10));
		BigDataPane.setVgap(8);
		BigDataPane.setHgap(10);

		Scene scene = new Scene(grid, 550, 300);
		Scene bigDataScene = new Scene(BigDataPane, 820, 1000);

		Label xLabel = new Label("X Coordinate Value:");
		GridPane.setConstraints(xLabel, 0, 1);

		TextField xInput = new TextField("6482261.8");

		GridPane.setConstraints(xInput, 1, 1);

		Label yLabel = new Label("Y Coordinate Value:");
		GridPane.setConstraints(yLabel, 0, 2);

		TextField yInput = new TextField("1837556.1");

		GridPane.setConstraints(yInput, 1, 2);

		Label dayLabel = new Label("Day of Week:");
		GridPane.setConstraints(dayLabel, 0, 3);

		Label dayLabelIntro = new Label("Eg: Tuesday enter 2 ");
		GridPane.setConstraints(dayLabelIntro, 2, 3);

		TextField day = new TextField("2");

		GridPane.setConstraints(day, 1, 3);

		Label hourLabel = new Label("Hour of Day:");
		GridPane.setConstraints(hourLabel, 0, 4);

		Label hourLabelIntro = new Label("Eg: 1:00PM enter 13");
		GridPane.setConstraints(hourLabelIntro, 2, 4);

		TextField hour = new TextField("13");

		GridPane.setConstraints(hour, 1, 4);

		Button predictButton = new Button("Predict Tickets");
		GridPane.setConstraints(predictButton, 1, 6);
		predictButton.setOnAction(e -> {
			// Validate user input for X Y Day and Hour
			boolean inputValid = false;
			try {
				X = Double.parseDouble(xInput.getText());
				Y = Double.parseDouble(yInput.getText());
				DAY = Integer.parseInt(day.getText());
				HOUR = Integer.parseInt(hour.getText());
				// Prompt message box if user input is invalid
			} catch (NumberFormatException E) {
				GUIMessageBox.display("Wrong Data Entry",
						"Please enter ONLY number for" + " X,Y Coordinates, Day of Week and Hour of Day!");
			}
			if (DAY >= 1 && DAY <= 7 && HOUR >= 0 && HOUR <= 24) {
				inputValid = true;
			} else {
				GUIMessageBox.display("Wrong Data Entry",
						"Hour Of Day should be between 0 " + "and 24, Day of Week should be between 1 and 7!");
			}
			HeatMap HeatMap = new HeatMap();
			HeatMap.GridGenerator();
			Location userLocation = new Location(X, Y);
			// Check if user input location is in Los Angeles
			if (HeatMap.blockMatcher(userLocation) == -1) {
				{
					GUIMessageBox.display("NOT IN LOS ANGELES",
							"The coordinate you input is not in Los Angeles. " + "Try Again!");
					inputValid = false;
				}
			}
			if (inputValid) {
				GUIMessageBox.display("Your Prediction", Predict());
			}

		});

		Image image1 = new Image("File:BarChartForViolationDesc.png");
		Image image2 = new Image("File:BarChartForViolationFee.png");
		Image image3 = new Image("File:PieChartForViolationByDay.png");
		Image image4 = new Image("File:PieChartForViolationByHour.png");

		ImageView imv = new ImageView(image4);

		// Add everything to grid
		Button BigDataButton = new Button("Big Data View");

		GridPane.setConstraints(BigDataButton, 1, 7);
		BigDataButton.setOnAction(e -> window.setScene(bigDataScene));

		Button backToPrediction = new Button("Back To Prediction View");
		GridPane.setConstraints(backToPrediction, 0, 0);
		backToPrediction.setOnAction(e -> window.setScene(scene));

		Button BarCharVioDesc = new Button("Ticket Type Distribution");
		GridPane.setConstraints(BarCharVioDesc, 0, 2);
		BarCharVioDesc.setOnAction(e -> imv.setImage(image1));

		Button BarCharVioFee = new Button("Violation Fine Distribution");
		GridPane.setConstraints(BarCharVioFee, 0, 3);
		BarCharVioFee.setOnAction(e -> imv.setImage(image2));

		Button PieVioDay = new Button("Daily Distribution");
		GridPane.setConstraints(PieVioDay, 0, 4);
		PieVioDay.setOnAction(e -> imv.setImage(image3));

		Button PieVioHour = new Button("Hourly Distribution");
		GridPane.setConstraints(PieVioHour, 0, 5);
		PieVioHour.setOnAction(e -> imv.setImage(image4));

		Hyperlink myHyperlink = new Hyperlink();
		myHyperlink.setText("Click for Coordinates from Map!");

		myHyperlink.setOnAction(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://epsg.io/map#srs=102645&x&y&z=14&layer=streets"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

		GridPane.setConstraints(imv, 0, 1);
        // Adding elements to the bigData scene.
		BigDataPane.getChildren().addAll(backToPrediction, imv, BarCharVioDesc, BarCharVioFee, PieVioDay, PieVioHour);
        // Adding elements to the primary scene.
		grid.getChildren().addAll(xLabel, xInput, yLabel, yInput, dayLabel, day, hourLabel, hour, predictButton,
				BigDataButton, dayLabelIntro, hourLabelIntro, myHyperlink);

		window.setScene(scene);
		window.show();
	}

	private static String Predict() {

		String predictionMsg = "No Data Available!";
		Location userLocation = new Location(X, Y);
		FileHandler fh = new FileHandler("parking-citations_cleaned.csv");
		HashMap<Integer, ParkingTickets> allTickets = fh.getParkingTicketsRaw();
		LikelyhoodPredictor lp = new LikelyhoodPredictor();

		HashMap<Integer, ParkingTickets> ticketsInLocation = userLocation.locationFilter(allTickets, 100, userLocation);

		ParkingTicketDataProcessor ptdp = new ParkingTicketDataProcessor(ticketsInLocation);

		HashMap<String, HashMap<Integer, Integer>> ticketCountsAllDayHourly = ptdp.ticketsCountsByDayTime();
		HashMap<Integer, Integer> ticketCountsDayHourly = ticketCountsAllDayHourly.get(dayConverter(DAY));

		predictionMsg = lp.predict(HOUR, ticketCountsDayHourly);

		return predictionMsg;
	}
/**
 * This method converts user input day to String day of the week 
 * @return
 */
	protected static String dayConverter(int dayNumber) {
		if (dayNumber == 1)
			return "Monday";
		else if (dayNumber == 2)
			return "Tuesday";
		else if (dayNumber == 3)
			return "Wednesday";
		else if (dayNumber == 4)
			return "Thursday";
		else if (dayNumber == 5)
			return "Friday";
		else if (dayNumber == 6)
			return "Saturday";
		else if (dayNumber == 7)
			return "Sunday";
		else
			return "";
	}

}