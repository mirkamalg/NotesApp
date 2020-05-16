import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class About {
    public static void initializeAboutScreen() throws IOException {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);

        Parent parent = FXMLLoader.load(About.class.getResource("AboutScreen.fxml"));
        Scene aboutScene = new Scene(parent, 300, 135);

        aboutStage.setScene(aboutScene);

        if (Main.isDarkModeEnabled) {
            aboutScene.getStylesheets().clear();
            aboutScene.getStylesheets().add("darktheme.css");
        }else{
            aboutScene.getStylesheets().clear();
            aboutScene.getStylesheets().add("/lighttheme.css");
        }

        //  Adding window icon
        aboutStage.getIcons().add(new Image(About.class.getResourceAsStream("res/abouticon.png")));
        aboutStage.show();
    }
}
