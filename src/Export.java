import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Export {

    @FXML
    private Label label;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private JFXTextField pathTextField;

    @FXML
    private JFXButton browseButton;

    @FXML
    private CheckBox compressCheckBox;

    static void initializeExportScreen() throws IOException {
        Stage exportStage = new Stage();
        exportStage.setTitle("Export notes");
        exportStage.initModality(Modality.APPLICATION_MODAL);
        exportStage.setResizable(false);

        Parent export = FXMLLoader.load(Export.class.getResource("ExportScreen.fxml"));
        Scene exportScene = new Scene(export, 755, 205);

        exportStage.setScene(exportScene);

        //Setting the theme

        if (Main.isDarkModeEnabled) {
            exportScene.getStylesheets().clear();
            exportScene.getStylesheets().add("darktheme.css");
        }else{
            exportScene.getStylesheets().clear();
            exportScene.getStylesheets().add("/lighttheme.css");
        }

        // Adding window icon
        exportStage.getIcons().add(new Image(Settings.class.getResourceAsStream("res/exporticon.png")));
        exportStage.show();
    }

    public void browseAction(ActionEvent actionEvent) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            pathTextField.setText(file.getAbsolutePath());
        }
    }

    public void exportAction(ActionEvent actionEvent) throws IOException {
        if (compressCheckBox.isSelected()) {                    //  Create files, add to zip, remove files, and move zip to the chosen folder.
            saveNotesToTxt();

            String sourceFile = pathTextField.getText();                       //  Creating zip archive
            FileOutputStream fos = new FileOutputStream("Notes.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);

            zipFile(fileToZip, fileToZip.getName(), zipOut);

            zipOut.close();
            fos.close();


            //  Moving the zip to the chosen folder.
            Path move = Files.move(Paths.get("Notes.zip"), Paths.get(pathTextField.getText() + "\\Notes.zip"));

            //  Removing the txt files
            for (String name:DataHandler.getNotes().keySet()) {
                File deletedFile = new File(pathTextField.getText() + "\\" + name + ".txt");
                deletedFile.delete();
            }

            // Tell user that save was successful
            label.setText("Export notes (successful)");
        }else {
            saveNotesToTxt();
            // Tell user that save was successful
            label.setText("Export notes (successful)");
        }
    }

    private void saveNotesToTxt() {
        DataHandler.getNotes().forEach((k, v) -> {
            try {
                FileWriter writer = new FileWriter(pathTextField.getText() + "\\" + k + ".txt");  //  Writing .txt files

                writer.write("~~~Created using Notes App by Mirkamal~~~\n\n\n");
                writer.write("Header: " + k + "\n\n");
                writer.write("Body: " + v.getBody() + "\n\n");
                writer.write("Last edited: " + v.getTime());

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void zipFile(@NotNull File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
