import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    public void exitApp(ActionEvent actionEvent) throws SQLException {
        boolean answer = ExitApp.confirmExit();
        if (answer){
            Stage window = (Stage) exitButton.getScene().getWindow();
            DataBase.closeConnection();
            window.close();
        }
    }

    public void getSelectedNote(MouseEvent mouseEvent) {
        enableButtons();
        noteTextArea.setEditable(true);
        String chosenNoteHeader = notesListView.getSelectionModel().getSelectedItem();
        noteTextArea.setText(DataHandler.getNotes().get(chosenNoteHeader).getBody());
        detailsTextArea.setText("Last edited: " + DataHandler.getNotes().get(chosenNoteHeader).getTime() + " By: " + System.getProperty("user.name"));
    }


    public void editHeaderAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String oldHeader = notesListView.getSelectionModel().getSelectedItem();
        String newHeader = EditHeader.initiateEditHeaderScreen().get();

        if (newHeader != null) {
            int index = notesListView.getSelectionModel().getSelectedIndex();

            Note note = DataHandler.getNotes().remove(notesListView.getSelectionModel().getSelectedItem());

            notesListView.getItems().remove(note.getHeader());
            note.setHeader(newHeader);

            note.setTime(DataHandler.formatDate(LocalDateTime.now()));

            DataHandler.insertToListView(notesListView, note, index);

            DataBase.updateNoteHeader(oldHeader, newHeader);
        }
    }

    public void deleteNoteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String deletedHeader = notesListView.getSelectionModel().getSelectedItem();
        boolean answer = DeleteNote.confirmDelete(deletedHeader);

        if (answer){
            notesListView.getItems().remove(DataHandler.getNotes().get(deletedHeader).getHeader());
            DataHandler.getNotes().remove(DataHandler.getNotes().get(deletedHeader).getHeader());

            DataBase.deleteNote(deletedHeader);

            noteTextArea.setText("Note is deleted");
            noteTextArea.setEditable(false);
            disableButtons();
        }
        disableNoteEditing();
    }

    public void saveNoteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String header = notesListView.getSelectionModel().getSelectedItem();
        String newText = noteTextArea.getText();
        DataHandler.getNotes().get(notesListView.getSelectionModel().getSelectedItem()).setBody(newText);
        DataHandler.getNotes().get(notesListView.getSelectionModel().getSelectedItem()).setTime(DataHandler.formatDate(LocalDateTime.now()));

        DataBase.updateNoteBody(header, newText);
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

    public JFXListView<String> getNotesListView() {
        return notesListView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBase dataBase = new DataBase();
        ResultSet rs;

        try {
            rs = dataBase.getNotesResultSet();
            while (rs.next()) {
                notesListView.getItems().add(rs.getString("header"));
                DataHandler.getNotes().put(rs.getString("header"), new Note(rs.getString("header"), rs.getString("body"), rs.getString("time")));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
}
