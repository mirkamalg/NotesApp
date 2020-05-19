import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveDialog {

    private static boolean answer;
    private static Stage confirmSaveStage;

    public static boolean confirmSave() throws IOException {
        confirmSaveStage = new Stage();
        confirmSaveStage.initModality(Modality.APPLICATION_MODAL);
        confirmSaveStage.setTitle("Save note");
        confirmSaveStage.setResizable(false);

        Parent confirmSaveWindow = FXMLLoader.load(SaveDialog.class.getResource("SaveDialogScreen.fxml"));
        Scene dialogScene = new Scene(confirmSaveWindow, 400, 100);

        if (Main.isDarkModeEnabled){
            dialogScene.getStylesheets().add("darktheme.css");
        }else {
            dialogScene.getStylesheets().clear();
            dialogScene.getStylesheets().add("lighttheme.css");
        }

        //Adding icon
        confirmSaveStage.getIcons().add(new Image(SaveDialog.class.getResourceAsStream("res/savenoteicon.png")));

        confirmSaveStage.setScene(dialogScene);
        confirmSaveStage.showAndWait();

        return answer;
    }

    public void yesAction(ActionEvent actionEvent) {
        answer = true;
        confirmSaveStage.close();
    }

    public void noAction(ActionEvent actionEvent) {
        answer = false;
        confirmSaveStage.close();
    }
}
