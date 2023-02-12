package scl.pdi.billpaid;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import scl.pdi.billpaid.modelo.Transaccion;
import scl.pdi.billpaid.modelo.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.ClientInfoStatus;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransaccionController extends MainPanelController {
    private Transaccion transaccion;
    private ArrayList<Transaccion> transacciones_almacenadas = new ArrayList<>();
    private ArrayList<User> deudores = new ArrayList<>();
    @FXML
    private Label lb_cantidad;
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
    private Button home;

    @FXML
    protected void onCrearTransaccionButtonClick() {

        ArrayList<String> pagadores = new ArrayList<>();
        pagadores.add(tf_pagador_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST

        //deudoSres.add(tf_deber_por.getText());
        //falta ajustar esto del string.....
        //String[] deudores_str = split(tf_deber_por.getText());

        //for (String subString : deudores_str) {

        //  deudores.add(new User(subString, "", "usuario"));
        //}

        //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST


        transaccion = new Transaccion("1", tf_nombre_transaccion.getText(), tf_descrip_trans.getText(), Double.parseDouble(tf_cantidad.getText()),
                "normal", pagadores, deudores, date_fecha_transaccion.getValue().toString());


        //Usa el método de Transaccion.java para pasar a String los parametros relevantes y devolverlos en una cadena que pinta el listview
        list_transacciones.getItems().addAll(transaccion.transaccion2List());
        transacciones_almacenadas.add(transaccion);

        System.out.println("Se ha añadido 1 transaccion");

        //suma la cantidad de cada transaccion introducida y actualiza el indicador
        cantidad += transaccion.getCantidad();
        lb_cantidad.setText(Double.toString(cantidad) + " €");

        cleanForm();
    }


    //SI SE PULSA SUPRIMIR, ELIMINA EL ITEM DE LA LISTA INTRODUCIDA
    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event) {


        if (event.getCode() == KeyCode.DELETE) {
            int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
            if (idx_eliminar >= 0) {
                list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

                //Actualiza la cantidad mostrada y elimina el objeto almacenado
                cantidad -= transacciones_almacenadas.get(idx_eliminar).getCantidad();
                lb_cantidad.setText(Double.toString(cantidad) + " €");
                transacciones_almacenadas.remove(idx_eliminar);
            }
        }

    }

    @FXML
    protected void onEliminarTransaccionClick() {
        int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();

        System.out.println(idx_eliminar);


        if (idx_eliminar >= 0) {
            list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            cantidad -= transacciones_almacenadas.get(idx_eliminar).getCantidad();
            lb_cantidad.setText(Double.toString(cantidad) + " €");
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
    protected void onModificarTransaccionClick(){
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
        }
    }


    private void cleanForm(){
        tf_nombre_transaccion.clear();
        tf_descrip_trans.clear();
        tf_cantidad.clear();
        tf_pagador_por.clear();
        tf_deber_por.clear();
        date_fecha_transaccion.getEditor().clear();
    }
}


