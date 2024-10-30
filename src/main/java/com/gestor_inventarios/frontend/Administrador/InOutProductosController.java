package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.backend.inventario;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class InOutProductosController{
    private int ID_Sucursal;
    private String Nombre_Sucursal;

    Operaciones_SQL op = new Operaciones_SQL();

    private int stock;
    private int cantidad;
    private int NuevoStock;

    public void setNombre_Sucursal(String Nombre_Sucursal) {
        this.Nombre_Sucursal = Nombre_Sucursal;
        LabelNombreSucursal.setText(Nombre_Sucursal);
    }

    public void setID_Sucursal(int ID_Sucursal) {
        this.ID_Sucursal = ID_Sucursal;
    }



    @FXML
    private void initialize(){
        // Agregar las opciones al menú de tipo de movimiento
        MenuItem MenuTipoIn = new MenuItem("Entrada");
        MenuItem MenuTipoOut = new MenuItem("Salida");
        MenuTipoMovimiento.getItems().addAll(MenuTipoIn,MenuTipoOut);
        // Eventos para cuando haya selección en el menu tipo de movimiento
        MenuTipoIn.setOnAction(actionEvent -> {
            MenuTipoMovimiento.setText(MenuTipoIn.getText());
            try {
                int Nuevo_Stock = Integer.parseInt(LabelStock.getText()) + Integer.parseInt(FieldCantidad.getText());
                LabelNuevoStock.setText(String.valueOf(Nuevo_Stock));
            }catch (NullPointerException | NumberFormatException e){
            }
        });
        MenuTipoOut.setOnAction(actionEvent -> {
            MenuTipoMovimiento.setText(MenuTipoOut.getText());
            try {
                int Nuevo_Stock = Integer.parseInt(LabelStock.getText()) - Integer.parseInt(FieldCantidad.getText());
                LabelNuevoStock.setText(String.valueOf(Nuevo_Stock));
            }catch (NullPointerException | NumberFormatException e){
            }

        });
        // Método para actualizar los labels de Nombre y stock al ingresar el ID
        FieldIDProducto.setOnKeyReleased(actionEvent -> {
            Operaciones_SQL op = new Operaciones_SQL();
            ArrayList<String> columnsProducto = new ArrayList<>();
            columnsProducto.add("Nombre");
            ArrayList<String> columnsInventario = new ArrayList<>();
            columnsInventario.add("Stock_Actual");
            ResultSet rs0 = op.Select("productos", columnsProducto, "ID_Producto = " + FieldIDProducto.getText());
            ResultSet rs1 = op.Select("inventario", columnsInventario, "ID_Producto = " + FieldIDProducto.getText() + " AND ID_Sucursal = " + ID_Sucursal);
            try {
                rs0.next();
                rs1.next();
                LabelNombreProducto.setText(rs0.getString("Nombre"));
                LabelStock.setText(String.valueOf(rs1.getInt("Stock_Actual")));
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
        // Método para actualizar el label de nuevo stock al ingresar la cantidad
        FieldCantidad.setOnKeyReleased(actionEvent -> {
            int cantidad;
            int Nuevo_Stock = 0;
            try {
                FieldCantidad.setStyle("-fx-border-color: transparent;");
                cantidad = Integer.parseInt(FieldCantidad.getText());
            }catch (NumberFormatException e){
                cantidad = 0;
                FieldCantidad.setStyle("-fx-border-color: red;");
            }
            catch (NullPointerException e) {
                cantidad = 0;
            }
            Nuevo_Stock = switch (MenuTipoMovimiento.getText()) {
                case "Entrada" -> Integer.parseInt(LabelStock.getText()) + cantidad;
                case "Salida" -> Integer.parseInt(LabelStock.getText()) - cantidad;
                default -> Integer.parseInt(LabelStock.getText());
            };
            LabelNuevoStock.setText(String.valueOf(Nuevo_Stock));
        });
    }

    @FXML
    protected void Ingresarmovimiento(){
        inventario iv = new inventario(Nombre_Sucursal);
        iv.actualizarInventario(MenuTipoMovimiento.getText(), Double.parseDouble(FieldIDProducto.getText()), Integer.parseInt(FieldCantidad.getText()));
        Stage stage = (Stage) BotonIngresarMovimiento.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button BotonIngresarMovimiento;
    @FXML
    private SplitMenuButton MenuTipoMovimiento;
    @FXML
    private TextField FieldIDProducto;
    @FXML
    private Label LabelNombreProducto;
    @FXML
    private TextField FieldCantidad;
    @FXML
    private Label LabelStock;
    @FXML
    private Label LabelNuevoStock;
    @FXML
    private Label LabelNombreSucursal;




}
