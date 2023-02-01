package scl.pdi.billpaid;

import scl.pdi.billpaid.helper.AlertHelper;
import scl.pdi.billpaid.Main;
import java.io.*;
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
import scl.pdi.billpaid.modelo.User;


public class RegisterController implements Initializable {


    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Button registerButton;

    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

   boolean p;


    @FXML
    void register() throws IOException {



        window = registerButton.getScene().getWindow();

        if (this.isValidated()) {

            try {
                RandomAccessFile raf = new RandomAccessFile("logins.txt", "rw");


                raf.writeBytes("Username:"+username.getText()+ "\r\n");
                raf.writeBytes("Password:"+password.getText()+ "\r\n");

                //User nuevoUser = new User(username.getText().toString(), password.getText().toString(), "user");
                //System.out.println(nuevoUser);


                clearForm();
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Usuario registrado.");



            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }



    private boolean isValidated() throws IOException {

        window = registerButton.getScene().getWindow();
        if (username.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Tienes que rellenar el usuario");
            username.requestFocus();
        } else if (username.getText().length() < 0 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Tienes que rellenar la contraseña");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else if (confirmPassword.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Tienes que rellenar la confirmación de la contraseña");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Confirm password text field cannot be less than 5 and greator than 25 characters.");
            confirmPassword.requestFocus();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Las contraseñas no coinciden");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    private boolean clearForm() {

        username.clear();
        password.clear();
        confirmPassword.clear();
        return true;
    }

    @FXML
    private void showLoginStage() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();

    }

}