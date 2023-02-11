package scl.pdi.billpaid;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scl.pdi.billpaid.MainPanelController;
import scl.pdi.billpaid.modelo.Grupo;

import java.util.ArrayList;

public class CreacionController extends MainPanelController {
    private Grupo grupo;
    private ArrayList<Grupo> gruposAlmacenados=new ArrayList<>();

    //contador
    private double cantidad = 0.00;

    @FXML
    private ListView <String> list_grupos;
    @FXML
    private Label lb_cantidad;
    @FXML
    private TextField tf_nombreGrupo;
    @FXML
    private TextArea tf_descripcionGrupo;
    @FXML
    private TextArea tf_cantidadIntegrantes;

    @FXML
    protected void onCrearGrupoButtonClick() {



        //crear el grupo
        grupo = new Grupo(tf_nombreGrupo.getText(),tf_descripcionGrupo.getText(),
                (int) Double.parseDouble(tf_cantidadIntegrantes.getText()));

        //Pintar la lista
        list_grupos.getItems().addAll(grupo.listarGrupo());
        gruposAlmacenados.add(grupo);

        System.out.println("Grupo creado correctamente");
    }

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
    @FXML
    protected void onEliminarTransaccionClick() {
        int idx_eliminar = list_grupos.getSelectionModel().getSelectedIndex();

        System.out.println(idx_eliminar);


        if (idx_eliminar >= 0) {
            list_grupos.getItems().remove(idx_eliminar);  //elimina de la lista

            //Actualiza la cantidad mostrada y elimina el objeto almacenado
            cantidad -= gruposAlmacenados.get(idx_eliminar).getCantidadIntegrantes();
            lb_cantidad.setText(Double.toString(cantidad) + " €");
            gruposAlmacenados.remove(idx_eliminar);
        }
    }
} // final del controlador de "Creacion"
