import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class AddNote {

    public static @NotNull
    AtomicReference<Note> initiateAddNoteScreen() {
        Stage window = new Stage();
        window.setTitle("Add note");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        AtomicReference<Note> newNote = new AtomicReference<>();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(8);

        Label headerLabel = new Label("Header");
        GridPane.setConstraints(headerLabel, 0, 0);

        JFXTextField headerInput = new JFXTextField();
        GridPane.setConstraints(headerInput, 1, 0);

        Label bodyLabel = new Label("Note");
        bodyLabel.setAlignment(Pos.TOP_LEFT);
        GridPane.setConstraints(bodyLabel, 0, 1);

        JFXTextArea bodyInput = new JFXTextArea();
        bodyInput.setPromptText("Your note goes here...");
        bodyInput.setPrefWidth(500);
        bodyInput.setPrefHeight(200);
        GridPane.setConstraints(bodyInput, 1, 1);

        JFXButton addButton = new JFXButton("Add");
        GridPane.setConstraints(addButton, 1, 2);

        addButton.setOnAction(e -> {
            String noteHeader = headerInput.getText();
            String noteBody = bodyInput.getText();
            LocalDateTime dateTime = LocalDateTime.now();

            newNote.set(new Note(noteHeader, noteBody, dateTime));
            window.close();
        });

        grid.getChildren().addAll(headerInput, headerLabel, bodyInput, bodyLabel, addButton);

        Scene scene = new Scene(grid, 600, 300);
        window.setScene(scene);
        window.showAndWait();

        return newNote;
    }
}
