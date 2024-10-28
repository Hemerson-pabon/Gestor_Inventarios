package com.gestor_inventarios.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion_SQL {
    private static Connection conexion;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/gestor_inventarios";
    private static final String user = "root";
    //private static final String password = "7P2a%Wj3";
    private static final String password = "2006";

    // Metodo main para la conexión
    public Conexion_SQL() {
        conexion = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);
            if (conexion!=null){
                System.out.println("Conexion establecida");
            }
            Statement statement = conexion.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al conectar con la base de datos: " +e.getMessage());
        }
    }
    // Getter para la conexión
    public Connection getConexion(){
        return conexion;
    }

    // Método para cerrar la conexión
    public void close(){
        conexion = null;
        System.out.println("Conexion cerrada");
    }


}
