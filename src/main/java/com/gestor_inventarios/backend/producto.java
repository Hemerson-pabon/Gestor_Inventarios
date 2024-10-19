package com.gestor_inventarios.backend;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class producto {
    private int ID_Producto;
    private float Precio_Venta;
    private String Descripcion;
    private int ID_Categoria;
    private int ID_Proveedor;
    private float CostoB; // Costo base del producto
    private float Utilidad; // Unidad en porcentaje
    private String UnidadMedida;
    private float IVA; // Unidad en porcentaje

    /* Método para crear un producto
    Este método añade los atributos del objeto a la base de datos, creando un nuevo producto.
    retorna true si el producto se creo correctamente.
    */
    public boolean crearProducto(String nombre, String Descripcion, String UnidadMedida, int ID_Categoria, int ID_Proveedor, String Fecha_Vencimiento, float CostoB, float IVA, float Utilidad) {
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
        values.add(Date.valueOf(Fecha_Vencimiento));
        values.add(CostoB);
        values.add(UnidadMedida);
        values.add(IVA);
        Precio_Venta = calcularPrecio();
        values.add(Precio_Venta);
        values.add(Utilidad);
        Operaciones_SQL op = new Operaciones_SQL();
        return op.Insert("productos", columns, values) == 1;
    }

    // Método para calcular el costo + iva
    private float calcularCosto() {
        return CostoB + (CostoB*(IVA/100));
    }

    // Método para calcular el precio de venta ---> Precio sugerido
    private float calcularPrecio() {
        return  calcularCosto() + calcularCosto() * (Utilidad/100);
    }

    /* Método para crear códigos para los productos
    Genera un código entre 0 y 99999, basándose en el nombre y la unidad de medida
    */
    private int generarCodigo(String nombre, String unidadMedida) {
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



}
