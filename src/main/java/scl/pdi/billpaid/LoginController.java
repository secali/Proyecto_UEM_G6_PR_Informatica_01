package scl.pdi.billpaid;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;


public class LoginController {

    Window window;

    private User usuario;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;


    @FXML
    public void login() throws Exception {


        if (this.isValidated()) {
            String Username = username.getText();
            String Password = password.getText();

            Connection connection = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            );

            String consulta = "SELECT * FROM Usuario WHERE username = ? AND passw = ?";
            PreparedStatement statement = connection.prepareStatement(consulta);

            statement.setString(1, Username);
            statement.setString(2, Password);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {

                    System.out.println("Login correcto");
                Parent root = FXMLLoader.load(RegisterController.class.getResource("MainPanelView.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle(Main.name());

                stage.show();
                }
                else System.err.println("Login incorrecto");
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
    public void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }
}
