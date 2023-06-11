package scl.pdi.billpaid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.util.function.Supplier;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import scl.pdi.billpaid.holders.GrupoHolder;
import scl.pdi.billpaid.holders.UserHolder;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.User;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;



public class MainPanelController implements Initializable {
    Logger logger = Logger.getLogger(getClass().getName());
    protected static final String  URL_BD = "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3";
    protected static final String USER_BD = "admin";
    protected static final String PASSW_BD = "Proyecto48";

    private User usuario;
    private Grupo grupo;

    Window window;
    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane perfilMenu;


    @FXML
    private Label perfilUser;
    @FXML
    private Label ok;
    @FXML
    private Label lbpremium;

    @FXML
    private PasswordField fieldPass;
    @FXML
    private PasswordField fieldNewpass;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Este método se utiliza para realizar inicializaciones adicionales después de cargar el archivo FXML.
        // En este caso, no se necesita ninguna inicialización adicional, por lo que el método está vacío.

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


        GrupoHolder holder = GrupoHolder.getInstance();

        logger.info((Supplier<String>) grupo);


        holder.setGrupo(grupo);

        Parent root = FXMLLoader.load(getClass().getResource("TransaccionView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());


        stage.show();

    }

    @FXML
    private void loadPerfil() {
        UserHolder h = UserHolder.getInstance();
        usuario = h.getUsuario();


        perfilUser.setText(usuario.getUsername());

        if (usuario.getUsername().equals("admin")) lbpremium.setText("PREMIUM");
        else lbpremium.setText(usuario.getRole());




        if (perfilMenu.isVisible())
            perfilMenu.setVisible(false);
        else {
            perfilMenu.setVisible(true);
            ok.setText("");
        }
    }

    @FXML
    private void onCambiarpass() {
        if (fieldPass.getText().equals(usuario.getPassword())) {
            usuario.setPassword(fieldNewpass.getText());

            ok.setText("Cambiada!");
        } else ok.setText("Error!");

        fieldPass.clear();
        fieldNewpass.clear();

    }
}
