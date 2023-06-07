package scl.pdi.billpaid;

import org.mariadb.jdbc.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scl.pdi.billpaid.holders.GrupoHolder;
import scl.pdi.billpaid.holders.ListGruposHolder;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.Sesion;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreacionController extends MainPanelController {
    private Grupo grupo;
    private final String usuario = Sesion.getUserId();
    private ArrayList<Grupo> gruposAlmacenados = new ArrayList<>();
    //contador
    private double cantidad = 0.00;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ListView<String> list_grupos;
    @FXML
    private Label lb_cantidad;
    @FXML
    private TextField tf_nombreGrupo;
    @FXML
    private TextArea tf_descripcionGrupo;
    @FXML
    private TextArea tf_cantidadIntegrantes;

    public void initialize(URL location, ResourceBundle resources) {
        GrupoHolder h = GrupoHolder.getInstance();
        grupo = h.getGrupo();
        grupo = this.grupo;
        gruposAlmacenados = new ArrayList<>();// guardamos los grupos aqui dentro

        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            );

            String consultaSQL = "SELECT * FROM Grupo WHERE id_username_creador = ?";
            PreparedStatement statement = conexion.prepareStatement(consultaSQL);
            statement.setString(1, usuario);
            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                String id_grupo = resultset.getString(1);
                String id_username_creador = resultset.getString(2);
                String nombre = resultset.getString(3);
                String descripcion = resultset.getString(4);
             //Mirar esta linea
             //   grupo = new Grupo(id_grupo,id_username_creador,nombre,descripcion);
                gruposAlmacenados.add(grupo);
                list_grupos.getItems().add(grupo.toString());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onCrearGrupoButtonClick() throws SQLException {

        Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                "admin", "Proyecto48"
        );

        String consulta = "INSERT INTO Grupo(id_username,nombre,descripcion)"
                + "VALUES(?,?,?);";
        PreparedStatement statement = conn.prepareStatement(consulta);
        statement.setString(1, Sesion.getUserId());
        statement.setString(2, grupo.getNombre());
        statement.setString(3, grupo.getDescripcion());
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Grupo creado correctamente");

        list_grupos.getItems().addAll(grupo.listarGrupo());
        gruposAlmacenados.add(grupo);
    }
    @FXML
    protected void onEliminarGrupoClick(KeyEvent accion) throws SQLException {

        if(accion.getCode() == KeyCode.DELETE) {
            int idx_eliminar = list_grupos.getSelectionModel().getSelectedIndex();
            if(idx_eliminar >=0){
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                        "admin", "Proyecto48"
                );

                String consulta = "DELETE FROM Grupo WHERE nombre = ?";
                PreparedStatement statement = connection.prepareStatement(consulta) ;
                statement.setString(1, String.valueOf(list_grupos)) ;
                ResultSet resultSet = statement.executeQuery();

                list_grupos.getItems().remove(idx_eliminar); //Elimina la lista.

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                cantidad += gruposAlmacenados.get(idx_eliminar).getCantidadIntegrantes();
                gruposAlmacenados.remove(idx_eliminar);


            }
        }
    } // Final de onEliminarGrupoClick


    @FXML
    protected void onEntrarTransaccion() throws IOException {
        Sesion.setLatestIdGroup(grupo.getNombre());
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("TransaccionView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

    }
