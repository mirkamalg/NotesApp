import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    @FXML
    private JFXToggleButton darkModeToggle;

    @FXML
    private JFXButton exportButton;

    @FXML
    private JFXToggleButton autoSaveToggle;

    public static boolean themeChanged = false;

    public static boolean isAutoSaveEnabled;

    @FXML
    void darkModeToggleAction(ActionEvent event) throws IOException {

        themeChanged = !themeChanged;  //Close the app only if theme is changed, changing and reverting back won't be an issue.

        saveConfigs();
    }

    static void initializeSettingsScreen() throws IOException {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.initModality(Modality.APPLICATION_MODAL);

        settingsStage.setOnCloseRequest(e -> {
            if (themeChanged){
                System.exit(0);
            }
        });

        Parent settings = FXMLLoader.load(Settings.class.getResource("SettingsScreen.fxml"));
        Scene settingsScene = new Scene(settings, 300, 240);

        settingsStage.setScene(settingsScene);
        settingsStage.setResizable(false);

        if (Main.isDarkModeEnabled) {
            settingsScene.getStylesheets().add("darktheme.css");
        }else{
            settingsScene.getStylesheets().clear();
            settingsScene.getStylesheets().add("/lighttheme.css");
        }

        //  Adding window icon (App settings window)
        settingsStage.getIcons().add(new Image(Settings.class.getResourceAsStream("res/settingsicon.png")));
        settingsStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.isDarkModeEnabled) {
            darkModeToggle.setSelected(true);
        }

        if (Main.isAutoSaveEnabled) {
            autoSaveToggle.setSelected(true);
        }
    }

    public void exportAction(ActionEvent actionEvent) throws IOException {
        Export.initializeExportScreen();
    }

    public void autoSaveToggleAction(ActionEvent actionEvent) throws IOException {
        isAutoSaveEnabled = !isAutoSaveEnabled;
        Main.isAutoSaveEnabled = !Main.isAutoSaveEnabled;
        saveConfigs();
    }

    private void saveConfigs() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("config.json"));
        Map<String, String> map = new HashMap<>();

        if (darkModeToggle.isSelected()) {         //  Saving theme configs
            map.put("theme", "dark");
            MainController.isThemeSwitchOn = true;
        }else{
            map.put("theme", "light");
            MainController.isThemeSwitchOn = false;
        }

        if (autoSaveToggle.isSelected()){
            map.put("autosave", "enabled");
        }else {
            map.put("autosave", "disabled");
        }

        MainController.saveConfigs(map, writer);
    }
}
