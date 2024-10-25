package com.gestor_inventarios.frontend.Empleados;

public class ProductoVentas {
    private String codigoVenta;
    private String nombreVenta;
    private Integer cantidadVenta;
    private Double precioVentaUnd;

    public ProductoVentas(String codigoVenta, String nombreVenta, Integer cantidadVenta, Double precioVentaUnd) {
        this.codigoVenta = codigoVenta;
        this.nombreVenta = nombreVenta;
        this.cantidadVenta = cantidadVenta;
        this.precioVentaUnd = precioVentaUnd;
    }
    public String getCodigoVenta() {
        return codigoVenta;
    }
    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }
    public String getNombreVenta() {
        return nombreVenta;
    }
    public void setNombreVenta(String nombreVenta) {
        this.nombreVenta = nombreVenta;
    }
    public Integer getCantidadVentas() {
        return cantidadVenta;
    }
    public void setCantidadVentas(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }
    public Double getPrecioVentaUnd() {
        return precioVentaUnd;
    }
    public void setPrecioVentaUnd(Double precioVentaUnd) {
        this.precioVentaUnd = precioVentaUnd;
    }
}
