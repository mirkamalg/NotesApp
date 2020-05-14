import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class EditHeader {

    @FXML
    private JFXTextField newHeaderTextField;

    private static AtomicReference<String> newHeader = new AtomicReference<>();
    private static Stage editHeaderStage;
    private static String newHeaderText;

    public static AtomicReference<String> initiateEditHeaderScreen(boolean isDarkModeEnabled) throws IOException {

        editHeaderStage = new Stage();
        editHeaderStage.initModality(Modality.APPLICATION_MODAL);
        editHeaderStage.setTitle("Edit header");
        editHeaderStage.setResizable(false);

        Parent editHeaderWindow = FXMLLoader.load(DeleteNote.class.getResource("EditHeaderScreen.fxml"));
        Scene editScene = new Scene(editHeaderWindow, 400, 125);

        if (isDarkModeEnabled){
            editScene.getStylesheets().add("darktheme.css");
        }

        editHeaderStage.setScene(editScene);
        editHeaderStage.showAndWait();

        return newHeader;


//        Stage window = new Stage();
//        window.setTitle("Edit header");
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setResizable(false);
//
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(10, 10, 10, 10));
//        grid.setVgap(12);
//        grid.setHgap(8);
//
//        Label newHeaderLabel = new Label("New header");
//        GridPane.setConstraints(newHeaderLabel, 0, 0);
//
//        JFXTextField newHeaderInput = new JFXTextField();
//        GridPane.setConstraints(newHeaderInput, 1, 0);
//
//        JFXButton applyButton = new JFXButton("Apply");
//        GridPane.setConstraints(applyButton, 0, 1);
//
//        AtomicReference<String> newHeader = new AtomicReference<>();
//        applyButton.setOnAction(e -> {
//            if (!newHeaderInput.getText().isEmpty() && !DataHandler.getNotes().containsKey(newHeaderInput.getText())){
//                newHeader.set(newHeaderInput.getText());
//                window.close();
//            }
//        });
//
//        grid.getChildren().addAll(newHeaderLabel, newHeaderInput, applyButton);
//
//        Scene scene = new Scene(grid, 350, 85);
//        window.setScene(scene);
//        window.showAndWait();
//        return newHeader;
    }

    public void applyAction(ActionEvent actionEvent) {
        newHeaderText = newHeaderTextField.getText();
        if (!newHeaderText.isEmpty() && !DataHandler.getNotes().containsKey(newHeaderText)){
                newHeader.set(newHeaderText);
                editHeaderStage.close();
        }
    }
}
