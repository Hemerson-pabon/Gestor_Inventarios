package com.gestor_inventarios.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class sucursal {


    Operaciones_SQL op = new Operaciones_SQL();

    public int obtenerID_Sucursal(String Nombre_Sucursal){
        try {
            ResultSet rs = op.Select("sucursales", new ArrayList<>(Arrays.asList("ID_Sucursal")), "Nombre_Sucursal = '" + Nombre_Sucursal + "'");
            rs.next();
            return rs.getInt("ID_Sucursal");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public boolean crearSucursal(int ID_Sucursal, String Dirección, int ID_Encargado, String Nombre_Sucursal ){
        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Sucursal");
        columns.add("Dirección");
        columns.add("ID_Empleado");
        columns.add("Nombre_Sucursal");
        ArrayList<Object> values = new ArrayList<>();
        values.add(ID_Sucursal);
        values.add(Dirección);
        values.add(ID_Encargado);
        values.add(Nombre_Sucursal);
        return op.Insert("sucursales", columns, values) >= 1;
    }

    public int generarIDSucursal(String Nombre_Sucursal){
        int hashN = Nombre_Sucursal.hashCode();
        return Math.abs(hashN) % 100000;
    }

}
