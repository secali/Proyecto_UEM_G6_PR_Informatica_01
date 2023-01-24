package scl.pdi.billpaid;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import scl.pdi.billpaid.modelo.Transaccion;

import java.time.Clock;
import java.util.ArrayList;

public class TransaccionController {
    private Transaccion transaccion;
    private ArrayList<Transaccion> transacciones_almacenadas = new ArrayList<>();
    @FXML
    private Label lb_cantidad;
    @FXML
    private ListView <String> list_transacciones;
    @FXML
    private TextField tf_nombre_transaccion;
    @FXML
    private TextArea tf_descrip_trans;
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
    protected void onCrearTransaccionButtonClick() {

        ArrayList<String>pagadores = new ArrayList<>();
        pagadores.add(tf_pagador_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST

        ArrayList<String>deudores = new ArrayList<>();
        deudores.add(tf_deber_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST



        transaccion = new Transaccion("1",tf_nombre_transaccion.getText(), tf_descrip_trans.getText(), Double.parseDouble(tf_cantidad.getText()),
                "normal",pagadores,deudores, date_fecha_transaccion.getValue().toString());

        System.out.println(transaccion);

        //Usa el método de Transaccion.java para pasar a String los parametros relevantes y devolverlos en una cadena que pinta el listview
        list_transacciones.getItems().addAll(transaccion.transaccion2List());
        transacciones_almacenadas.add(transaccion);

        //suma la cantidad de cada transaccion introducida y actualiza el indicador
        cantidad += transaccion.getCantidad();
        lb_cantidad.setText(Double.toString(cantidad)+" €");

    }


    //SI SE PULSA SUPRIMIR, ELIMINA EL ITEM DE LA LISTA INTRODUCIDA
    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event) {


            if (event.getCode() == KeyCode.DELETE) {
                int idx_eliminar = list_transacciones.getSelectionModel().getSelectedIndex();
                if (idx_eliminar > 0) {
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

        if (idx_eliminar > 0) {
            list_transacciones.getItems().remove(idx_eliminar);  //elimina de la lista

            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            cantidad -= transacciones_almacenadas.get(idx_eliminar).getCantidad();
            lb_cantidad.setText(Double.toString(cantidad) + " €");
            transacciones_almacenadas.remove(idx_eliminar);
        }
    }
    }


