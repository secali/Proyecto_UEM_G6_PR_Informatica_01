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

        /*ListGruposHolder h2 = ListGruposHolder.getInstance();
        gruposAlmacenados = new ArrayList<>();
        gruposAlmacenados = h2.getLista_grupos();
        list_grupos.getItems().addAll(String.valueOf(gruposAlmacenados));*/

    }
    @FXML
    protected void onCrearGrupoButtonClick() throws SQLException {

        grupo = new Grupo(tf_nombreGrupo.getText(), tf_descripcionGrupo.getText());

        //Conexion con la Base
        Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                "admin", "Proyecto48"
        );
        //Consulta SQL
        String consulta = "INSERT INTO Grupo(nombre,descripcion,participantes)"
                + "VALUES(?,?,?);";
        PreparedStatement statement = conn.prepareStatement(consulta);
        statement.setString(1, grupo.getNombre());
        statement.setString(2, grupo.getID_Persona()); //Necesito cambiar el atributo en GRUPO
        statement.setString(3, String.valueOf(grupo.getCantidadIntegrantes()));

        int filasInsertadas = statement.executeUpdate();
        // FilasInsertadas almacenara el numero de filas afectadas por la operacion de insercion

        if(filasInsertadas>0){
            System.out.println("Se ha creado un grupo");
        }

        //Pintar la lista
        list_grupos.getItems().addAll(grupo.listarGrupo());
        gruposAlmacenados.add(grupo);

        //simula la creacion de un grupo porque el CRUD de grupos no funciona, SINGLETON
        GrupoHolder holder = GrupoHolder.getInstance();
        holder.setGrupo(grupo);

        System.out.println("Grupo creado correctamente");
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
                cantidad -= gruposAlmacenados.get(idx_eliminar).getCantidadIntegrantes();
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
