/**
 * This class is created as a placeholder for later GUI implementation,
 * it will collaborate with ParkingTicketRunner Class.
 * We are currently looking at JavaFX and Google Web Toolkit and deciding
 * which framework we are going use. Additional classes and methods will be
 * added after we make a decision on UI framework.
 * @author Weiwen Zhao
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("LA Parking Wizard");
        button = new Button("Check Tickets");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 1280, 720);

        window.setScene(scene);
        window.show();
    }


}