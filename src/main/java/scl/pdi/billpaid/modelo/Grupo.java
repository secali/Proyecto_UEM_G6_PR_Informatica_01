package scl.pdi.billpaid.modelo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Grupo {

    private int ID_Persona;
    private String nombre;
    private Date fechaCreacion;

    public Grupo (int ID_Persona, String nombre, Date fechaCreacion){
        this.ID_Persona = ID_Persona;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }
    public int getID_Persona() {
        return ID_Persona;
    }
    public void setID_Persona(int ID_Persona) {
        this.ID_Persona = ID_Persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public void modificarGrupo(){

    }
    public void eliminarGrupo(){

    }

    public void importarGrupo(){

    }

    public void exportarGrupo(){

    }
    public void mostrarGrupo(){
    }


}
