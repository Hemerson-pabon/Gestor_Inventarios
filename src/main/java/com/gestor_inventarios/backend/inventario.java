package com.gestor_inventarios.backend;

import com.gestor_inventarios.frontend.Administrador.InOutProductosController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class inventario{
    protected int ID_Sucursal;
    private String Nombre_Sucursal;
    private int Stock_Actual;
    private int Stock_Minimo;
    private int Stock_Maximo;
    // Instación de operaciones SQL
    private Operaciones_SQL op = new Operaciones_SQL();

    // Constructor
    public inventario(String Nombre_Sucursal){
        this.Nombre_Sucursal = Nombre_Sucursal;
    }

    // Métodos set para los atributos

    public void setID_Sucursal(int ID_Sucursal) {
        this.ID_Sucursal = ID_Sucursal;
    }
    public void setNombre_Sucursal(String Nombre_Sucursal) {
        this.Nombre_Sucursal = Nombre_Sucursal;
    }

    // Método para obtener el ID_Sucursal según el nombre

    public int obtenerID(){
        try {
            ArrayList<String> columns = new ArrayList<>();
            columns.add("ID_Sucursal");
            ResultSet rs = op.Select("sucursales", columns, "Nombre_Sucursal = '" + Nombre_Sucursal + "'");
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public ObservableList<producto> cargarInventario(){
        ObservableList<producto> productos = FXCollections.observableArrayList();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Sucursal");

        // Obtener el ID de la sucursal a partir del nombre
        Operaciones_SQL op = new Operaciones_SQL();
        ResultSet rs = op.Select("sucursales", columns, "Nombre_Sucursal = '" + Nombre_Sucursal + "'");
        try {
            rs.next();
            ID_Sucursal = rs.getInt(1);
            System.out.println("El valor de ID Sucursal es: " + ID_Sucursal);
        } catch (SQLException e) {
            System.err.println("Error: al intentar obtener ID " + e.getMessage());
        }
        // Obtener los productos que pertenecen a esa sucursal
        producto pIDs = new producto();
        ArrayList<Integer> ids = pIDs.cargarIDs(ID_Sucursal);
        for (int id : ids) {
            productos.add(new producto().cargarProducto(id, ID_Sucursal));
        }
        return productos;

    }

    // Método para actualizar un inventario
    public int actualizarInventario(String TipoMovimiento, int ID_Producto, int Cantidad){
        int ID_Sucursal = obtenerID();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Stock_Actual");
        ArrayList<Object> values = new ArrayList<>();
        int Nuevo_Stock = 0;
        try {
            ResultSet rs = op.Select("inventario", columns, "ID_Producto = " + ID_Producto + " AND ID_Sucursal = " + ID_Sucursal);
            rs.next();
            switch (TipoMovimiento){
                case "Entrada":
                    Nuevo_Stock = rs.getInt(1) + Cantidad;
                    break;
                    case "Salida":
                        Nuevo_Stock = rs.getInt(1) - Cantidad;
                        break;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        values.add(Nuevo_Stock);
        registrarMovimiento(TipoMovimiento, ID_Producto, Cantidad);
        return op.Update("inventario", columns, values,"ID_Producto = " + ID_Producto + " AND ID_Sucursal = " + ID_Sucursal);
    }

    // Método para registrar un movimiento

    public int registrarMovimiento(String TipoMovimiento, int ID_Producto, int Cantidad ){
        Date FechaMovimiento = Date.valueOf(LocalDate.now());
        Time HoraMovimiento = Time.valueOf(LocalTime.now());
        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Movimiento");
        columns.add("ID_Producto");
        columns.add("Fecha_Movimiento");
        columns.add("Hora_Movimiento");
        columns.add("Cantidad");
        columns.add("Tipo_Movimiento");
        ArrayList<Object> values = new ArrayList<>();
        values.add(generarCodigo(ID_Producto, String.valueOf(FechaMovimiento), String.valueOf(HoraMovimiento), TipoMovimiento));
        values.add(ID_Producto);
        values.add(FechaMovimiento);
        values.add(HoraMovimiento);
        values.add(Cantidad);
        values.add(TipoMovimiento);
        return op.Insert("movimientos", columns, values);
    }

    // Método para generar un código para registrar el movimiento

    public int generarCodigo(int ID_Producto, String Fecha_Movimiento, String Hora_Movimiento, String Tipo_Movimiento) {
        int hashID = String.valueOf(ID_Producto).hashCode();
        int hashTipo = String.valueOf(Tipo_Movimiento).hashCode();
        int hashFecha = Fecha_Movimiento.hashCode();
        int hashHora = Hora_Movimiento.hashCode();
        int codigo = Math.abs(hashID+ hashTipo+ hashFecha + hashHora);
        return codigo % 100000;
    }


}
