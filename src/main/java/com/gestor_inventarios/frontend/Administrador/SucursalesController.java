package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.backend.inventario;
import com.gestor_inventarios.backend.producto;
import com.gestor_inventarios.backend.sucursal;
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
import java.util.Arrays;

public class SucursalesController {

    @FXML
    public void initialize() {
        AñadirSucursalesMenu();
    }

    @FXML
    private TextField FieldNuevaCategoria;
    @FXML
    private TextField FieldDireccion;
    @FXML
    private TextField FieldIDEncargado;
    @FXML
    private SplitMenuButton SplitSucursales;
    @FXML
    private TextField FieldEdSucursales;
    @FXML
    private TextField FieldEdEncargado;

    @FXML
    protected void BtnCrearSucursalClickeado() throws IOException {
        sucursal suc = new sucursal();
        if (suc.crearSucursal(suc.generarIDSucursal(FieldNuevaCategoria.getText()),FieldDireccion.getText(),Integer.parseInt(FieldIDEncargado.getText()), FieldNuevaCategoria.getText())){
            Alerta("Se creo la nueva sucursal.");
        }else {
            Alerta("No se creo la sucursal");
        }
    }

    @FXML
    protected void BtnActualizarSucursalClickeado() throws IOException {
        Operaciones_SQL op = new Operaciones_SQL();
        ArrayList<Object> values = new ArrayList<>();
        values.add(Integer.parseInt(FieldIDEncargado.getText()));
        values.add(FieldEdSucursales.getText());
        if (op.Update("sucursales", new ArrayList<>(Arrays.asList("ID_Empleado", "Nombre_Sucursal")), values, "Nombre_Sucursal = '" + SplitSucursales.getText() + "'") >= 1){
            Alerta("Se actualizo la sucursal.");
        }else {
            Alerta("No se actualizo la sucursal.");
        }
    }


    private void AñadirSucursalesMenu() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Nombre_Sucursal");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("sucursales", columns, "");
            while (rs.next()){
                MenuItem SplitSucursalItem = new MenuItem(rs.getString(1));
                SplitSucursales.getItems().add(SplitSucursalItem);
                SplitSucursalItem.setOnAction(actionEvent -> {
                    SplitSucursales.setText(SplitSucursalItem.getText());
                    FieldEdSucursales.setText(SplitSucursalItem.getText());
                    ResultSet rs1 = op.Select("sucursales", new ArrayList<>(Arrays.asList("ID_Empleado")),"Nombre_Sucursal = '" + SplitSucursalItem.getText() + "'" );
                    try {
                        rs1.next();
                        FieldEdEncargado.setText(rs1.getString(1));
                    } catch (SQLException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println("Error: ");
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
