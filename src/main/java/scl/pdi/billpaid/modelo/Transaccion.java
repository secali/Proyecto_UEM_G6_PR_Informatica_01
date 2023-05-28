package scl.pdi.billpaid.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Transaccion {
    private String id_grupo;
    private String nombre;
    private String descripcion;
    private String fecha;
    private double importe;
    private String id_pagador;
    private String id_deudor;
    private String id_creador;

}
