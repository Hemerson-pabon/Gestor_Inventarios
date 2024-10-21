package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.producto;
import com.gestor_inventarios.frontend.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class AdminController {

    @FXML
    private ChoiceBox<String> OpcionEyS;

    @FXML
    private ChoiceBox<String> chUndM;

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
        //OpcionEyS.getItems().addAll(" ", "Entrada","Salida");
        //OpcionEyS.setValue(" ");
        //
        ChCategoria.getItems().addAll("Categoria1");
        ChCategoria.setValue("Categoria1");
        ChUnidad.getItems().addAll("UND", "KG","GR");
        ChUnidad.setValue("UND");
        paneDefault.toFront();

        //
        FieldCodigo.setText("");
        // Eventos para modificar el valor de costo + IVA automaticamente
        FieldCostoB.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoB(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                LabelPrecio.setText(String.valueOf(p.calcularPrecio()));
            }catch (NullPointerException e){
                FieldCostoB.setText("0");
            }catch (NumberFormatException e){
                LabelCostoI.setText("");
            }
        }));
        FieldIVA.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoB(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                LabelPrecio.setText(String.valueOf(p.calcularPrecio()));
            }catch (NullPointerException e){
                FieldIVA.setText("0");
            }catch (NumberFormatException e){
                LabelCostoI.setText("");
            }
        }));
        FieldUtilidad.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoB(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                LabelPrecio.setText(String.valueOf(p.calcularPrecio()));
            }catch (NullPointerException e){
                FieldUtilidad.setText("0");
            }catch (NumberFormatException e){
                LabelPrecio.setText("");
            }
        }));

        FieldNombre.textProperty().addListener(observable -> {
            try {
                producto p = new producto();
                FieldCodigo.setText(String.valueOf(p.generarCodigo(FieldNombre.getText(), ChUnidad.getSelectionModel().getSelectedItem())));
            }catch (NullPointerException e){
                FieldCodigo.setText("");
            }
        });
        ChUnidad.setOnAction(actionEvent -> {
            try {
                producto p = new producto();
                FieldCodigo.setText(String.valueOf(p.generarCodigo(FieldNombre.getText(), ChUnidad.getSelectionModel().getSelectedItem())));
            }catch (NullPointerException e){
                FieldCodigo.setText("");
            }
        });





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



        if (Objects.equals(FieldNombre.getText(), "") && Objects.equals(ChUnidad.getSelectionModel().getSelectedItem() ,"")){
            FieldCodigo.setText("");
        }
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
    private TextField FieldCodigo;
    @FXML
    private TextArea AreaDescripcion;
    @FXML
    private ChoiceBox<String> ChUnidad;
    @FXML
    private ChoiceBox<String> ChCategoria;
    @FXML
    private TextField FieldCostoB;
    @FXML
    private TextField FieldIVA;
    @FXML
    private Label LabelCostoI;
    @FXML
    private TextField FieldUtilidad;
    @FXML
    private Label LabelPrecio;
    @FXML
    private DatePicker FechaVencimiento;



    // Evento de presionar el boton de crear producto
    @FXML
    protected void BotonCrearProductoClickeado(){
        try{
            String Nombre = FieldNombre.getText();
            String Descripcion = AreaDescripcion.getText();
            String Unidad = ChUnidad.getSelectionModel().getSelectedItem();
            String Categoria = ChCategoria.getSelectionModel().getSelectedItem();
            float costoB;
            if (Objects.equals(FieldCostoB.getText(), "")){
                costoB = 0;
            }else{
                costoB = Float.parseFloat(FieldCostoB.getText());
            }
            float IVA;
            if (Objects.equals(FieldIVA.getText(), "")){
                IVA = 0;
            }else {
                IVA = Float.parseFloat(FieldIVA.getText());
            }
            float Utilidad;
            if (Objects.equals(FieldUtilidad.getText(), "")){
                Utilidad = 0;
            }else {
                Utilidad = Float.parseFloat(FieldUtilidad.getText());
            }

            /*
            float costoB =  Float.parseFloat(FieldCostoB.getText());
            float IVA = Float.parseFloat(FieldIVA.getText());
            float Utilidad = Float.parseFloat(FieldUtilidad.getText());
            */
            // Obtener la fecha de vencimiento y cambiar el formato para ingresarla en la base de datos

            LocalDate FechaVencimientoSel = FechaVencimiento.getValue();
            String FechaVencimientoFor;
            if (FechaVencimientoSel == null){
                FechaVencimientoFor = "";
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                FechaVencimientoFor = FechaVencimientoSel.format(formatter);
            }
            producto p = new producto();
            boolean res = p.crearProducto(Nombre, Descripcion, Unidad, 1, 1,FechaVencimientoFor,costoB,IVA, Utilidad);
            if(res){
                try {
                    Alerta("Se agrego el producto.");
                    ResetearPaneCrearProducto();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                try{
                    Alerta("No se agrego el producto.");
                    ResetearPaneCrearProducto();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (NumberFormatException e){
            try {
                Alerta("Hay campos vacios, rellenelos y vuelva a intentar.");
                ResetearPaneCrearProducto();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }


    }

    // Resetar pane de creación de productos

    private void ResetearPaneCrearProducto(){
        FieldNombre.setText("");
        AreaDescripcion.setText("");
        FieldCodigo.setText("");
        ChUnidad.setValue("");
        ChCategoria.setValue("");
        FechaVencimiento.setValue(null);
        FieldCostoB.setText("");
        FieldIVA.setText("");
        LabelCostoI.setText("");
        FieldUtilidad.setText("");
        LabelPrecio.setText("");
        if (Objects.equals(FieldNombre.getText(), "") && Objects.equals(ChUnidad.getSelectionModel().getSelectedItem() ,"")){
            FieldCodigo.setText("");
        }
    }

    // Método para crear una alerta

    private void Alerta(String mensaje) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Alerta.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AlertaController controlador = fxmlLoader.getController();
        controlador.setMensaje(mensaje);
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
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


