package scl.pdi.billpaid.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

//@Getter
//@Setter
//@ToString
//@AllArgsConstructor

public class Grupo {

    private String ID_Grupo;
    private String Descripcion;
    private double CantidadIntegrantes;
    private ArrayList<Transaccion> transacciones;

    //Constructor para Nombre de grupo, descripcion del grupo, cantidad de integrantes.
    public Grupo(String ID_Grupo, String Descripcion) {
        this.ID_Grupo = ID_Grupo;
        this.Descripcion = Descripcion;
        transacciones = new ArrayList<>();
    }

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


    public String getID_Persona() {
        return ID_Grupo;
    }

    public String getDescripcion(){
        return Descripcion;
    }

    public void setID_Persona(String ID_Persona) {
        this.ID_Grupo = ID_Persona;
    }

    public String getNombre() {
        return ID_Grupo;
    }

    public void setNombre(String nombre) {
        this.Descripcion = nombre;
    }

    public double getCantidadIntegrantes() {
        return CantidadIntegrantes;
    }

    public void setCantidadIntegrantes(int fechaCreacion) {
        this.CantidadIntegrantes = fechaCreacion;
    }

    public String listarGrupo() {
        return ID_Grupo + " --------- Nombre del grupo" + Descripcion + " --------- (Click para abrir transacciones)";
    }

    public String toString() {
        return "Grupo {" +
                "id_grupo ='" + ID_Grupo + '\'' +
                ", nombre='" + Descripcion + '\''+
                '}';
    }
}
