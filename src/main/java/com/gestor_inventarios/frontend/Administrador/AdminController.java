package com.gestor_inventarios.frontend.Administrador;

import com.gestor_inventarios.backend.*;
import com.gestor_inventarios.frontend.main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private Pane pnlUsuarios;

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
    private SplitMenuButton SplitMenuSucursal;

    FXMLLoader fxmlLoaderInOut;
    InOutProductosController controladorInOut;


    // Método initialize de la ventana de admin

    @FXML
    private void initialize() {
        //OpcionEyS.getItems().addAll(" ", "Entrada","Salida");
        //OpcionEyS.setValue(" ");
        //
        ChUnidad.getItems().addAll("UND", "KG","GR");
        ChUnidad.setValue("UND");
        paneDefault.toFront();

        //
        FieldCodigo.setText("");
        // Eventos para modificar el valor de costo + IVA automáticamente en el panel de crear producto
        FieldCostoB.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                FieldPrecio.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldCostoB.setText("0");
            } catch (NumberFormatException e) {
                LabelCostoI.setText("");
            }
        }));

        FieldIVA.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                FieldPrecio.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldIVA.setText("0");
            } catch (NumberFormatException e) {
                LabelCostoI.setText("");
            }
        }));

        FieldUtilidad.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoB.getText()));
                p.setIVA(Float.parseFloat(FieldIVA.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidad.getText()));
                LabelCostoI.setText(String.valueOf(p.calcularCosto()));
                FieldPrecio.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldUtilidad.setText("0");
            } catch (NumberFormatException e) {
                FieldPrecio.setText("");
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

        AñadirCategoriasMenu(SplitCategoria);
        AñadirCategoriasMenu(SplitCategoriaEd);

        // Insertar las sucursales desde la base de datos
        AñadirSucursalesMenu();

        // Cargar el producto cuando se inserte el còdigo para editarlo
        FieldCodigoEd.textProperty().addListener(observable -> {
            Operaciones_SQL op = new Operaciones_SQL();
            ArrayList<String> columns = new ArrayList<>(Arrays.asList("ID_Categoria", "Fecha_Vencimiento", "Nombre", "Descripción", "UnidadMedida", "IVA", "CostoBase", "Utilidad", "Precio_Venta"));
            ResultSet rs = op.Select("productos", columns, "ID_Producto = " + FieldCodigoEd.getText());
            try {
                rs.next();
                FieldNombreEd.setText(rs.getString("Nombre"));
                AreaDescripcionEd.setText(rs.getString("Descripción"));
                ChUnidadEd.setValue(rs.getString("UnidadMedida"));
                categoria ca = new categoria();
                SplitCategoriaEd.setText(ca.obtenerNombreCategoria(rs.getInt("ID_Categoria")));
                FechaVencimientoEd.setValue(rs.getDate("Fecha_Vencimiento").toLocalDate());
                FieldCostoBEd.setText(rs.getString("CostoBase"));
                FieldIVAEd.setText(rs.getString("IVA"));
                FieldUtilidadEd.setText(rs.getString("Utilidad"));
                FieldPrecioEd.setText(rs.getString("Precio_Venta"));
            }catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
        FieldCostoBEd.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoBEd.getText()));
                p.setIVA(Float.parseFloat(FieldIVAEd.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidadEd.getText()));
                LabelCostoIEd.setText(String.valueOf(p.calcularCosto()));
                FieldPrecioEd.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldCostoBEd.setText("0");
            } catch (NumberFormatException e) {
                LabelCostoIEd.setText("");
            }
        }));

        FieldIVAEd.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoBEd.getText()));
                p.setIVA(Float.parseFloat(FieldIVAEd.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidadEd.getText()));
                LabelCostoIEd.setText(String.valueOf(p.calcularCosto()));
                FieldPrecioEd.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldIVA.setText("0");
            } catch (NumberFormatException e) {
                LabelCostoIEd.setText("");
            }
        }));

        FieldUtilidadEd.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                p.setCostoBase(Float.parseFloat(FieldCostoBEd.getText()));
                p.setIVA(Float.parseFloat(FieldIVAEd.getText()));
                p.setUtilidad(Float.parseFloat(FieldUtilidadEd.getText()));
                LabelCostoIEd.setText(String.valueOf(p.calcularCosto()));
                FieldPrecioEd.setText(String.valueOf(p.calcularPrecio()));
            } catch (NullPointerException e) {
                FieldUtilidadEd.setText("0");
            } catch (NumberFormatException e) {
                FieldPrecioEd.setText("");
            }
        }));


        // Initialize para el apartado de ventas
        DatePickerVentas.setOnAction(actionEvent -> {
            System.out.println("puto");
            cargarMovimientos(DatePickerVentas.getValue().toString());
        });





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

    // Eventos con los botones para mostrar los pane de cada sección

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
        cargarMovimientos(null);
    }

    @FXML
    public void botonUsuariosClickeado() {
        pnlUsuarios.toFront();
    }






     /*
     * ----------------------------------------------------
     * --------------- PANEL DE INICIO  -------------------
     * ----------------------------------------------------
     */





     /*
     * --------------------------------------------------------------
     * --------------- PANEL DE CREACIÓN DE PRODUCTOS ---------------
     * --------------------------------------------------------------
     */

    @FXML
    private TextField FieldNombre;
    @FXML
    private TextField FieldCodigo;
    @FXML
    private TextArea AreaDescripcion;
    @FXML
    private ChoiceBox<String> ChUnidad;
    @FXML
    private SplitMenuButton SplitCategoria;
    @FXML
    private TextField FieldCostoB;
    @FXML
    private TextField FieldIVA;
    @FXML
    private Label LabelCostoI;
    @FXML
    private TextField FieldUtilidad;
    @FXML
    private TextField FieldPrecio;
    @FXML
    private DatePicker FechaVencimiento;



    // Evento de presionar el boton de crear producto
    @FXML
    protected void BotonCrearProductoClickeado(){
        try{
            String Nombre = FieldNombre.getText();
            String Descripcion = AreaDescripcion.getText();
            String Unidad = ChUnidad.getSelectionModel().getSelectedItem();
            String Categoria = SplitCategoria.getText();
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
        SplitCategoria.setText("");
        FechaVencimiento.setValue(null);
        FieldCostoB.setText("");
        FieldIVA.setText("");
        LabelCostoI.setText("");
        FieldUtilidad.setText("");
        FieldPrecio.setText("");
        if (Objects.equals(FieldNombre.getText(), "") && Objects.equals(ChUnidad.getSelectionModel().getSelectedItem() ,"")){
            FieldCodigo.setText("");
        }
    }

    /*
     * --------------------------------------------------------------
     * --------------- PANEL DE EDICIÓN DE PRODUCTOS  ---------------
     * --------------------------------------------------------------
     */

    @FXML
    private TextField FieldNombreEd;
    @FXML
    private TextField FieldCodigoEd;
    @FXML
    private TextArea AreaDescripcionEd;
    @FXML
    private ChoiceBox<String> ChUnidadEd;
    @FXML
    private SplitMenuButton SplitCategoriaEd;
    @FXML
    private TextField FieldCostoBEd;
    @FXML
    private TextField FieldIVAEd;
    @FXML
    private Label LabelCostoIEd;
    @FXML
    private TextField FieldUtilidadEd;
    @FXML
    private TextField FieldPrecioEd;
    @FXML
    private DatePicker FechaVencimientoEd;


    // Evento del botón de edición de productos
    @FXML
    protected void BtnActualizarProdClick(){

        try{
            int ID_Producto = Integer.parseInt(FieldCodigoEd.getText());
            String Nombre = FieldNombreEd.getText();
            String Descripcion = AreaDescripcionEd.getText();
            String Unidad = ChUnidadEd.getSelectionModel().getSelectedItem();
            categoria ca = new categoria();
            int ID_Categoria = ca.obtenerIDCategoria(SplitCategoriaEd.getText());
            String Categoria = SplitCategoriaEd.getText();
            float costoB;
            if (Objects.equals(FieldCostoBEd.getText(), "")){
                costoB = 0;
            }else{
                costoB = Float.parseFloat(FieldCostoBEd.getText());
            }
            float IVA;
            if (Objects.equals(FieldIVAEd.getText(), "")){
                IVA = 0;
            }else {
                IVA = Float.parseFloat(FieldIVAEd.getText());
            }
            float Utilidad;
            if (Objects.equals(FieldUtilidadEd.getText(), "")){
                Utilidad = 0;
            }else {
                Utilidad = Float.parseFloat(FieldUtilidadEd.getText());
            }

            // Obtener la fecha de vencimiento y cambiar el formato para ingresarla en la base de datos
            LocalDate FechaVencimientoSel = FechaVencimientoEd.getValue();
            String FechaVencimientoFor;
            if (FechaVencimientoSel == null){
                FechaVencimientoFor = "";
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                FechaVencimientoFor = FechaVencimientoSel.format(formatter);
            }
            producto p = new producto();
            boolean res = p.actualizarProducto(ID_Producto, Nombre, Descripcion, Unidad, ID_Categoria, 1,FechaVencimientoFor,costoB,IVA, Utilidad);
            if(res){
                try {
                    Alerta("Se actualizo el producto.");
                    ResetearPaneCrearProducto();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                try{
                    Alerta("No se actualizo el producto.");
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

    private void AñadirCategoriasMenu(SplitMenuButton Split) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Nombre_Categoria");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("categorias", columns, "");
            while (rs.next()){
                if (!Objects.equals(rs.getString(1), null)){
                    MenuItem SplitItem = new MenuItem(rs.getString(1));
                    Split.getItems().add(SplitItem);
                    SplitItem.setOnAction(actionEvent -> {
                        Split.setText(SplitItem.getText());
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: ");
        }
    }

    @FXML
    private void BtnAgregarCategoriaClick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Administrador/Categorias.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }






    /*
    * ----------------------------------------------------
    * --------------- PANEL DE INVENTARIO  ---------------
    * ----------------------------------------------------
    */

    private int ID_Sucursal;
    private String Nombre_Sucursal;
    Parent rootInOut;

    @FXML
    private TableView<producto> tablaProductos;
    @FXML
    private TableColumn<producto, Integer> columnaID_Producto;
    @FXML
    private TableColumn<producto, String> columnaNombre;
    @FXML
    private TableColumn<producto, Float> columnaPrecioVenta;
    @FXML
    private TableColumn<producto, Integer> columnaStockMin;
    @FXML
    private TableColumn<producto, Integer> columnaStockAc;
    @FXML
    private TableColumn<producto, Integer> columnaStockMax;


    // Método para añadir las sucursales al splitmenubutton de sucursales

    private void AñadirSucursalesMenu() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Nombre_Sucursal");
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ResultSet rs = op.Select("sucursales", columns, "");
            while (rs.next()){
                MenuItem SplitSucursalItem = new MenuItem(rs.getString(1));
                SplitMenuSucursal.getItems().add(SplitSucursalItem);
                SplitSucursalItem.setOnAction(actionEvent -> {
                    SplitMenuSucursal.setText(SplitSucursalItem.getText());
                    // Crear un objeto de inventario para acceder a los productos de la sucursal
                    inventario iv = new inventario(SplitSucursalItem.getText());
                    ObservableList<producto> productos = iv.cargarInventario();
                    // Cargar el controlador de la ventana de entrada y salida de productos
                    try {
                        fxmlLoaderInOut = new FXMLLoader(main.class.getResource("Administrador/InOutProductos.fxml"));
                        rootInOut = fxmlLoaderInOut.load();
                        controladorInOut = fxmlLoaderInOut.getController();
                        Nombre_Sucursal = SplitSucursalItem.getText();
                        ID_Sucursal = iv.obtenerID();
                        controladorInOut.setNombre_Sucursal(SplitSucursalItem.getText());
                        controladorInOut.setID_Sucursal(iv.obtenerID());
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    // Cargar los productos en la tabla de inventario
                    columnaID_Producto.setCellValueFactory(new PropertyValueFactory<>("ID_Producto"));
                    columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                    columnaPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("Precio_Venta"));
                    columnaStockMin.setCellValueFactory(new PropertyValueFactory<>("Stock_Minimo"));
                    columnaStockAc.setCellValueFactory(new PropertyValueFactory<>("Stock_Actual"));
                    columnaStockMax.setCellValueFactory(new PropertyValueFactory<>("Stock_Maximo"));
                    tablaProductos.setItems(productos);
                });
            }
        } catch (SQLException e) {
            System.out.println("Error: ");
        }
    }


    // Evento para llamar a la ventana de Entrada y salida de productos

    @FXML
    private void BotonEnSaClickeado() throws IOException{
            fxmlLoaderInOut = new FXMLLoader(main.class.getResource("Administrador/InOutProductos.fxml"));
            rootInOut = fxmlLoaderInOut.load();
            controladorInOut = fxmlLoaderInOut.getController();
            Scene scene = new Scene(rootInOut);
            controladorInOut.setID_Sucursal(ID_Sucursal);
            controladorInOut.setNombre_Sucursal(Nombre_Sucursal);
            Stage stage = new Stage();
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();

    }

     /*
     * ----------------------------------------------------
     * --------------- PANEL DE VENTAS  -------------------
     * ----------------------------------------------------
     */

    @FXML
    private DatePicker DatePickerVentas;

    @FXML
    private TableView<movimiento> tablaVentas;
    @FXML
    private TableColumn<movimiento, String> columnFechaMovimiento;
    @FXML
    private TableColumn<movimiento, String> columnHoraMovimiento;
    @FXML
    private TableColumn<movimiento, String> columnProducto;
    @FXML
    private TableColumn<movimiento, Integer> columnCantidad;
    @FXML
    private TableColumn<movimiento, String> columnTipoMovimiento;


    private void cargarMovimientos(String Fecha_Movimiento){
        movimiento mv = new movimiento();
        ArrayList<Integer> ids = mv.obtenerIDsM(Fecha_Movimiento);
        ObservableList<movimiento> movimientos = FXCollections.observableArrayList();
        for (int id: ids){
            movimiento m = new movimiento();
            movimientos.add(m.cargarMovimiento(id));
        }
        columnFechaMovimiento.setCellValueFactory(new PropertyValueFactory<>("Fecha_Movimiento"));
        columnHoraMovimiento.setCellValueFactory(new PropertyValueFactory<>("Hora_Movimiento"));
        columnProducto.setCellValueFactory(new PropertyValueFactory<>("ID_Producto"));
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
        columnTipoMovimiento.setCellValueFactory(new PropertyValueFactory<>("Tipo_Movimiento"));
        tablaVentas.setItems(movimientos);
    }







}
