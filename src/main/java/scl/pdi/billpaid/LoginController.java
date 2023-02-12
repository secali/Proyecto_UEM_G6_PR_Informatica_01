package scl.pdi.billpaid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;



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
import scl.pdi.billpaid.holders.UserHolder;
import scl.pdi.billpaid.modelo.User;


public class LoginController implements Initializable {

    private User usuario;
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    Window window;

    Map<String, String> users = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Scanner scanner = new Scanner(new File("logins.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void login() throws Exception {
        if (this.isValidated()) {
            String enteredUsername = username.getText();
            String enteredPassword = password.getText();


            if (users.containsKey(enteredUsername) && users.get(enteredUsername).equals(enteredPassword)) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(RegisterController.class.getResource("MainPanelView.fxml"));

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle(Main.name());


                //PATRON SINGLETON
                UserHolder holder = UserHolder.getInstance();
                usuario = new User(enteredUsername, enteredPassword, "USER");

                System.out.println(usuario);
                holder.setUsuario(usuario);

                stage.show();
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Usuario y contraseña incorrecto");
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
