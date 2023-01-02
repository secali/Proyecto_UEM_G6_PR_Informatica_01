package scl.pdi.billpaid;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import scl.pdi.billpaid.modelo.Transaccion;

import java.time.Clock;
import java.util.ArrayList;

public class TransaccionController {
    @FXML
    private Label lb_cantidad;
    @FXML
    private ListView <String> list_transacciones;
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
    protected void onCrearTransaccionButtonClick() {

        ArrayList<String>pagadores = new ArrayList<>();
        pagadores.add(tf_pagador_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST

        ArrayList<String>deudores = new ArrayList<>();
        deudores.add(tf_deber_por.getText()); //HABRIA QUE SEPARAR POR COMAS Y METERLOS EN EL ARRAYLIST



        Transaccion t = new Transaccion("1",tf_nombre_transaccion.getText(), tf_descrip_trans.getText(), Double.parseDouble(tf_cantidad.getText()),
                "normal",pagadores,deudores, date_fecha_transaccion.getValue().toString());

        System.out.println(t);

        //Usa el método de Transaccion.java para pasar a String los parametros relevantes y devolverlos en una cadena que pinta el listview
        list_transacciones.getItems().addAll(t.transaccion2List());

        //suma la cantidad de cada transaccion introducida y actualiza el indicador
        cantidad += t.getCantidad();
        lb_cantidad.setText(Double.toString(cantidad)+" €");

    }


    //SI SE PULSA SUPRIMIR, ELIMINA EL ITEM DE LA LISTA INTRODUCIDA
    @FXML
    protected void onEliminarTransaccionSUPR(KeyEvent event){
        if (event.getCode() == KeyCode.DELETE){
            list_transacciones.getItems().remove(list_transacciones.getSelectionModel().getSelectedIndex());
        }
    }

}

