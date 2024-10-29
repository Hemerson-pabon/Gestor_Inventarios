package com.gestor_inventarios.backend;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class producto {
    // Atributos completos del producto
    private int ID_Producto;
    private int ID_Categoria;
    private int ID_Proveedor;
    private String Fecha_Vencimiento;
    private String Nombre;
    private String Descripcion;
    private float Precio_Venta;
    private String UnidadMedida;
    private float Utilidad;
    private float CostoBase;
    private float IVA;
    // Atributos para el producto según la sucursal
    private int Stock_Minimo;
    private int Stock_Maximo;
    private int Stock_Actual;

    // Métodos set para los atributos

    public void setCostoBase(float costoB) {
        CostoBase = costoB;
    }

    public void setIVA(float IVA) {
        this.IVA = IVA;
    }

    public void setUtilidad(float utilidad) {
        Utilidad = utilidad;
    }

    public void setPrecio_Venta(float precioVenta) {
        Precio_Venta = precioVenta;
    }

    // Métodos get para todos los atributos

    public int getID_Producto() {
        return ID_Producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public float getPrecio_Venta() {
        return Precio_Venta;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public float getCostoBase() {
        return CostoBase;
    }

    public float getUtilidad() {
        return Utilidad;
    }

    public String getFecha_Vencimiento() {
        return Fecha_Vencimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public int getStock_Actual() {
        return Stock_Actual;
    }

    public int getStock_Maximo() {
        return Stock_Maximo;
    }

    public int getStock_Minimo() {
        return Stock_Minimo;
    }

    /* Método para crear un producto
    Este método añade los atributos del objeto a la base de datos, creando un nuevo producto.
    retorna true si el producto se creo correctamente.
    */
    public boolean crearProducto(String nombre, String Descripcion, String UnidadMedida, int ID_Categoria, int ID_Proveedor, String Fecha_Vencimiento, float CostoB, float IVA, float Utilidad){
        this.Utilidad = Utilidad;
        this.CostoBase = CostoB;
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
        Precio_Venta = calcularPrecio();
        values.add(Precio_Venta);
        values.add(Utilidad);
        Operaciones_SQL op = new Operaciones_SQL();
        return op.Insert("productos", columns, values) == 1;
    }

    // Método para editar las características de un producto
    public boolean actualizarProducto(int ID_Producto, String nombre, String Descripcion, String UnidadMedida, int ID_Categoria, int ID_Proveedor, String Fecha_Vencimiento, float CostoB, float IVA, float Utilidad){
        this.Utilidad = Utilidad;
        this.CostoBase = CostoB;
        this.IVA = IVA;
        ArrayList<String> columns = new ArrayList<>();
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
        Precio_Venta = calcularPrecio();
        values.add(Precio_Venta);
        values.add(Utilidad);
        Operaciones_SQL op = new Operaciones_SQL();
        return  op.Update("productos", columns, values, "ID_Producto = " + ID_Producto) == 1;
    }




    // Método para calcular el costo + iva
    public float calcularCosto() {
        return CostoBase + (CostoBase*(IVA/100));
    }

    // Método para calcular el precio de venta ---> Precio sugerido
    public float calcularPrecio() {
        return  calcularCosto() + calcularCosto() * (Utilidad/100);
    }

    // Método para calcular la utilidad
    public float calcularUtilidad(){return ((Precio_Venta/calcularCosto()) - 1) / 100; }

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

    // Método para buscar los IDs de los productos que estan en una sucursal
    public ArrayList<Integer> cargarIDs(int ID_Sucursal){
        ArrayList columns = new ArrayList<>();
        columns.add("ID_Producto");
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("inventario", columns, "ID_Sucursal = " + String.valueOf(ID_Sucursal));
            while (rs.next()){
                ids.add(rs.getInt("ID_producto"));
            }
            return ids;
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


    // Método para cargar un producto desde la base de datos al objeto

    public producto cargarProducto(int ID_Producto, int ID_Sucursal) {
        // Columnas para los datos del inventario
        ArrayList columnsInventario = new ArrayList<>();
        columnsInventario.add("ID_Producto");
        columnsInventario.add("Stock_Mínimo");
        columnsInventario.add("Stock_Maximo");
        columnsInventario.add("Stock_Actual");
        // Columnas para los datos del producto
        ArrayList columnsProducto = new ArrayList<>();
        columnsProducto.add("ID_Producto");
        columnsProducto.add("Precio_Venta");
        columnsProducto.add("UnidadMedida");
        columnsProducto.add("Nombre");
        columnsProducto.add("CostoBase");
        columnsProducto.add("Utilidad");
        columnsProducto.add("Fecha_Vencimiento");
        columnsProducto.add("Descripción");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs;
            ResultSet rsInvent;
            if (ID_Producto == 0){
                rs = op.Select("productos", columnsProducto, "ID_Producto > 0");
            }else{
                rs = op.Select("productos", columnsProducto, "ID_Producto = " + ID_Producto);
            }
            rsInvent = op.Select("inventario", columnsInventario, "ID_Producto = " + ID_Producto + " AND " + "ID_Sucursal = " + ID_Sucursal);
            rsInvent.next();
            rs.next();
           this.ID_Producto = rs.getInt("ID_Producto");
           this.Nombre = rs.getString("Nombre");
           this.Precio_Venta = rs.getFloat("Precio_Venta");
           this.UnidadMedida = rs.getString("UnidadMedida");
           this.CostoBase = rs.getFloat("CostoBase");
           this.Utilidad = rs.getFloat("Utilidad");
           // Convertir la fecha de DATE a String
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            this.Fecha_Vencimiento = formater.format(rs.getObject("Fecha_Vencimiento"));
            this.Descripcion = rs.getString("Descripción");

           // Datos desde la tabla de inventario
            this.Stock_Actual = rsInvent.getInt("Stock_Actual");
            this.Stock_Minimo = rsInvent.getInt("Stock_Mínimo");
            this.Stock_Maximo = rsInvent.getInt("Stock_Maximo");
           return this;
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }




}
