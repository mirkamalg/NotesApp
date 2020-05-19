import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    static Scene mainScene;
    public static boolean isDarkModeEnabled = false;
    public static boolean isAutoSaveEnabled = false;

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Notes");
        mainScene = new Scene(root, 1100, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        //  Adding app icon (primary screen)
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("res/appicon.png")));

        loadConfigs();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void loadConfigs() throws IOException {
        Gson gson = new Gson();

        try {
            Path path = Paths.get("config.json");
            Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(path.toAbsolutePath())));
            Map<String, String> map = gson.fromJson(reader, Map.class);
            reader.close();

            String theme = map.get("theme");
            String autoSave = map.get("autosave");

            if (theme.equals("dark")){                   //  Loading theme
                mainScene.getStylesheets().clear();
                mainScene.getStylesheets().add("/darktheme.css");
                isDarkModeEnabled = true;
            }else{
                mainScene.getStylesheets().clear();
                mainScene.getStylesheets().add("/lighttheme.css");
            }

            if (autoSave.equals("enabled")){             //  Loading autosave
                isAutoSaveEnabled = true;
            }else if (autoSave.equals("disabled")){
                isAutoSaveEnabled = false;
            }


        }catch (Exception e) {
            /*
            * This is a workaround if config.json is missing
            * Theme will be set as "light" and autosave as "disabled"
            * Corresponding config file will be created
            * */
            mainScene.getStylesheets().clear();
            mainScene.getStylesheets().add("/lighttheme.css");

            Map<String, String> map = new HashMap<>();
            map.put("theme", "light");
            map.put("autosave", "disabled");

            MainController.saveConfigs(map, Files.newBufferedWriter(Paths.get("config.json")));
        }
    }
}
