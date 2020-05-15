import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class AddNote {

    @FXML
    private StackPane pane;


    @FXML
    private JFXButton addButton;

    @FXML
    private JFXTextField headerInput;

    @FXML
    private JFXTextArea bodyInput;
    
    private static Stage addNoteStage;
    private static AtomicReference<Note> newNote = new AtomicReference<>();
    private static boolean isDarkModeEnabled = false;

    public static @NotNull
    AtomicReference<Note> initiateAddNoteScreen(boolean isDarkModeEnabled) throws IOException {
        AddNote.isDarkModeEnabled = isDarkModeEnabled;

        addNoteStage = new Stage();
        addNoteStage.initModality(Modality.APPLICATION_MODAL);
        addNoteStage.setTitle("Add note");
        addNoteStage.setResizable(false);

        Parent addNoteWindow = FXMLLoader.load(AddNote.class.getResource("AddNoteScreen.fxml"));
        Scene addScene = new Scene(addNoteWindow, 750, 350);

        if (isDarkModeEnabled) {
            addScene.getStylesheets().add("darktheme.css");
        }else{
            addScene.getStylesheets().clear();
            addScene.getStylesheets().add("/lighttheme.css");
        }

        //  Adding window icon (Add note window icon)
        addNoteStage.getIcons().add(new Image(Main.class.getResourceAsStream("res/addnoteicon.png")));

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
        } else {
            JFXDialogLayout layout = new JFXDialogLayout();     //  Hardcoding the dialog part

            Label header = new Label("Can't create your note.");
            Label body = new Label("Note header must be unique and not empty.");

            layout.setHeading(header);
            layout.setBody(body);

            JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);

            JFXButton button = new JFXButton("Okay");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });

            if (isDarkModeEnabled){
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setStyle("-fx-background-color: #212b43;-fx-text-fill: aliceblue;");
                layout.setStyle("-fx-background-color: #303a52;");
                header.setStyle("-fx-text-fill: aliceblue;");
                body.setStyle("-fx-text-fill: white;");
            }else {
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setStyle("-fx-background-color: #c0a9a1;-fx-text-fill: black;");
                body.setStyle("-fx-text-fill: black;");
            }

            layout.setActions(button);

            dialog.show();
        }
    }
}
