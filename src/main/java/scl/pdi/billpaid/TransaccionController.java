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
import scl.pdi.billpaid.modelo.Transaccion;
import scl.pdi.billpaid.modelo.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransaccionController extends MainPanelController {

    private Grupo grupo;
    private Transaccion transaccion;
    Window window;
    private User usuario;
    private ArrayList<Transaccion> transacciones_almacenadas;
    private ArrayList<User> deudores = new ArrayList<>();
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
        System.out.println(grupo.toString());

        lb_grupos.setText(grupo.getNombre());

        transacciones_almacenadas = new ArrayList<>();

        grupo.cargarDemoTransacciones();

        for (int i = 0; i < grupo.getTransacciones().size(); i++) {
            list_transacciones.getItems().add(grupo.getTransacciones().get(i).transaccion2List());
            transacciones_almacenadas.add(grupo.getTransacciones().get(i));

            cantidad += grupo.getTransacciones().get(i).getCantidad();
            lb_cantidad.setText(Double.toString(cantidad) + " €");
        }

        UserHolder hol = UserHolder.getInstance();
        usuario = hol.getUsuario();
        System.out.println(usuario.toString());

        usuario.setRole("PREMIUM");

    }

    @FXML
    protected void onCrearTransaccionButtonClick() {
        if (!(tf_nombre_transaccion.getText().isBlank() || tf_cantidad.getText().isBlank() || tf_pagador_por.getText().isBlank() || tf_deber_por.getText().isBlank())) {
            ArrayList<String> pagadores = new ArrayList<>();
            pagadores.add(tf_pagador_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST

            ArrayList<String> deudores = new ArrayList<>();
            deudores.add(tf_deber_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST

            //deudoSres.add(tf_deber_por.getText());
            //falta ajustar esto del string.....
            //String[] deudores_str = split(tf_deber_por.getText());

            //for (String subString : deudores_str) {

            //  deudores.add(new User(subString, "", "usuario"));
            //}

            //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST
            transaccion = new Transaccion(grupo.getNombre(), tf_nombre_transaccion.getText(), tf_descrip_trans.getText(), Double.parseDouble(tf_cantidad.getText()),
                    "normal", pagadores, deudores, date_fecha_transaccion.getValue().toString());


            //Usa el método de Transaccion.java para pasar a String los parametros relevantes y devolverlos en una cadena que pinta el listview
            list_transacciones.getItems().add(transaccion.transaccion2List());
            transacciones_almacenadas.add(transaccion);
            grupo.setTransaccion(transaccion);

            System.out.println("Se ha añadido 1 transaccion");

            //suma la cantidad de cada transaccion introducida y actualiza el indicador
            cantidad += transaccion.getCantidad();
            lb_cantidad.setText(Double.toString(cantidad) + " €");

            cleanForm();
        }
    }


    //SI SE PULSA SUPRIMIR, ELIMINA EL ITEM DE LA LISTA INTRODUCIDA
    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event) {


        if (event.getCode() == KeyCode.DELETE) {
            int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
            if (idx_eliminar >= 0) {
                list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                cantidad -= grupo.getTransacciones().get(idx_eliminar).getCantidad();
                lb_cantidad.setText(Double.toString(cantidad) + " €");
                transacciones_almacenadas.remove(idx_eliminar);
                grupo.removeTransaccion(idx_eliminar);
            }
        }

    }

    @FXML
    protected void onEliminarTransaccionClick() {
        int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
        System.out.println(list_transacciones.getItems().toString());
        System.out.println(idx_eliminar);


        if (idx_eliminar >= 0) {
            list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            cantidad -= grupo.getTransacciones().get(idx_eliminar).getCantidad();
            lb_cantidad.setText(Double.toString(cantidad) + " €");
            transacciones_almacenadas.remove(idx_eliminar);
            grupo.removeTransaccion(idx_eliminar);
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
            tf_cantidad.setText(String.valueOf(t.getCantidad()));

            DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(t.getFecha(), customDateTimeFormatter);

            date_fecha_transaccion.setValue(localDate);

            tf_pagador_por.setText(t.getId_pagadores().toString());
            tf_deber_por.setText(t.getId_deudores().toString());

            bt_modificar.setVisible(true);
            bt_crear.setVisible(false);
        }
    }

    @FXML
    protected void onGrabarModificacionClick() {
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
        if (!usuario.getRole().equals("PREMIUM")){
            window = bt_premium.getScene().getWindow();

            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Usted no disfruta de cuenta PREMIUM!");

        }else {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "ATENCIÓN",
                    "Esta funcionalidad estará disponible muy pronto!");
        }
    }
}


