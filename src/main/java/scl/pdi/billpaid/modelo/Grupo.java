package scl.pdi.billpaid.modelo;

import java.util.ArrayList;

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

    public void cargarDemoTransacciones() {
        ArrayList<String> a = new ArrayList<>();
        a.add("Sergio");
        a.add("Pablo");
        a.add("Maria");
        ArrayList<String> b = new ArrayList<>();
        b.add("Jorge");
        b.add("Antonio");
        b.add("Isa");
        b.add("Carmen");
        transacciones.add(new Transaccion(ID_Grupo, "Cena de tapas", "comida malisima", 45.0, "pago", a.subList(0, 1), b, "2023-01-01"));
        transacciones.add(new Transaccion(ID_Grupo, "Regalo Miguel", "iphone", 999.0, "pago", a, b.subList(0, 1), "2023-02-01"));
        transacciones.add(new Transaccion(ID_Grupo, "Gasolina viaje", "300km", 45, "pago", a.subList(1, 2), b, "2023-02-01"));

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
