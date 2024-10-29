package com.gestor_inventarios.backend;

import com.gestor_inventarios.frontend.Administrador.AlertaController;
import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class categoria {

    Operaciones_SQL op = new Operaciones_SQL();



    public boolean crearCategoria(String Nombre_Categoria){
        ArrayList<String> columns = new ArrayList<>();
        columns.add("ID_Categoria");
        columns.add("Nombre_Categoria");
        ArrayList<Object> values = new ArrayList<>();
        values.add(generarID_Categoria(Nombre_Categoria));
        values.add(Nombre_Categoria);
        if (validarCategoria(generarID_Categoria(Nombre_Categoria))){
            return op.Insert("categorias", columns, values) == 1;
        }
        return false;
    }


    public boolean actualizarCategoria(String Nombre_Categoria, String Nuevo_Nombre){
        // Obtener el ID antiguo
        ArrayList<String> columnsC = new ArrayList<>(Arrays.asList("ID_Categoria"));
        ResultSet rs = op.Select("categorias", columnsC, "Nombre_Categoria = '" + Nombre_Categoria + "'");
        int ID_Antiguo = 0;
        try {
            rs.next();
            ID_Antiguo = rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        //Mover todos los ID_Categoria de los productos pertenecientes a la categoría a actualizar a una categoría por defecto
        ArrayList<String> columnsp = new ArrayList<>(Arrays.asList("ID_Categoria"));
        ArrayList<Object> valuesp = new ArrayList<>();
        valuesp.add(0);

        int r1 = op.Update("productos", columnsp, valuesp, "ID_Categoria = " + ID_Antiguo);
        // Borrar la categoría con el ID Antiguo
        int r2 = op.Delete("categorias", "ID_Categoria = " + ID_Antiguo);
        // Crear una nueva categoría con el nombre nuevo y ID_Categoria nuevo
        int Nuevo_ID = generarID_Categoria(Nuevo_Nombre);
        int r3 = op.Insert("categorias", new ArrayList<>(Arrays.asList("ID_Categoria", "Nombre_Categoria")), new ArrayList<>(Arrays.asList(Nuevo_ID, Nuevo_Nombre)));
        // Mover todos los productos a la nueva categoria creada
        int r4 = op.Update("productos", columnsp, new ArrayList<>(Arrays.asList(Nuevo_ID)), "ID_Categoria = " + 0);
        return (r1 + r2 + r3 + r4) > 1;
    }



    private int generarID_Categoria(String Nombre_Categoria){
        return Math.abs(Nombre_Categoria.hashCode() % 100000);
    }

    private boolean validarCategoria(int ID_Categoria){
        ArrayList<String> columns = new ArrayList<>(Arrays.asList("ID_Categoria"));
        ResultSet rs =  op.Select("categorias", columns, "ID_Categoria = " + ID_Categoria);
        try {
            rs.next();
            if (rs.getInt(1) == ID_Categoria){
                Alerta("La categoría ya existe.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }catch (IOException e){
        }
        return true;
    }


    public int obtenerIDCategoria(String Nombre_Categoria){
        try {
            ResultSet rs = op.Select("categorias", new ArrayList<>(Arrays.asList("ID_Categoria")), "Nombre_Categoria = '" + Nombre_Categoria + "'");
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public String obtenerNombreCategoria(int ID_Categoria){
        try {
            ResultSet rs = op.Select("categorias", new ArrayList<>(Arrays.asList("Nombre_Categoria")),"ID_Categoria = " + ID_Categoria );
            rs.next();
            return rs.getString(1);
        }catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


    private void Alerta(String mensaje) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Administrador/Alerta.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AlertaController controlador = fxmlLoader.getController();
        controlador.setMensaje(mensaje);
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
