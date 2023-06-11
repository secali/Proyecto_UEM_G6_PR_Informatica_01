package scl.pdi.billpaid.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class Grupo {

    private final String id_username_creador;
    private final String nombre;
    private String ID_Grupo;
    private String Descripcion;

    static private ArrayList<Transaccion> transacciones; //Necesito saber si esto lo usa alguien

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }
    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    public void setTransaccion(Transaccion t) {
        transacciones.add(t);
    }
    public void removeTransaccion(int index) {
        transacciones.remove(index);
    }
    public String listarGrupo() {
        return ID_Grupo + " --------- Nombre del grupo" + Descripcion + " --------- (Click para abrir transacciones)";
    }

}
