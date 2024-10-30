package com.gestor_inventarios.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class movimiento {
    private int ID_Movimiento;
    private Double ID_Producto;
    private String Fecha_Movimiento;
    private String Hora_Movimiento;
    private int Cantidad;
    private String Tipo_Movimiento;

    public Double getID_Producto() {
        return ID_Producto;
    }

    public int getID_Movimiento() {
        return ID_Movimiento;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public String getFecha_Movimiento() {
        return Fecha_Movimiento;
    }

    public String getHora_Movimiento() {
        return Hora_Movimiento;
    }

    public String getTipo_Movimiento() {
        return Tipo_Movimiento;
    }

    Operaciones_SQL op = new Operaciones_SQL();

    public ArrayList<Integer> obtenerIDsM(String Fecha_Movimiento){
        ArrayList<Integer> ids = new ArrayList<>();
        if (Fecha_Movimiento == null){
            try {
                ResultSet rs = op.Select("movimientos", new ArrayList<>(Arrays.asList("ID_Movimiento")), "");
                while (rs.next()) {
                    ids.add(rs.getInt("ID_Movimiento"));
                }
                return ids;
            }catch (SQLException e){
                System.err.println("Error: " + e.getMessage());
            }
        }else{
            try {
                ResultSet rs = op.Select("movimientos", new ArrayList<>(Arrays.asList("ID_Movimiento")), "Fecha_Movimiento='" + Fecha_Movimiento + "'");
                while (rs.next()){
                    ids.add(rs.getInt("ID_Movimiento"));
                }
                return ids;
            }catch (SQLException e){
                System.err.println("Error: " + e.getMessage());
            }
        }

        return null;
    }

    public movimiento cargarMovimiento(int ID_Movimiento){
        ArrayList<String> columns = new ArrayList<>(Arrays.asList("ID_Producto", "Fecha_Movimiento", "Hora_Movimiento", "Cantidad", "Tipo_Movimiento"));
        try {
            ResultSet rs = op.Select("movimientos", columns, "ID_Movimiento = " + ID_Movimiento );
            rs.next();
            this.ID_Movimiento = ID_Movimiento;
            this.ID_Producto = rs.getDouble("ID_Producto");
            this.Fecha_Movimiento = rs.getString("Fecha_Movimiento");
            this.Hora_Movimiento = rs.getString("Hora_Movimiento");
            this.Cantidad = rs.getInt("Cantidad");
            this.Tipo_Movimiento = rs.getString("Tipo_Movimiento");
            return this;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        ImprimirMovimiento();
        return null;
    }


    public void ImprimirMovimiento(){
        System.out.println("-------------------------------------");
        System.out.println("ID_Movimiento: " + this.ID_Movimiento);
        System.out.println("ID_Producto: " + this.ID_Producto);
        System.out.println("Fecha_Movimiento: " + this.Fecha_Movimiento);
        System.out.println("Hora_Movimiento: " + this.Hora_Movimiento);
        System.out.println("Cantidad: " + this.Cantidad);
        System.out.println("Tipo_Movimiento: " + this.Tipo_Movimiento);
        System.out.println("-------------------------------------");
    }



}
