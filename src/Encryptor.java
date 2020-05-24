import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor {

    @FXML
    private JFXTextArea commonTextArea;

    @FXML
    private JFXTextField keyTextField;

    @FXML
    private JFXButton encryptButton;

    @FXML
    private JFXButton decryptButton;

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void initializeCipherScreen() throws IOException {
        Stage cipherStage = new Stage();
        cipherStage.initModality(Modality.APPLICATION_MODAL);
        cipherStage.setTitle("Cipher");
        cipherStage.setResizable(false);

        Parent cipherScreen = FXMLLoader.load(Encryptor.class.getResource("CipherScreen.fxml"));
        Scene cipherScene = new Scene(cipherScreen, 600, 350);

        if (Main.isDarkModeEnabled) {
            cipherScene.getStylesheets().add("darktheme.css");
        }else{
            cipherScene.getStylesheets().clear();
            cipherScene.getStylesheets().add("/lighttheme.css");
        }

        cipherStage.getIcons().add(new Image(Encryptor.class.getResourceAsStream("res/ciphericon.png")));

        cipherStage.setScene(cipherScene);
        cipherStage.showAndWait();
    }

    public static void setKey(@NotNull String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static @Nullable String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static @Nullable String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public void encryptAction(ActionEvent actionEvent) {
        if (!commonTextArea.getText().isEmpty() && !keyTextField.getText().isEmpty()) {
            commonTextArea.setText(encrypt(commonTextArea.getText(), keyTextField.getText()));
        }
    }

    public void decryptAction(ActionEvent actionEvent) {
        if (!commonTextArea.getText().isEmpty() && !keyTextField.getText().isEmpty()) {
            commonTextArea.setText(decrypt(commonTextArea.getText(), keyTextField.getText()));
        }
    }

    public void copyAction(ActionEvent actionEvent) {
        StringSelection stringSelection = new StringSelection(commonTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
