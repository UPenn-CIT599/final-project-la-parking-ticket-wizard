import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


/**
 * This class is for GUI class to communicate with user by prompting 
 * messages. User will need to click "close window" button to continue
 * the program.
 * @author Weiwen Zhao
 *
 */
public class GUIMessageBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}