package scl.pdi.billpaid.modelo;

import java.util.Arrays;
import java.util.Date;

public class Transaccion {
    private String id_grupo;
    private String nombre;
    private String descripcion;
    private double cantidad;
    private String tipoTransaccion;

    private String [] id_pagadores;
    private String [] id_deudores;

    private Date fecha;

    public Transaccion(String id_grupo, String nombre, String descripcion, double cantidad, String tipoTransaccion, String[] id_pagadores, String[] id_deudores, Date fecha) {
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

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public String[] getId_pagadores() {
        return id_pagadores;
    }

    public String[] getId_deudores() {
        return id_deudores;
    }

    public Date getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id_grupo='" + id_grupo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", id_pagadores=" + Arrays.toString(id_pagadores) +
                ", id_deudores=" + Arrays.toString(id_deudores) +
                ", fecha=" + fecha +
                '}';
    }
}
