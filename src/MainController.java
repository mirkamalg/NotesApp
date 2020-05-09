import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class MainController {

    @FXML
    private JFXButton newNoteButton;

    @FXML
    private JFXButton editHeaderButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton exitButton;

    @FXML
    private Label todoLabel;

    @FXML
    private Color x2;

    @FXML
    private Font x1;

    @FXML
    private JFXListView<String> notesListView = new JFXListView<>();

    @FXML
    private Label headerLabel;

    @FXML
    private JFXTextArea noteTextArea;

    @FXML
    private Label detailsLabel;

    @FXML
    private JFXTextArea detailsTextArea;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    public void newNoteAction(ActionEvent actionEvent) {
        Note newNote = AddNote.initiateAddNoteScreen().get();
        DataHandler.addToListView(notesListView, newNote);
    }

    public void exitApp(ActionEvent actionEvent) {
        boolean answer = ExitApp.confirmExit();
        if (answer){
            Stage window = (Stage) exitButton.getScene().getWindow();
            window.close();
        }
    }

    public void getSelectedNote(MouseEvent mouseEvent) {
        enableButtons();
        noteTextArea.setEditable(true);
        String chosenNoteHeader = notesListView.getSelectionModel().getSelectedItem();
        noteTextArea.setText(DataHandler.getNotes().get(chosenNoteHeader).getBody());
        detailsTextArea.setText("Last edited: " + DataHandler.formatDate(DataHandler.getNotes().get(chosenNoteHeader).getTime()) + " By: " + System.getProperty("user.name"));
    }


    public void editHeaderAction(ActionEvent actionEvent) {
        if (notesListView.getItems().size() == 0) {
            editHeaderButton.setDisable(true);
            return;
        }
        String newHeader = EditHeader.initiateEditHeaderScreen().get();
        int index = notesListView.getSelectionModel().getSelectedIndex();
        Note note = DataHandler.getNotes().remove(notesListView.getSelectionModel().getSelectedItem());
        notesListView.getItems().remove(note.getHeader());
        note.setHeader(newHeader);
        note.setTime(LocalDateTime.now());
        DataHandler.insertToListView(notesListView, note, index);
    }

    public void deleteNoteAction(ActionEvent actionEvent) {
        if (notesListView.getItems().size() == 0) {
            deleteButton.setDisable(true);
            return;
        }
        String deletedHeader = notesListView.getSelectionModel().getSelectedItem();
        boolean answer = DeleteNote.confirmDelete(deletedHeader);

        if (answer){
            notesListView.getItems().remove(DataHandler.getNotes().get(deletedHeader).getHeader());
            DataHandler.getNotes().remove(DataHandler.getNotes().get(deletedHeader).getHeader());
            noteTextArea.setText("Note is deleted");
            noteTextArea.setEditable(false);
            disableButtons();
        }
        disableNoteEditing();
    }

    public void saveNoteAction(ActionEvent actionEvent) {
        String text = noteTextArea.getText();
        DataHandler.getNotes().get(notesListView.getSelectionModel().getSelectedItem()).setBody(text);
        DataHandler.getNotes().get(notesListView.getSelectionModel().getSelectedItem()).setTime(LocalDateTime.now());
    }

    private void enableButtons() {
        if (notesListView.getSelectionModel().getSelectedItem() != null) {
            editHeaderButton.setDisable(false);
            deleteButton.setDisable(false);
            saveButton.setDisable(false);
        }
    }

    private void disableButtons() {
        editHeaderButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(true);
    }

    private void disableNoteEditing() {
        if (notesListView.getSelectionModel().getSelectedItem() != null) {
            noteTextArea.setEditable(false);
        }
    }
}
