package scl.pdi.billpaid;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import scl.pdi.billpaid.helper.AlertHelper;
import scl.pdi.billpaid.holders.GrupoHolder;
import scl.pdi.billpaid.holders.UserHolder;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.Sesion;
import scl.pdi.billpaid.modelo.Transaccion;
import scl.pdi.billpaid.modelo.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransaccionController extends MainPanelController {

    private Grupo grupo;
    private Transaccion transaccion;
    Window window;
    private final String usuario = Sesion.getUserId();
    private ArrayList<Transaccion> transacciones_almacenadas;

    @FXML
    private Label lb_cantidad, lb_grupos;
    @FXML
    private ListView<String> list_transacciones;
    @FXML
    private TextField tf_nombre_transaccion;
    @FXML
    private TextField tf_descrip_trans;
    @FXML
    private TextField tf_cantidad;
    private double cantidad = 0.00;
    @FXML
    private DatePicker date_fecha_transaccion;
    @FXML
    private TextField tf_pagador_por;
    @FXML
    private TextField tf_deber_por;

    @FXML
    private Button home, bt_crear, bt_modificar, bt_premium;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GrupoHolder h = GrupoHolder.getInstance();
        grupo = h.getGrupo();

        lb_grupos.setText(grupo.getNombre());

        transacciones_almacenadas = new ArrayList<>();


        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            );

            String consulta = "SELECT * FROM Transacciones WHERE id_username_creador = ?";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, usuario);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                String descripcion = resultSet.getString(3);
                String fecha = resultSet.getString(4);
                String cantidad = resultSet.getString(5);
                String id_username_pagador =  resultSet.getString(6);
                String id_username_deudor =  resultSet.getString(7);
                String username_creador = resultSet.getString(8);
                transaccion =  new Transaccion("ID_DEL_GRUPO",nombre,descripcion,fecha,Double.parseDouble(cantidad),id_username_pagador,id_username_deudor,username_creador, id);
                transacciones_almacenadas.add(transaccion);

                list_transacciones.getItems().add(transaccion.toString());


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        for (int i = 0; i < grupo.getTransacciones().size(); i++) {
            list_transacciones.getItems().add(grupo.getTransacciones().get(i).toString());
            transacciones_almacenadas.add(grupo.getTransacciones().get(i));

            cantidad += grupo.getTransacciones().get(i).getImporte();
            lb_cantidad.setText(cantidad + " €");
        }
    }

    @FXML
    protected void onCrearTransaccionButtonClick() throws SQLException {
        if (!(tf_nombre_transaccion.getText().isBlank() || tf_cantidad.getText().isBlank() || tf_pagador_por.getText().isBlank() || tf_deber_por.getText().isBlank())) {
            ArrayList<String> pagadores = new ArrayList<>();
            pagadores.add(tf_pagador_por.getText());

            ArrayList<String> deudores = new ArrayList<>();
            deudores.add(tf_deber_por.getText());


            transaccion = new Transaccion(grupo.getNombre(), tf_nombre_transaccion.getText(), tf_descrip_trans.getText(),date_fecha_transaccion.getValue().toString() ,Double.parseDouble(tf_cantidad.getText())
                    , pagadores.get(0), deudores.get(0) , usuario, "NOT_YET");


            list_transacciones.getItems().add(transaccion.toString());
            transacciones_almacenadas.add(transaccion);
            grupo.setTransaccion(transaccion);


            Connection connection = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            );

            String consulta = "INSERT INTO Transacciones (nombre, descripcion, fecha, cantidad_a_pagar, id_usuario_pagador, id_usuario_deudor, id_username_creador)"+
                    "VALUES (?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, transaccion.getNombre());
            statement.setString(2, transaccion.getDescripcion());
            statement.setString(3, transaccion.getFecha());
            statement.setString(4, String.valueOf(transaccion.getImporte()));
            statement.setString(5, transaccion.getId_pagador());
            statement.setString(6, transaccion.getId_deudor());
            statement.setString(7, usuario);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Se ha añadido 1 transaccion");

            //suma la cantidad de cada transaccion introducida y actualiza el indicador
            cantidad += transaccion.getImporte();
            lb_cantidad.setText(cantidad + " €");

            cleanForm();
        }
    }

    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event) throws SQLException {


        if (event.getCode() == KeyCode.DELETE) {
            int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
            if (idx_eliminar >= 0) {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                        "admin", "Proyecto48"
                );

                String consulta = "DELETE FROM Transacciones WHERE nombre = ?";
                PreparedStatement statement = connection.prepareStatement(consulta);
                statement.setString(1, String.valueOf(transacciones_almacenadas.get(idx_eliminar).getNombre()));
                ResultSet resultSet = statement.executeQuery();

                list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                //cantidad -= grupo.getTransacciones().get(idx_eliminar).getImporte();
                lb_cantidad.setText(cantidad + " €");
                transacciones_almacenadas.remove(idx_eliminar);
                grupo.removeTransaccion(idx_eliminar);
            }
        }

    }

    @FXML
    protected void onEliminarTransaccionClick() throws SQLException {
        int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
        System.out.println(list_transacciones.getItems().toString());
        System.out.println(idx_eliminar);


        if (idx_eliminar >= 0) {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mariadb://proyecto2.cxksbyurm5sm.eu-north-1.rds.amazonaws.com/proyecto3",
                    "admin", "Proyecto48"
            );

            String consulta = "DELETE FROM Transacciones WHERE nombre = ? and descripcion=?";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, transacciones_almacenadas.get(idx_eliminar).getNombre());
            statement.setString(2, transacciones_almacenadas.get(idx_eliminar).getDescripcion());
            ResultSet resultSet = statement.executeQuery();


            list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            //cantidad -= grupo.getTransacciones().get(idx_eliminar).getImporte();
            lb_cantidad.setText(cantidad + " €");
            transacciones_almacenadas.remove(idx_eliminar);
        }
    }

    @FXML
    protected void onExportarTransaccionesClick() {
        try {
            FileWriter writer = new FileWriter("transacciones_exportadas.txt");

            writer.write(list_transacciones.getItems().toString());
            writer.write(System.lineSeparator());
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    protected void onModificarTransaccionClick() {
        int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
        if (idx_eliminar >= 0) {
            Transaccion t = transacciones_almacenadas.get(idx_eliminar);

            tf_nombre_transaccion.setText(t.getNombre());
            tf_descrip_trans.setText(t.getDescripcion());
            tf_cantidad.setText(String.valueOf(t.getImporte()));

            DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(t.getFecha(), customDateTimeFormatter);

            date_fecha_transaccion.setValue(localDate);

            tf_pagador_por.setText(t.getId_pagador());
            tf_deber_por.setText(t.getId_deudor());

            bt_modificar.setVisible(true);
            bt_crear.setVisible(false);
        }
    }

    @FXML
    protected void onGrabarModificacionClick() throws SQLException {
        onEliminarTransaccionClick();
        onCrearTransaccionButtonClick();

        bt_modificar.setVisible(false);
        bt_crear.setVisible(true);
    }


    private void cleanForm() {
        tf_nombre_transaccion.clear();
        tf_descrip_trans.clear();
        tf_cantidad.clear();
        tf_pagador_por.clear();
        tf_deber_por.clear();
        date_fecha_transaccion.getEditor().clear();
    }


    @FXML
    protected void onPremium(){
        /*if (!usuario.getRole().equals("PREMIUM")){
            window = bt_premium.getScene().getWindow();

            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Usted no disfruta de cuenta PREMIUM!");

        }else {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "ATENCIÓN",
                    "Esta funcionalidad estará disponible muy pronto!");
        }*/
    }
}


