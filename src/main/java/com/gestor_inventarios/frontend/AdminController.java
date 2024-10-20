package com.gestor_inventarios.frontend;

import com.gestor_inventarios.backend.producto;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;


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

    // Panel de creación de productos

    @FXML
    private TextField FieldNombre;
    @FXML
    private TextArea AreaDescripcion;
    @FXML
    private ChoiceBox<String> ChUnidad;
    @FXML
    private ChoiceBox<String> ChCategoria;
    @FXML
    private TextField FieldCosto;
    @FXML
    private TextField FieldIVA;
    @FXML
    private Label LabelCostoI;
    @FXML
    private TextField FieldUtilidad;
    @FXML
    private Label LabelPrecio;

    // Evento de presionar el boton de crear producto
    @FXML
    protected void BotonCrearProductoClickeado(){
        String Nombre = FieldNombre.getText();
        String Descripcion = AreaDescripcion.getText();
        String Unidad = ChUnidad.getSelectionModel().getSelectedItem();
        String Categoria = ChCategoria.getSelectionModel().getSelectedItem();
        float costo = Integer.parseInt(FieldCosto.getText());
        FieldIVA.getText();
        FieldUtilidad.getText();

        producto p = new producto();


    }













    // Panel de inventario

    @FXML
    private TableView<producto> tablaProductos;
    @FXML
    private TableColumn<producto, Integer> columnaID_Producto;
    @FXML
    private TableColumn<producto, String> columnaNombre;
    @FXML
    private TableColumn<producto, Float> columnaPrecioVenta;
    @FXML
    private TableColumn<producto, String> columnaUnidad;
    @FXML
    private TableColumn<producto, Float> columnaCosto;
    @FXML
    private TableColumn<producto, Float> columnaUtilidad;
    @FXML
    private TableColumn<producto, String> columnaFechaVencimiento;
    @FXML
    private TableColumn<producto, String> columnaDescripcion;


    @FXML
    private void botonsucursal(){
        ObservableList<producto> productos = FXCollections.observableArrayList();

        int[] ids = {27265, 27266, 27267, 27268, 27269, 27264, 27263, 27262, 27261, 27260, 24618, 24619, 24620,24621,24622,24623,24624, 24625, 24626,24627,24649,24650,24651,24652,24653,24654,24655,24655,24657};

        for (int id : ids) {
            productos.add(new producto().cargarProducto(id));
        }

        columnaID_Producto.setCellValueFactory(new PropertyValueFactory<>("ID_Producto"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("PrecioVenta"));
        columnaUnidad.setCellValueFactory(new PropertyValueFactory<>("UnidadMedida"));
        columnaCosto.setCellValueFactory(new PropertyValueFactory<>("CostoB"));
        columnaUtilidad.setCellValueFactory(new PropertyValueFactory<>("Utilidad"));
        columnaFechaVencimiento.setCellValueFactory(new PropertyValueFactory<>("FechaVencimiento"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        tablaProductos.setItems(productos);

    }



}


