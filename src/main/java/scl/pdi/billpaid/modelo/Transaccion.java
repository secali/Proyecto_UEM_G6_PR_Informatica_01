package scl.pdi.billpaid.modelo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Transaccion {
    private String id_grupo;
    private String nombre;
    private String descripcion;
    private double cantidad;
    private String tipoTransaccion;

    private List id_pagadores;
    private List id_deudores;

    private String fecha;

    public Transaccion(String id_grupo, String nombre, String descripcion, double cantidad, String tipoTransaccion, List id_pagadores, List id_deudores, String fecha) {
        this.id_grupo = id_grupo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tipoTransaccion = tipoTransaccion;
        this.id_pagadores = id_pagadores;
        this.id_deudores = id_deudores;
        this.fecha = fecha;
    }

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public List getId_pagadores() {
        return id_pagadores;
    }

    public void setId_pagadores(List id_pagadores) {
        this.id_pagadores = id_pagadores;
    }

    public List getId_deudores() {
        return id_deudores;
    }

    public void setId_deudores(List id_deudores) {
        this.id_deudores = id_deudores;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String transaccion2List(){
        return nombre+" --------- Pagado por "+id_pagadores+" --------- "+fecha+" ------- "+cantidad+"€";
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id_grupo='" + id_grupo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", id_pagadores=" + id_pagadores +
                ", id_deudores=" + id_deudores +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
