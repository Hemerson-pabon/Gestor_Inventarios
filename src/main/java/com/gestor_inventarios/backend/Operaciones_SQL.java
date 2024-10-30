package com.gestor_inventarios.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Operaciones_SQL {
    private Conexion_SQL cn;
    private PreparedStatement ST;

    public Operaciones_SQL() {
        // cn = new Conexion_SQL();
        ST = null;
    }

    /* Método para obtener datos desde la base de datos
    Retorna un ResultSet con los datos de la consulta
    String table --> String con el nombre de la tabla donde se van a consultar los datos
    Arraylist columns --> ArrayList de strings con los nombres de las columnas que se van a consultar
    String condition --> Condición para filtrar los datos, si no hay condición debe ir "" y se devolverán todas las filas
     */

    public ResultSet Select(String table, ArrayList<String> columns, String condition){
        cn = new Conexion_SQL();
        // Construcción del string para obtener los datos
        StringBuilder sql = new StringBuilder("SELECT ");
        if (columns == null){
            sql.append("*");
        }else {
            for (int i = 0; i < columns.size(); i++) {
                if (i == columns.size() - 1) {
                    sql.append(columns.get(i)).append(" ");
                }else {
                    sql.append(columns.get(i)).append(", ");
                }
            }
        }
        sql.append("FROM ").append(table);
        if (!Objects.equals(condition, "")){
            sql.append(" WHERE ").append(condition);
        }
        // Ejecución de la sentencia SQL
        try {
            System.out.println(sql.toString());
            ST = cn.getConexion().prepareStatement(sql.toString());
            return ST.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al intentar consultar datos: " + e.getMessage());
        }
       finally {
        cn.close();
        }
        return null;
    }

    /* Método para insertar datos en la base de datos
    Retorna 1 si la inserción fue exitosa, retorna 0 si no lo fue
    String table --> String con la tabla donde se van a insertar los datos
    ArrayList columns --> Arraylist de string con los nombres de las columnas donde se asignaran valores
    ArrayList values --> Arraylist con los datos que se van a insertar (DEBEN SEGUIR EL MISMO ORDEN DE LAS COLUMNAS)
    */
    public int Insert(String table, ArrayList<String> columns, ArrayList<Object> values){
        cn = new Conexion_SQL();
        // Construcción del string para insertar datos
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table);
        sql.append(" (");
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                sql.append(columns.get(i));
            }else{
                sql.append(columns.get(i)).append(", ");
            }
        }
        sql.append(") VALUES (");

        for (int i = 0; i < values.size(); i++) {
            if (i == values.size() - 1) {
                sql.append("?");
            }
            else {
                sql.append("?, ");
            }
        }
        sql.append(")");
        try{
            ST = cn.getConexion().prepareStatement(sql.toString());
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                switch (value) {
                    case String s -> ST.setString(i + 1, s);  // Para cadenas
                    case Integer integer -> ST.setInt(i + 1, integer);  // Para enteros
                    case Double v -> ST.setDouble(i + 1, v);  // Para números decimales
                    case Boolean b -> ST.setBoolean(i + 1, b);  // Para valores booleanos
                    case null, default -> ST.setObject(i + 1, value);  // Para otros tipos de datos
                }
            }

            return ST.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error al intentar insertar datos: " + e.getMessage());
        }
        finally {
            cn.close();
        }
        return 0;
    }

    /* Método para eliminar datos
    Retorna 1 si elimino los datos correctamente, retorna 0 si no lo fue
    String table --> String con el nombre de la tabla donde se van a borrar los datos
    String condition --> String con la condición para la fila que se va a borrar, debe ser la llave primaria de la tabla
    Si String condition = "" , SE BORRARAN TODOS LOS DATOS DE LA TABLA.

     */
    public int Delete(String table, String condition){
        cn = new Conexion_SQL();
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(table);
        if (!Objects.equals(condition, "")){
            sql.append(" WHERE ").append(condition);
        }
        try{
            ST = cn.getConexion().prepareStatement(sql.toString());
            return ST.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar datos: " + e.getMessage());
        }finally {
            cn.close();
        }
        return 0;
    }

    /* Método para actualizar datos
    Retorna 1 si actualizo los datos correctamente, retorna 0 si no lo fue
    String table --> String con el nombre de la tabla en la que se van a actualizar los datos
    ArrayList columns --> ArrayList con los nombres de las columnas de los datos que se van a modificar
    ArrayList values --> ArrayList con los valores para actualizar, DEBEN SEGUIR EL MISMO ORDEN QUE LAS COLUMNAS
    String condition --> Condición para filtrar la fila en la que se van a actualizar los datos
    si String condition = "", LA ACTUALIZACIÓN SE APLICARA A TODAS LAS FILAS.
     */

    public int Update(String table, ArrayList<String> columns, ArrayList<Object> values, String condition){
        cn = new Conexion_SQL();
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(table);
        sql.append(" SET ");
        for (int i = 0; i < columns.size(); i++) {
            sql.append(columns.get(i)).append(" = ?");
            if (i < columns.size() - 1) {
                sql.append(", ");
            }
        }
        if (!Objects.equals(condition, "")){
            sql.append(" WHERE ").append(condition);
        }
        try{
            System.out.println(sql.toString());
            ST = cn.getConexion().prepareStatement(sql.toString());
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                switch (value) {
                    case String s -> ST.setString(i + 1, s);  // Para cadenas
                    case Integer integer -> ST.setInt(i + 1, integer);  // Para enteros
                    case Double v -> ST.setDouble(i + 1, v);  // Para números decimales
                    case Boolean b -> ST.setBoolean(i + 1, b);  // Para valores booleanos
                    case null, default -> ST.setObject(i + 1, value);  // Para otros tipos de datos
                }
            }
            return ST.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error al intentar actualizar datos: " + e.getMessage());
        }finally {
            cn.close();
        }
        return 0;
    }



}
