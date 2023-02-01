package scl.pdi.billpaid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;


import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.stage.Window;


import scl.pdi.billpaid.helper.AlertHelper;


public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void login() throws Exception {

        if (this.isValidated()) {
            try {
                RandomAccessFile raf = new RandomAccessFile("logins.txt", "rw");
                String line = raf.readLine();
                String Username=line.substring(9);
                String Password=raf.readLine().substring(9);



                if(username.getText().equals(Username)& password.getText().equals(Password)){

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    Parent root = FXMLLoader.load(RegisterController.class.getResource("MainPanelView.fxml"));

                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setTitle(Main.name());

                    stage.show();
                }else{
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Usuario y contraseña incorrecto");

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private boolean isValidated() {

        window = loginButton.getScene().getWindow();
        if (username.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Tienes que rellenar el usuario ");
            username.requestFocus();
        } else if (username.getText().length() < 0 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "El usuario tiene un maximo de 25 caracteres");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Tienes que rellenar la contraseña");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "La contraseña tiene que ser mayor o menos que 25");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }
}
