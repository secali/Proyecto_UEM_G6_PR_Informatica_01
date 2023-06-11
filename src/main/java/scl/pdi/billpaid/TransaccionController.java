package scl.pdi.billpaid;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scl.pdi.billpaid.holders.GrupoHolder;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.Sesion;
import scl.pdi.billpaid.modelo.Transaccion;

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
    private final String usuario = Sesion.getUserId();
    private ArrayList<Transaccion> transaccionesAlmacenadas;

    @FXML
    private Label lbCantidad, lbGrupos;
    @FXML
    private ListView<String> listTransacciones;
    @FXML
    private TextField tfNombreTransaccion;
    @FXML
    private TextField tfDescripTrans;
    @FXML
    private TextField tfCantidad;
    private double cantidad = 0.00;
    @FXML
    private DatePicker dateFechaTransaccion;
    @FXML
    private TextField tfPagadorPor;
    @FXML
    private TextField tfDeberPor;

    @FXML
    private Button btCrear, btModificar, btPremium;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbGrupos.setText(Sesion.getLatestIdGroup());
        transaccionesAlmacenadas = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    URL_BD,
                    USER_BD, PASSW_BD
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
                transaccionesAlmacenadas.add(transaccion);

                listTransacciones.getItems().add(transaccion.toString());

                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onCrearTransaccionButtonClick()  {
        if (!(tfNombreTransaccion.getText().isBlank() || tfCantidad.getText().isBlank() || tfPagadorPor.getText().isBlank() || tfDeberPor.getText().isBlank())) {
            ArrayList<String> pagadores = new ArrayList<>();
            pagadores.add(tfPagadorPor.getText());

            ArrayList<String> deudores = new ArrayList<>();
            deudores.add(tfDeberPor.getText());

            transaccion = new Transaccion(grupo.getNombre(), tfNombreTransaccion.getText(), tfDescripTrans.getText(), dateFechaTransaccion.getValue().toString() ,Double.parseDouble(tfCantidad.getText())
                    , pagadores.get(0), deudores.get(0) , usuario, "NOT_YET");

            listTransacciones.getItems().add(transaccion.toString());
            transaccionesAlmacenadas.add(transaccion);
            grupo.setTransaccion(transaccion);

            try {
                Connection connection = DriverManager.getConnection(
                        URL_BD, USER_BD, PASSW_BD
                );

                String consulta = "INSERT INTO Transacciones (nombre, descripcion, fecha, cantidad_a_pagar, id_usuario_pagador, id_usuario_deudor, id_username_creador)" +
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

                connection.close();

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            cantidad += transaccion.getImporte();
            lbCantidad.setText(cantidad + " €");

            cleanForm();
        }
    }

    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event) throws SQLException {


        if (event.getCode() == KeyCode.DELETE) {
            int idxEliminar = listTransacciones.getSelectionModel().getSelectedIndex();
            if (idxEliminar >= 0) {
                Connection connection = DriverManager.getConnection(
                        URL_BD, USER_BD, PASSW_BD
                );

                String consulta = "DELETE FROM Transacciones WHERE nombre = ?";
                PreparedStatement statement = connection.prepareStatement(consulta);
                statement.setString(1, String.valueOf(transaccionesAlmacenadas.get(idxEliminar).getNombre()));
                ResultSet resultSet = statement.executeQuery();

                listTransacciones.getItems().remove(idxEliminar);  //elimina de la lista

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                //cantidad -= grupo.getTransacciones().get(idxEliminar).getImporte();
                lbCantidad.setText(cantidad + " €");
                transaccionesAlmacenadas.remove(idxEliminar);
                grupo.removeTransaccion(idxEliminar);
            }
        }

    }

    @FXML
    protected void onEliminarTransaccionClick()  {
        int idx_eliminar = listTransacciones.getSelectionModel().getSelectedIndex();

        if (idx_eliminar >= 0) {

            try {
                Connection connection = DriverManager.getConnection(
                        URL_BD,
                        USER_BD, PASSW_BD
                );

                String consulta = "DELETE FROM Transacciones WHERE nombre = ? and descripcion=?";
                PreparedStatement statement = connection.prepareStatement(consulta);
                statement.setString(1, transaccionesAlmacenadas.get(idx_eliminar).getNombre());
                statement.setString(2, transaccionesAlmacenadas.get(idx_eliminar).getDescripcion());
                ResultSet resultSet = statement.executeQuery();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            listTransacciones.getItems().remove(idx_eliminar);  //elimina de la lista

            lbCantidad.setText(cantidad + " €");
            transaccionesAlmacenadas.remove(idx_eliminar);
        }
    }

    @FXML
    protected void onExportarTransaccionesClick() {
        try {
            FileWriter writer = new FileWriter("transacciones_exportadas.txt");

            writer.write(listTransacciones.getItems().toString());
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
        int idx_eliminar = listTransacciones.getSelectionModel().getSelectedIndex();
        if (idx_eliminar >= 0) {
            Transaccion t = transaccionesAlmacenadas.get(idx_eliminar);

            tfNombreTransaccion.setText(t.getNombre());
            tfDescripTrans.setText(t.getDescripcion());
            tfCantidad.setText(String.valueOf(t.getImporte()));

            DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(t.getFecha(), customDateTimeFormatter);

            dateFechaTransaccion.setValue(localDate);

            tfPagadorPor.setText(t.getId_pagador());
            tfDeberPor.setText(t.getId_deudor());

            btModificar.setVisible(true);
            btCrear.setVisible(false);
        }
    }

    @FXML
    protected void onGrabarModificacionClick() {
        onEliminarTransaccionClick();
        onCrearTransaccionButtonClick();

        btModificar.setVisible(false);
        btCrear.setVisible(true);
    }


    private void cleanForm() {
        tfNombreTransaccion.clear();
        tfDescripTrans.clear();
        tfCantidad.clear();
        tfPagadorPor.clear();
        tfDeberPor.clear();
        dateFechaTransaccion.getEditor().clear();
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


