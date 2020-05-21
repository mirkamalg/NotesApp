import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EditHeader implements Initializable {

    @FXML
    private JFXTextField newHeaderTextField;

    @FXML
    private StackPane pane;

    private static AtomicReference<String> newHeader = new AtomicReference<>();
    private static Stage editHeaderStage;
    private static String newHeaderText;

    private static boolean isDarkModeEnabled = false;

    public static AtomicReference<String> initiateEditHeaderScreen() throws IOException {
        EditHeader.isDarkModeEnabled = Main.isDarkModeEnabled;

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
        } else {
            JFXDialogLayout layout = new JFXDialogLayout();     //  Hardcoding the dialog part

            Label header = new Label("Can't change note header.");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  Setting old header as default text
        newHeaderTextField.setText(MainController.currentHeader);
    }
}
