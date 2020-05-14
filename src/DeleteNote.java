import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteNote {

    private static boolean answer;
    private static Stage deleteNoteStage;

    public static boolean confirmDelete(boolean isDarkModeEnabled) throws IOException {

        deleteNoteStage = new Stage();
        deleteNoteStage.initModality(Modality.APPLICATION_MODAL);
        deleteNoteStage.setTitle("Delete note");
        deleteNoteStage.setResizable(false);

        Parent deleteWindow = FXMLLoader.load(DeleteNote.class.getResource("DeleteNoteConfirmScreen.fxml"));
        Scene deleteScene = new Scene(deleteWindow, 400, 100);

        if (isDarkModeEnabled){
            deleteScene.getStylesheets().add("darktheme.css");
        }

        //  Adding window icon (Delete note window)
        deleteNoteStage.getIcons().add(new Image(Main.class.getResourceAsStream("res/deletenoteicon.png")));

        deleteNoteStage.setScene(deleteScene);
        deleteNoteStage.showAndWait();

        return answer;
    }

    public void yesAction(ActionEvent actionEvent) {
        answer = true;
        deleteNoteStage.close();
    }

    public void noAction(ActionEvent actionEvent) {
        answer = false;
        deleteNoteStage.close();
    }

}
