package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.*;
import com.gestor_inventarios.frontend.main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class NuevoUsuarioController {


    @FXML
    public void initialize() {
        empleado ep = new empleado();

        Nombre_Usuario.textProperty().addListener((observable) -> {
            ID_Empleado.setText(Integer.toString( ep.generarIDEmpleado(Nombre_Usuario.getText(), SplitPermisos.getText(), SplitSucursal.getText())));

        });
        SplitPermisos.setOnAction(event -> {
            ID_Empleado.setText(Integer.toString( ep.generarIDEmpleado(Nombre_Usuario.getText(), SplitPermisos.getText(), SplitSucursal.getText())));
        });
        SplitSucursal.setOnAction(event -> {
            ID_Empleado.setText(Integer.toString( ep.generarIDEmpleado(Nombre_Usuario.getText(), SplitPermisos.getText(), SplitSucursal.getText())));
        });
        SplitPermisosItem.setOnAction(event -> {
            SplitPermisos.setText(SplitPermisosItem.getText());
        });
        SplitPermisosItem1.setOnAction(event -> {
            SplitPermisos.setText(SplitPermisosItem1.getText());
        });

        AñadirSucursalesMenu();

    }


    @FXML
    private TextField Nombre_Usuario;
    @FXML
    private TextField contraseña;
    @FXML
    private TextField Nombre_Empleado;
    @FXML
    private TextField ID_Empleado;
    @FXML
    private SplitMenuButton SplitSucursal;
    @FXML
    private SplitMenuButton SplitPermisos;
    @FXML
    private MenuItem SplitPermisosItem;
    @FXML
    private MenuItem SplitPermisosItem1;



    @FXML
    protected void btnCrearUsuarioClick() throws IOException {
        String NombreUsuario = Nombre_Usuario.getText();
        String rawpass = contraseña.getText();
        String NombreEmpleado = Nombre_Empleado.getText();
        int IDEmpleado = Integer.parseInt(ID_Empleado.getText());
        String NombreSucursal = SplitSucursal.getText();
        String Permisos = SplitPermisos.getText();
        usuario us = new usuario();
        boolean b1 = us.crearUsuario(NombreUsuario, rawpass, Permisos, IDEmpleado);
        sucursal sc = new sucursal();
        empleado ep = new empleado();
        boolean b2 = ep.crearEmpleado(IDEmpleado, NombreEmpleado, sc.obtenerID_Sucursal(NombreSucursal));
        Alerta("Usuario Creado correctamente...");

    }

    private void AñadirSucursalesMenu() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Nombre_Sucursal");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("sucursales", columns, "");
            while (rs.next()){
                MenuItem SplitSucursalItem = new MenuItem(rs.getString("Nombre_Sucursal"));
                SplitSucursal.getItems().addAll(SplitSucursalItem);
                SplitSucursalItem.setOnAction(actionEvent -> {
                    SplitSucursal.setText(SplitSucursalItem.getText());
                });
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Método para crear una alerta

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
