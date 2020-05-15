import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        }else {
            editScene.getStylesheets().clear();
            editScene.getStylesheets().add("/lighttheme.css");
        }

        //  Adding window icon (Edit header icon)
        editHeaderStage.getIcons().add(new Image(Main.class.getResourceAsStream("res/editnoteheadericon.png")));

        editHeaderStage.setScene(editScene);
        editHeaderStage.showAndWait();

        return newHeader;
    }

    public void applyAction(ActionEvent actionEvent) {
        newHeaderText = newHeaderTextField.getText();
        if (!newHeaderText.isEmpty() && !DataHandler.getNotes().containsKey(newHeaderText)){
                newHeader.set(newHeaderText);
                editHeaderStage.close();
        }
    }
}
