package scl.pdi.billpaid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scl.pdi.billpaid.modelo.Grupo;


public class Main extends Application {

    public void start(Stage stage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));





        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(name());


        stage.show();
    }
    public static String name(){
        String name = "BillPaid";
        return name;
    }
    public static void main(String[] args) {
        launch(args);

    }
}
