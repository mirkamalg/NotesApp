import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitApp {

    private static boolean answer;
    private static Stage exitAppStage;

    public static boolean confirmExit(boolean isDarkModeEnabled) throws IOException {

        exitAppStage = new Stage();
        exitAppStage.initModality(Modality.APPLICATION_MODAL);
        exitAppStage.setTitle("Exit app");
        exitAppStage.setResizable(false);

        Parent exitWindow = FXMLLoader.load(DeleteNote.class.getResource("ExitAppScreen.fxml"));
        Scene exitScene = new Scene(exitWindow, 400, 100);

        if (isDarkModeEnabled){
            exitScene.getStylesheets().add("darktheme.css");
        }

        //  Adding window icon (Exit app window)
        exitAppStage.getIcons().add(new Image(Main.class.getResourceAsStream("res/exitappicon.png")));

        exitAppStage.setScene(exitScene);
        exitAppStage.showAndWait();

        return answer;
//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle("Are you sure?");
//        window.setResizable(false);
//
//        javafx.scene.control.Label label = new Label();
//        label.setText("Do you want to exit?");
//
//        JFXButton yesButton = new JFXButton("yes");
//        JFXButton noButton = new JFXButton("no");
//
//        yesButton.setOnAction(e -> {
//            answer = true;
//            window.close();
//        });
//        noButton.setOnAction(e -> {
//            answer = false;
//            window.close();
//        });
//
//        HBox layout = new HBox(10);
//        layout.getChildren().addAll(label, yesButton, noButton);
//        layout.setAlignment(Pos.CENTER);
//
//        Scene scene = new Scene(layout);
//        window.setScene(scene);
//        window.showAndWait();
//
//        return answer;
    }

    public void yesAction(ActionEvent actionEvent) {
        answer = true;
        exitAppStage.close();
    }

    public void noAction(ActionEvent actionEvent) {
        answer = false;
        exitAppStage.close();
    }
}
