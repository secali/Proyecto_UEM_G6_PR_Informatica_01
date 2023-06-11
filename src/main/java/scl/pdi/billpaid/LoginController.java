package scl.pdi.billpaid;

import java.util.logging.Logger;
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
    public void login() {
        String usr = this.username.getText();
        String pssw = this.password.getText();
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                "admin", "Proyecto48"
        )) {
            String consulta = "SELECT * FROM Usuario WHERE username = ? AND passw = ?";
            try (PreparedStatement statement = connection.prepareStatement(consulta)) {
                statement.setString(1, usr);
                statement.setString(2, pssw);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        logger.info("Login correcto");
                        Sesion.setUserId(usr);

                        Parent root = FXMLLoader.load(RegisterController.class.getResource("MainPanelView.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.setTitle(Main.name());

                        stage.show();
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error, "Inicio de sesión fallido");
                    }
                }
            }
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, AlertHelper.Error, "Inicio de sesión fallido");
        }
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
