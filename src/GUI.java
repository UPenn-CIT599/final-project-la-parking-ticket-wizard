
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GUI extends Application {
	private double X, Y;
	private int DAY, HOUR;
	Stage window;
	String message;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Parking Wizard Los Angeles");

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		GridPane BigDataPane = new GridPane();
		BigDataPane.setPadding(new Insets(10, 10, 10, 10));
		BigDataPane.setVgap(8);
		BigDataPane.setHgap(10);

		Scene scene = new Scene(grid, 350, 300);
		Scene bigDataScene = new Scene(BigDataPane, 820, 1000);

		Label xLabel = new Label("X Coordinate Value:");
		GridPane.setConstraints(xLabel, 0, 0);

		TextField xInput = new TextField();
		xInput.setPromptText("123456.78");
		GridPane.setConstraints(xInput, 1, 0);

		Label yLabel = new Label("Y Coordinate Value:");
		GridPane.setConstraints(yLabel, 0, 1);

		TextField yInput = new TextField();
		yInput.setPromptText("123456.78");
		GridPane.setConstraints(yInput, 1, 1);

		Label dayLabel = new Label("Day of Week:");
		GridPane.setConstraints(dayLabel, 0, 2);

		TextField day = new TextField();
		day.setPromptText("5");
		GridPane.setConstraints(day, 1, 2);

		Label hourLabel = new Label("Hour of Day:");
		GridPane.setConstraints(hourLabel, 0, 3);

		TextField hour = new TextField();
		hour.setPromptText("14");
		GridPane.setConstraints(hour, 1, 3);

		Button predictButton = new Button("Predict Tickets");
		GridPane.setConstraints(predictButton, 1, 5);
		predictButton.setOnAction(e -> {
			boolean inputValid = false;
			try {
				X = Double.parseDouble(xInput.getText());
				Y = Double.parseDouble(yInput.getText());
				DAY = Integer.parseInt(day.getText());
				HOUR = Integer.parseInt(hour.getText());
	//			System.out.println("X Value: " + X + "   Y Value: " + Y); // TEST
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
			HeatMap HeatMap=new HeatMap();
			HeatMap.GridGenerator();
			Location userLocation = new Location(X, Y);
	//		System.out.println(X+"  "+Y+"ZONE"+HeatMap.blockMatcher(userLocation));//TEST
			if (HeatMap.blockMatcher(userLocation)==-1) {
				{GUIMessageBox.display("NOT IN LOS ANGELES","The coordinate you input is not in Los Angeles. "
						+ "Try Again!");
				inputValid=false;
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

		GridPane.setConstraints(imv, 0, 1);

		BigDataPane.getChildren().addAll(backToPrediction, imv, BarCharVioDesc, BarCharVioFee, PieVioDay, PieVioHour);

		grid.getChildren().addAll(xLabel, xInput, yLabel, yInput, dayLabel, day, hourLabel, hour, predictButton,
				BigDataButton);

		window.setScene(scene);
		window.show();
	}

	private static String Predict() {
		// TO-DO
		String predictionMsg = "No Data Available!";

		return predictionMsg;
	}

}