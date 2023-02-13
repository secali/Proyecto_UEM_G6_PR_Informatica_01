package scl.pdi.billpaid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scl.pdi.billpaid.holders.GrupoHolder;
import scl.pdi.billpaid.holders.UserHolder;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.User;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainPanelController implements Initializable {
    private User usuario;
    private Grupo grupo;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane perfil_menu;

    private List<Button> menus;

    @FXML
    private AreaChart<?, ?> chartPurchase;

    @FXML
    private AreaChart<?, ?> chartSale;

    @FXML
    private LineChart<?, ?> chartReceipt;


    @FXML
    private Label perfil_user, ok;

    @FXML
    private PasswordField field_pass, field_newpass;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void changeButtonBackground(ActionEvent e) {
        Iterator<Button> iteratorMenus = menus.iterator();

        while (iteratorMenus.hasNext()) {
            Button clickedButton = (Button) e.getSource();
            Button OtherButton = iteratorMenus.next();
            if (clickedButton == OtherButton) {
                clickedButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#2b2a26;");
            } else {
                if (OtherButton != null) {
                    OtherButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#404040;");
                }
            }
        }

    }


    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private void loadFXML(String fileName) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/view/" + fileName + ".fxml"));
            borderPane.setCenter(parent);

        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close() throws IOException {

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

    @FXML
    private void loadHomeView(ActionEvent e) throws IOException {

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("MainPanelView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

    @FXML
    private void loadPage01View(ActionEvent e) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("CrearGrupos.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

    @FXML
    private void loadPage02View(ActionEvent e) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("CrearGrupos.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

    @FXML
    private void loadDemoGroup(ActionEvent e) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        //simula la creacion de un grupo porque el CRUD de grupos no funciona, SINGLETON
        GrupoHolder holder = GrupoHolder.getInstance();
        grupo = new Grupo("Fiesta de Cumpleaños", "Tarta y cena en restaurante", 4);
        System.out.println(grupo);


        holder.setGrupo(grupo);

        Parent root = FXMLLoader.load(getClass().getResource("TransaccionView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());


        stage.show();

    }

    @FXML
    private void loadPerfil() throws IOException {
        UserHolder h = UserHolder.getInstance();
        usuario = h.getUsuario();
        System.out.println(usuario.toString());

        perfil_user.setText(usuario.getUsername());


        if (perfil_menu.isVisible())
            perfil_menu.setVisible(false);
        else {
            perfil_menu.setVisible(true);
            ok.setText("");
        }
    }

    @FXML
    private void onCambiarpass() throws IOException {
        if (field_pass.getText().equals(usuario.getPassword())) {
            usuario.setPassword(field_newpass.getText());

            ok.setText("Cambiada!");
        } else ok.setText("Error!");

        field_pass.clear();
        field_newpass.clear();
        ;
    }
}
