import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class EditHeader {

    public static AtomicReference<String> initiateEditHeaderScreen() {
        Stage window = new Stage();
        window.setTitle("Edit header");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(8);

        Label newHeaderLabel = new Label("New header");
        GridPane.setConstraints(newHeaderLabel, 0, 0);

        JFXTextField newHeaderInput = new JFXTextField();
        GridPane.setConstraints(newHeaderInput, 1, 0);

        JFXButton applyButton = new JFXButton("Apply");
        GridPane.setConstraints(applyButton, 0, 1);

        AtomicReference<String> newHeader = new AtomicReference<>();
        applyButton.setOnAction(e -> {
            if (!newHeaderInput.getText().isEmpty()){
                newHeader.set(newHeaderInput.getText());
                window.close();
            }
        });

        grid.getChildren().addAll(newHeaderLabel, newHeaderInput, applyButton);

        Scene scene = new Scene(grid, 350, 85);
        window.setScene(scene);
        window.showAndWait();
        return newHeader;
    }
}
