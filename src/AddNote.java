import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class AddNote {

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXTextField headerInput;

    @FXML
    private JFXTextArea bodyInput;
    
    private static Stage addNoteStage;
    private static AtomicReference<Note> newNote = new AtomicReference<>();

    public static @NotNull
    AtomicReference<Note> initiateAddNoteScreen(boolean isDarkModeEnabled) throws IOException {

        addNoteStage = new Stage();
        addNoteStage.initModality(Modality.APPLICATION_MODAL);
        addNoteStage.setTitle("Add note");
        addNoteStage.setResizable(false);

        Parent addNoteWindow = FXMLLoader.load(AddNote.class.getResource("AddNoteScreen.fxml"));
        Scene addScene = new Scene(addNoteWindow, 750, 350);

        if (isDarkModeEnabled) {
            addScene.getStylesheets().add("darktheme.css");
        }

        addNoteStage.setScene(addScene);
        addNoteStage.showAndWait();

        return newNote;
    }

    public void addAction(ActionEvent actionEvent) {

        String noteHeader = headerInput.getText();
        String noteBody = bodyInput.getText();
        String noteTimeFormatted = DataHandler.formatDate(LocalDateTime.now());

        if (!headerInput.getText().isEmpty() && !DataHandler.getNotes().containsKey(headerInput.getText())){
            newNote.set(new Note(noteHeader, noteBody, DataHandler.formatDate(LocalDateTime.now())));

            try {
                DataBase.addNote(noteHeader, noteBody, noteTimeFormatted);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            addNoteStage.close();
        }

    }
}
