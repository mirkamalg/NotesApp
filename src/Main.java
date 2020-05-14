import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main extends Application {

    static Scene mainScene;
    public static boolean isDarkModeEnabled = false;

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Notes");
        mainScene = new Scene(root, 1100, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true);

        loadConfigs();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void loadConfigs() {
        Gson gson = new Gson();

        try {
            Path path = Paths.get("config.json");
            Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(path.toAbsolutePath())));
            Map<String, String> map = gson.fromJson(reader, Map.class);
            reader.close();

            String darkMode = map.get("theme");

            if (darkMode.equals("dark")){
                mainScene.getStylesheets().clear();
                mainScene.getStylesheets().add("/darktheme.css");
                isDarkModeEnabled = true;
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
