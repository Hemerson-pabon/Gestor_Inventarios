package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.frontend.main;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class EmpleadoController {
    //Declaraciones de objetos

    //Home
    @FXML
    private Button homeBtn;
    @FXML
    private Pane paneDefault;

    //Gastos de caja
    @FXML
    private Pane gastosPanel;
    @FXML
    private Label totalGastosDia;
    @FXML
    private Label nombVendedorGastos;
    @FXML
    private TextField valorGastoField;
    @FXML
    private TextField motivoGastoField;
    @FXML
    private Button ingresoGastoButton;
    @FXML
    private Button gastosButton;

    //Cierre de caja
    @FXML
    private Pane cierreCajaPanel;
    @FXML
    private Label nombVendedorCierre;
    @FXML
    private Label nequiTotalLabel;
    @FXML
    private Label daviplataTotalLabel;
    @FXML
    private Label datafonoTotalLabel;
    @FXML
    private Button insertIngresosButton;
    @FXML
    private Button cierreCajaButton;

    //Devolucion
    @FXML
    private TableView tablaDevolucion;
    @FXML
    private TableColumn<ProductoDevo, String> colCodigo;
    @FXML
    private TableColumn<ProductoDevo, String> colNombre;
    @FXML
    private TableColumn<ProductoDevo, Integer> colCantidad;
    @FXML
    private TableColumn<ProductoDevo, Double> colPrecioUnd;
    @FXML
    private TableColumn<ProductoDevo, Double> colPrecioTotal;

    @FXML
    private Pane devolucionPanel;
    @FXML
    private Label nombVendedorDevol;
    @FXML
    private Label totalPrecioDevol;
    @FXML
    private Label totalDevolucion;
    @FXML
    private Label precioUnitDevol;
    @FXML
    private Label nombreProdDevol;
    @FXML
    private Button devolverButton;
    @FXML
    private Button nextProductDevolButton;
    @FXML
    private Button editarFactDevolButton;
    @FXML
    private TextField cantProductoDevolucionField;
    @FXML
    private TextField codigoProductoDevolucion;
    @FXML
    private Button devolucionButton;

    //Mostrador de ventas
    @FXML
    private TableView tablaVentas;
    @FXML
    private TableColumn<ProductoVentas, String> colCodigoVentas;
    @FXML
    private TableColumn<ProductoVentas, String> colNombreVentas;
    @FXML
    private TableColumn<ProductoVentas, Integer> colCantidadVentas;
    @FXML
    private TableColumn<ProductoVentas, Double> colPrecioUndVentas;
    @FXML
    private TableColumn<ProductoVentas, Double> colPrecioTotalVentas;
    @FXML
    private Pane mostradorPanel;
    @FXML
    private Label nombVendedor;
    @FXML
    private Label totalPrecioMostrador;
    @FXML
    private Label precioUnitMostrador;
    @FXML
    private Label nombreProdMostrador;
    @FXML
    private Label totalVentaMostrador;
    @FXML
    private TextField cantProductoField;
    @FXML
    private TextField codigoProdMostrador;
    @FXML
    private Button facturarButton;
    @FXML
    private Button editarFacturaButton;
    @FXML
    private Button nextProductoButton;
    @FXML
    private Button mostradorButton;

    private ObservableList<ProductoDevo> listaProductoDevos = FXCollections.observableArrayList();

    private ObservableList<ProductoVentas> listaProductoVentas = FXCollections.observableArrayList();

    //Variables de instancias
    int precioProduct = 0;
    int totalVenta = 0;
    int resultado = 0;

    // Metodo de inicialización
    @FXML
    private void initialize() {
        //obtener valores en el Field del codigo del mostrador
        codigoProdMostrador.textProperty().addListener((observable -> {
            try {
                Operaciones_SQL op = new Operaciones_SQL();
                ArrayList<String> columns = new ArrayList<>();
                columns.add("Descripción");
                columns.add("Precio_Venta");
                ResultSet res = op.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProdMostrador.getText()));
                res.next();
                System.out.println("Nombre: " + res.getString(1));
                System.out.println("Precio: " + res.getString(2));
                precioProduct = res.getInt(2);
            } catch (SQLException | NullPointerException ex) {
                codigoProdMostrador.setText("0");
            } catch (NumberFormatException e) {
                precioUnitMostrador.setText("");
            }
        }));
        //aca valida si  el Field de cantidad es llenado con un numero, aun no puedo hacer que el numero sea solo positivo
        try {
            int valorCantProduct = Integer.parseInt(cantProductoField.getText());
            resultado = valorCantProduct * precioProduct;
        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado debe ser un numero.");
        } finally {
            totalVenta += resultado;
            //totalPrecioMostrador.setText(String.valueOf(resultado));
            totalVentaMostrador.setText(String.valueOf(totalVenta));
        }


        //Tabla Ventas
        //Enlaza cada columna con el atributo que corresponde
        colCodigoVentas.setCellValueFactory(new PropertyValueFactory("codigoVenta"));
        colNombreVentas.setCellValueFactory(new PropertyValueFactory("nombreVenta"));
        colCantidadVentas.setCellValueFactory(new PropertyValueFactory("cantidadVenta"));
        colPrecioUndVentas.setCellValueFactory(new PropertyValueFactory("precioVentaUnd"));
        colPrecioTotalVentas.setCellValueFactory(cellData -> {
            ProductoVentas productoVentas = cellData.getValue();
            double total = productoVentas.getCantidadVentas() * productoVentas.getPrecioVentaUnd();
            return new SimpleDoubleProperty(total).asObject();
        });

        //Asocia la lista con la tabla
        tablaVentas.setItems(listaProductoVentas);
        //Tabla Devolucion
        //Enlaza cada columna con el atributo que corresponde
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnd.setCellValueFactory(new PropertyValueFactory<>("precioUnd"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioTotal.setCellValueFactory(cellData ->{
            ProductoDevo productoDevo = cellData.getValue();
            double precioTotal = productoDevo.getCantidad() * productoDevo.getPrecioUnd();
            return new SimpleDoubleProperty(precioTotal).asObject();
        });

        //Asocia la lista con la tabla
        tablaDevolucion.setItems(listaProductoDevos);

        //Actualiza el total de devolucion
         listaProductoDevos.addListener((ListChangeListener<? super ProductoDevo>) c -> {
             double totalDevo = totalPrecioDevolucion();
             totalDevolucion.setText("$ " + String.format("%.2f", totalDevo));
         });
    }




    //Metodos

        //Ventana Home
    @FXML
    public void buttonHomeClickeado(){
        paneDefault.toFront();
    }

        //Mostrador de venta
    @FXML
    public void buttonMostradorInterfazClickeado() {
        //en este metodo activa el panel de mostrador
        // y envia ese panel al frente
        mostradorPanel.toFront();
        mostradorPanel.setVisible(true);
    }

    @FXML
    public void buttonSiguienteMostradorClickeado() {
        //Tomar los datos
        String code = codigoProdMostrador.getText();
        String name = nombreProdMostrador.getText();
        int cant = Integer.parseInt(cantProductoField.getText());
        double precioUnd = Double.parseDouble(codigoProdMostrador.getText());

        //Crea un nuevo producto
        ProductoVentas productoVentas = new ProductoVentas(name, code, cant, precioUnd);
        //Vacia los campos para escribir
        codigoProdMostrador.clear();
        nombreProdMostrador.setText(" ");
        cantProductoField.clear();
        precioUnitMostrador.setText("0");
    }

    @FXML
    public void buttonEdicionMostradorClickeado() {
        /*En este metodo hay que hacer una tabla donde se puedan eliminar productos
          que el cliente ya no desee*/

    }
    @FXML
    public void buttonFacturarClickeado() throws IOException {
        /*Aca debe se debe hacer un metodo para abrir el CambioView.fxml,
          ademas que envie losa datos del total de venta a las label de ahi*/
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/CambioView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Cambio");
            stage.show();
    }

        //Devolucion de producto
    @FXML
    public void buttonDevolucionInterfazClickeado(){
        //en este metodo activa el panel de devolucion
        // y envia ese panel al frente
        devolucionPanel.toFront();
        devolucionPanel.setVisible(true);
    }
    @FXML
    public  void buttonSiguienteDevolClickeado(){
        // Tomar los datos
        String codigo = codigoProductoDevolucion.getText();
        String nombre = nombreProdDevol.getText();
        int cantidad = Integer.parseInt(cantProductoDevolucionField.getText());
        double precioUnd = Double.parseDouble(precioUnitDevol.getText());


        // Crea un nuevo producto para añadirlo
        ProductoDevo productoDevo = new ProductoDevo(codigo, nombre, cantidad, precioUnd);
        listaProductoDevos.add(productoDevo);
        //Vacía los campos para escribir
        codigoProductoDevolucion.clear();
        cantProductoDevolucionField.clear();
        nombreProdDevol.setText("nombreProdDevol");
        precioUnitDevol.setText("1250");
    }
    //Calcular el Total de devolucion
    @FXML
    public Double totalPrecioDevolucion(){
        Double totalDevolucion = 0.0;
        for(ProductoDevo productoDevo : listaProductoDevos){
            totalDevolucion += productoDevo.getCantidad() * productoDevo.getPrecioUnd();
        }
        return totalDevolucion;
    }
    @FXML
    public void buttonEdicionDevolClickeado(){
        /*En este metodo hay que hacer una tabla donde se puedan eliminar productos
          que el cliente ya no desee*/
    }
    @FXML
    public void buttonDevolucionClickeado(){
        /*En este metodo se debe hacer que se devuelva al inventario los productos
        * puestos a devolucion ademas de restarlo en los ingresos de ese mismo dia*/
    }

        //Cierre de caja
        @FXML
    public void buttonCierreInterfazClickeado(){
        /*en este metodo activa el panel de cierre
         de caja y envia ese panel al frente*/
            /*En este metodo apenas se abra debe enviarse la informacion
            * de los movimientos en caja sobre los pagos de nequi, daviplata y
            * datafono, registrados en las ventas*/
        cierreCajaPanel.toFront();
        cierreCajaPanel.setVisible(true);
    }
    @FXML
    public void buttonCierreClickeado(){
        /*En este metodo sirve para enviar toda la informacion de los
        * ingresos en caja del dia a la base de datos*/
    }

        //Gastos de caja
        @FXML
    public void buttonGastosInterfazClickeado(){
        //en este metodo activa el panel de gastos
            // y envia ese panel al frente
        gastosPanel.toFront();
        gastosPanel.setVisible(true);
    }
    @FXML
    public void buttonGastosClickeado(){


    }



}
