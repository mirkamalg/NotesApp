import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    void darkModeToggleAction(ActionEvent event) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("config.json"));
        Map<String, String> map = new HashMap<>();

        MainController.themeChanged = !MainController.themeChanged;  //Close the app only if theme is change, changing and reverting back won't be an issue.

        if (darkModeToggle.isSelected()) {
            map.put("theme", "dark");
            MainController.isThemeSwitchOn = true;
        }else{
            map.put("theme", "light");
            MainController.isThemeSwitchOn = false;
        }

        MainController.saveConfigs(map, writer);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.isDarkModeEnabled) {
            darkModeToggle.setSelected(true);
        }
    }
}
