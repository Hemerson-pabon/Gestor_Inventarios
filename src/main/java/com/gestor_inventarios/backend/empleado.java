package com.gestor_inventarios.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class empleado {
    private String Nombre_Empleado;
    private String Nombre_Usuario;
    private String Tipo_Usuario;
    private String Sucursal;

    public String getNombre_Empleado() {
        return Nombre_Empleado;
    }

    public String getNombre_Usuario() {
        return Nombre_Usuario;
    }

    public String getTipo_Usuario() {
        return Tipo_Usuario;
    }

    public String getSucursal() {
        return Sucursal;
    }

    Operaciones_SQL op = new Operaciones_SQL();

    public ArrayList<Integer> obtenerIDs(){
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ResultSet rs = op.Select("usuarios", new ArrayList<>(Arrays.asList("ID_Empleado")),"");
            while (rs.next()) {
                ids.add(rs.getInt("ID_Empleado"));
            }
            return ids;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (NullPointerException e) {
        }
        return null;
    }

    public empleado cargarEmpleado(int ID_Empleado){
        ArrayList<String> columnsU = new ArrayList<>();
        columnsU.add("user");
        columnsU.add("Tipo_Usuario");
        ArrayList<String> columnsE = new ArrayList<>();
        columnsE.add("Nombre_Empleado");
        columnsE.add("ID_Sucursal");
        ArrayList<String> columnsS = new ArrayList<>();
        columnsS.add("Nombre_Sucursal");
        try {
            ResultSet rsU = op.Select("usuarios", columnsU,"ID_Empleado = " + ID_Empleado);
            ResultSet rsE = op.Select("empleados", columnsE, "ID_Empleado = " + ID_Empleado);
            rsE.next();
            ResultSet rsS = op.Select("sucursales", columnsS, "ID_Sucursal = " + rsE.getInt("ID_Sucursal"));
            rsU.next();
            rsS.next();
            this.Nombre_Empleado = rsE.getString("Nombre_Empleado");
            this.Nombre_Usuario = rsU.getString("user");
            this.Tipo_Usuario = rsU.getString("Tipo_Usuario");
            this.Sucursal = rsS.getString("Nombre_Sucursal");
            return this;
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


    public int generarIDEmpleado(String Nombre_Empleado, String Tipo_Usuario, String Sucursal){
        int Nombre = Nombre_Empleado.hashCode();
        int Tipo = Tipo_Usuario.hashCode();
        int Suc = Sucursal.hashCode();
        return (Math.abs(Nombre + Tipo + Suc)) % 100000;
    }

    public boolean crearEmpleado(int ID_Empleado, String Nombre_Empleado, int ID_Sucursal){
        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Empleado");
        columns.add("Nombre_Empleado");
        columns.add("ID_Sucursal");
        ArrayList<Object> values = new ArrayList<>();
        values.add(ID_Empleado);
        values.add(Nombre_Empleado);
        values.add(ID_Sucursal);
        return op.Insert("empleados",columns, values) >= 1;
    }

}
