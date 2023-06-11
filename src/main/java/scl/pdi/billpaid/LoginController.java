package scl.pdi.billpaid;

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

import scl.pdi.billpaid.modelo.Sesion;
import scl.pdi.billpaid.modelo.User;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class LoginController {

    Window window;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;


    @FXML
    public void login() throws Exception {

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
                Sesion.setUserId(Username);

                Parent root = FXMLLoader.load(RegisterController.class.getResource("MainPanelView.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle(Main.name());

                stage.show();
                }
                else AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Inicio de sesion fallido");

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
