package com.gestor_inventarios.frontend.Empleados;

//Tabla para la devolucion

public class ProductoDevo {
    private String codigo;
    private String nombre;
    private Integer cantidad;
    private double precioUnd;


    public ProductoDevo(String codigo, String nombre, Integer cantidad, double precioUnd) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnd = precioUnd;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecioUnd() {
        return precioUnd;
    }
    public void setPrecioUnd(double precioUnd) {
        this.precioUnd = precioUnd;
    }
}
