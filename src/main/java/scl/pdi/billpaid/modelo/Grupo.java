package scl.pdi.billpaid.modelo;

import java.util.ArrayList;

public class Grupo {

    private String ID_Grupo;
    private String Descripcion;
    private double CantidadIntegrantes;

      private ArrayList <Transaccion> transacciones;


      //Constructor para Nombre de grupo, descripcion del grupo, cantidad de integrantes.
    public Grupo (String ID_Grupo, String Descripcion, double CantidadIntegrantes){
        this.ID_Grupo = ID_Grupo;
        this.Descripcion = Descripcion;
        this.CantidadIntegrantes = CantidadIntegrantes;

        transacciones = new ArrayList<>();

    }


    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }


    public String getID_Persona() {
        return ID_Grupo;
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
        return ID_Grupo + " --------- Nombre del grupo" + Descripcion + " --------- " + CantidadIntegrantes;
    }
    public String toString(){
        return "Grupo {" +
                "id_grupo ='" + ID_Grupo + '\'' +
                ", nombre='" + Descripcion + '\'' +
                ", Cantidad de Integrantes='" + CantidadIntegrantes + '\'' +
                '}';
    }


}
