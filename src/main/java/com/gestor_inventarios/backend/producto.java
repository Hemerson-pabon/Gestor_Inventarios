package com.gestor_inventarios.backend;

import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class producto {
    private int ID_Producto;
    private String Nombre;
    private float PrecioVenta;
    private String UnidadMedida;
    private float Utilidad;
    private String FechaVencimiento;
    private String Descripcion;
    private int ID_Categoria;
    private int ID_Proveedor;
    private float CostoB; // Costo base del producto
    private float IVA; // Unidad en porcentaje

    // Métodos set para los atributos

    public void setCostoB(float costoB) {
        CostoB = costoB;
    }

    public void setIVA(float IVA) {
        this.IVA = IVA;
    }

    public void setUtilidad(float utilidad) {
        Utilidad = utilidad;
    }
    // Métodos get para todos los atributos

    public int getID_Producto() {
        return ID_Producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public float getPrecioVenta() {
        return PrecioVenta;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public float getCostoB() {
        return CostoB;
    }

    public float getUtilidad() {
        return Utilidad;
    }

    public String getFechaVencimiento() {
        return FechaVencimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    /* Método para crear un producto
    Este método añade los atributos del objeto a la base de datos, creando un nuevo producto.
    retorna true si el producto se creo correctamente.
    */
    public boolean crearProducto(String nombre, String Descripcion, String UnidadMedida, int ID_Categoria, int ID_Proveedor, String Fecha_Vencimiento, float CostoB, float IVA, float Utilidad){
        this.Utilidad = Utilidad;
        this.CostoB = CostoB;
        this.IVA = IVA;

        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Producto");
        columns.add("Nombre");
        columns.add("Descripción");
        columns.add("ID_Categoria");
        columns.add("ID_Proveedor");
        columns.add("Fecha_Vencimiento");
        columns.add("CostoBase");
        columns.add("UnidadMedida");
        columns.add("IVA");
        columns.add("Precio_Venta");
        columns.add("Utilidad");
        ArrayList<Object> values = new ArrayList<>();
        ID_Producto = generarCodigo(nombre, UnidadMedida);
        if (!validarProducto(ID_Producto)){
            // Ventana emergente diciendo que el producto ya existe
            // ------------------------------------------------------
            // |      El producto ya existe en la base de datos     |
            // ------------------------------------------------------
            System.out.println("El producto ya existe...");
            return false;
        }
        values.add(ID_Producto);
        values.add(nombre);
        values.add(Descripcion);
        values.add(ID_Categoria);
        values.add(ID_Proveedor);
        if (Objects.equals(Fecha_Vencimiento, "")){
            values.add(null);
        }else{
            values.add(Date.valueOf(Fecha_Vencimiento));
        }
        values.add(CostoB);
        values.add(UnidadMedida);
        values.add(IVA);
        PrecioVenta = calcularPrecio();
        values.add(PrecioVenta);
        values.add(Utilidad);
        Operaciones_SQL op = new Operaciones_SQL();
        return op.Insert("productos", columns, values) == 1;
    }

    // Método para calcular el costo + iva
    public float calcularCosto() {
        return CostoB + (CostoB*(IVA/100));
    }

    // Método para calcular el precio de venta ---> Precio sugerido
    public float calcularPrecio() {
        return  calcularCosto() + calcularCosto() * (Utilidad/100);
    }

    /* Método para crear códigos para los productos
    Genera un código entre 0 y 99999, basándose en el nombre y la unidad de medida
    */
    public int generarCodigo(String nombre, String unidadMedida) {
        if (Objects.equals(nombre, "") && Objects.equals(unidadMedida, "")){
            return 0;
        }
        int hashNombre = nombre.hashCode();
        int hashUnidadMedida = unidadMedida.hashCode();
        int codigo = Math.abs(hashUnidadMedida + hashNombre);
        return codigo % 100000;
    }

    /* Método para validar si un producto ya existe en la base de datos
    Retorna true si el producto no existe en la base de datos.
    Retorna false si el producto ya existe en la base de datos.
    */
    private boolean validarProducto(int ID_Producto){
       Operaciones_SQL op = new Operaciones_SQL();
       ArrayList<String> columns = new ArrayList<>();
       columns.add("ID_Producto");
       ResultSet rs = op.Select("productos", columns, "ID_Producto = " + String.valueOf(ID_Producto) );
       try{
            while (rs.next()){
                if (rs.getInt("ID_Producto") == ID_Producto){
                    return false;
                }
            }
       }catch (SQLException e){
           System.err.println("Error: " + e.getMessage());
       }
       return true;
    }

    /* Método para eliminar un producto
    Retorna true si se elimino el producto, retorna false si no se elimino el producto (es decir NO existe en la base de datos)
    */
    public boolean eliminarProducto(int ID_Producto) {
       Operaciones_SQL op = new Operaciones_SQL();
       return op.Delete("productos", "ID_Producto = " + String.valueOf(ID_Producto)) == 1;
    }

    // Método para cargar un producto desde la base de datos al objeto

    public producto cargarProducto(int ID_Producto) {
        ArrayList columns = new ArrayList<>();
        columns.add("Nombre");
        columns.add("Precio_Venta");
        columns.add("UnidadMedida");
        columns.add("CostoBase");
        columns.add("Utilidad");
        columns.add("Fecha_Vencimiento");
        columns.add("Descripción");
        try {
           Operaciones_SQL op = new Operaciones_SQL();
           ResultSet rs = op.Select("productos", columns, "ID_Producto = " + String.valueOf(ID_Producto));
           rs.next();
           this.ID_Producto = ID_Producto;
           this.Nombre = rs.getString("Nombre");
           this.PrecioVenta = rs.getFloat("Precio_Venta");
           this.UnidadMedida = rs.getString("UnidadMedida");
           this.CostoB = rs.getFloat("CostoBase");
           this.Utilidad = rs.getFloat("Utilidad");
           this.FechaVencimiento = rs.getString("Fecha_Vencimiento");
           this.Descripcion = rs.getString("Descripción");
           return this;
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }



}
