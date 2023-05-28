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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String consulta = "INSERT INTO Grupo(nombre, cantidad, descripcion_grupo, integrantes)"
                + "VALUES(?,?,?,?);";
        PreparedStatement statement = conn.prepareStatement(consulta);
        statement.setString(1, grupo.getNombre());
        statement.setString(2, grupo.getID_Persona());
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
    protected void onEliminarGrupoClick() throws SQLException {

        String url = "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com";
        String user = "admin";
        String password = "Proyecto48";
        Connection conn = DriverManager.getConnection(url, user, password);


        //DEVUELVE EL ELEMENTO A ELIMINAR, QUE ESTÁ SELECCIONADO CON EL RATON
        int idx_eliminar = list_grupos.getSelectionModel().getSelectedIndex();

        System.out.println(idx_eliminar);


        if (idx_eliminar >= 0) { //AQUI SE LLAMA AL JDBC Y SE METE LA QUERY DE BORRAR GRUPO CON ID_GRUPO = IDX_ELIMINAR
            list_grupos.getItems().remove(idx_eliminar);  //elimina de la lista



            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            cantidad -= gruposAlmacenados.get(idx_eliminar).getCantidadIntegrantes();
            gruposAlmacenados.remove(idx_eliminar);
        }
    }

    @FXML
    protected void onEntrarTransaccion() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("TransaccionView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Main.name());

        stage.show();
    }

     /*
    @FXML
    protected void onEliminarGrupoSUPR(KeyEvent event) {


        if (event.getCode() == KeyCode.DELETE) {
            int idx_eliminar = list_grupos.getSelectionModel().getSelectedIndex();
            if (idx_eliminar >= 0) {
                list_grupos.getItems().remove(idx_eliminar);  //elimina de la lista

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                cantidad -= gruposAlmacenados.get(idx_eliminar).getCantidadIntegrantes();
                lb_cantidad.setText(Double.toString(cantidad) + " €");
                gruposAlmacenados.remove(idx_eliminar);
            }
        }

    }
    */

} // final del controlador de "Creacion"
