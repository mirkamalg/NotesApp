import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExitApp {

    static boolean answer;

    public static boolean confirmExit() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Are you sure?");
        window.setResizable(false);

        javafx.scene.control.Label label = new Label();
        label.setText("Do you want to exit?");

        JFXButton yesButton = new JFXButton("yes");
        JFXButton noButton = new JFXButton("no");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox layout = new HBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
