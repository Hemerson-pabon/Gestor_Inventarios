package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.backend.categoria;
import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriasController {


    categoria ct = new categoria();

    @FXML
    private SplitMenuButton SplitCategorias;
    @FXML
    private TextField FieldEdCategorias;
    @FXML
    private TextField FieldNuevaCategoria;



    @FXML
    private void initialize(){
        AñadirCategoriasEx();


    }


    // Método para cargar las categorías existentes en la parte de edición de categorías
    private void AñadirCategoriasEx(){
        SplitCategorias.getItems().clear();
        SplitCategorias.setText("Categorias...");
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Nombre_Categoria");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("categorias", columns, "");
            while (rs.next()){
                if (rs.getString(1) != null){
                    MenuItem SplitCategoriasItem = new MenuItem(rs.getString(1));
                    SplitCategorias.getItems().add(SplitCategoriasItem);
                    SplitCategoriasItem.setOnAction(actionEvent -> {
                        SplitCategorias.setText(SplitCategoriasItem.getText());
                        FieldEdCategorias.setText(SplitCategoriasItem.getText());
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Evento del botón para crear una categoría

    @FXML
    protected void BtnCrearCategoriaClickeado() throws IOException{
        if (ct.crearCategoria(FieldNuevaCategoria.getText())){
            Alerta("Se creo la nueva categoría.");
            AñadirCategoriasEx();
            FieldNuevaCategoria.setText("");
        }else{
            Alerta("No se creo la categoría, revise lo datos e intente de nuevo.");
            FieldNuevaCategoria.setText("");
            AñadirCategoriasEx();
        }
    }

    // Evento con el botón para editar una categoría

    @FXML
    protected void BtnActualizarCatClickeado() throws IOException{
        if(ct.actualizarCategoria(SplitCategorias.getText(),FieldEdCategorias.getText())){
            Alerta("Se Actualizo la categoría.");
            FieldEdCategorias.setText("");
            AñadirCategoriasEx();
        }else{
            Alerta("No se actualizo la categoría, revise los datos e intente de nuevo.");
            FieldEdCategorias.setText("");
            AñadirCategoriasEx();
        }
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
