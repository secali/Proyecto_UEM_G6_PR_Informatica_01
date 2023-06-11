package scl.pdi.billpaid;

import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import scl.pdi.billpaid.helper.AlertHelper;

import java.io.IOException;



public class RegisterController{


    Window window;
    boolean p;
    @FXML
    private TextField username;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Button registerButton;

    @FXML
    void register() {
        window = registerButton.getScene().getWindow();

        if (this.isValidated()) {
            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            )) {
                String consulta = "INSERT INTO Usuario (username, nombre, email, passw) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(consulta)) {
                    statement.setString(1, username.getText());
                    statement.setString(2, name.getText());
                    statement.setString(3, email.getText());
                    statement.setString(4, password.getText());

                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, AlertHelper.Info, "Usuario registrado.");

                    clearForm();
                    statement.executeUpdate();

                }
            }catch (Exception e){
                AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error, "Error al hacer conexion");

            }
        }
    }






    private boolean isValidated() {

        window = registerButton.getScene().getWindow();
        if (username.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Tienes que rellenar el usuario");
            username.requestFocus();
        } else if (username.getText().length() < 0 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Tienes que rellenar la contraseña");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else if (confirmPassword.getText().equals("")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Tienes que rellenar la confirmación de la contraseña");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
                    "Confirm password text field cannot be less than 5 and greator than 25 characters.");
            confirmPassword.requestFocus();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error,
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
        name.clear();
        email.clear();
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