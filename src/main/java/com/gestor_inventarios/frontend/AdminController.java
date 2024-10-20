package com.gestor_inventarios.frontend;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;



public class AdminController {

    @FXML
    private ChoiceBox<String> OpcionEyS;

    @FXML
    private ChoiceBox<String> chUndM;

    @FXML
    private ChoiceBox<String> chUndM1;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEdicion;

    @FXML
    private Button btnEntrada;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnVentas;

    @FXML
    private Pane paneDefault;

    @FXML
    private Pane pnlEdicion;

    @FXML
    private Pane pnlEntrada;

    @FXML
    private Pane pnlCrear;

    @FXML
    private Pane pnlInventario;

    @FXML
    private Pane pnlVentas;

    @FXML
    private void initialize() {
        OpcionEyS.getItems().addAll(" ", "Entrada","Salida");
        OpcionEyS.setValue(" ");
        chUndM.getItems().addAll("UND", "KG","GR");
        chUndM.setValue("UND");
        chUndM1.getItems().addAll("UND", "KG","GR");
        chUndM1.setValue("UND");
        paneDefault.toFront();
    }

    @FXML
    private void botonHomeClickeado() {
        paneDefault.toFront();
    }
    @FXML
    private void botonEntradaClickeado() {
        pnlEntrada.toFront();
    }

    @FXML
    public void botonEdicionClickeado() {
        pnlEdicion.toFront();
    }

    @FXML
    public void botonCrearClickeado() {
        pnlCrear.toFront();
    }

    @FXML
    public void botonInventarioClickeado() {
        pnlInventario.toFront();
    }

    @FXML
    public void botonVentasClickeado() {
        pnlVentas.toFront();
    }

    @FXML
    public void botonUsuariosClickeado() {

    }


    // Interfaz de inventario

    /*
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, String> columnaDescripcion;
    @FXML
    private TableColumn<Producto, Float> columnaPrecio;


    @FXML
    private void botonsucursal(){
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        productos.add(new Producto("producto1", "descripción del producto 1", 2345));
        productos.add(new Producto("producto2", "descripción del producto 2", 2234));

        columnaNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("Nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("Descripcion"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Float>("Precio"));

        tablaProductos.setItems(productos);

    }

     */

}


