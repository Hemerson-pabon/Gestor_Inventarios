package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.frontend.main;
import javafx.beans.property.SimpleDoubleProperty;
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
import java.util.List;

public class EmpleadoController {
    //Declaraciones de objetos

    //Home
    @FXML
    private Button homeBtn;
    @FXML
    private Pane paneDefault;

    //Gastos de caja
    @FXML
    private TableView tablaGastosDiarios;
    @FXML
    private TableColumn<String, Gastos> colMotivo;
    @FXML
    private TableColumn<Double, Gastos> colValorGastos;
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
    protected Label totalPrecioMostrador;
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

    private ObservableList<Gastos> listaGastos = FXCollections.observableArrayList();


    public String getTotalPrecioMostradorText() {
        return totalPrecioMostrador.getText();
    }



    //Variables de instancias
    int precioProduct = 0;
    int totalVenta = 0;
    int resultado = 0;

    // Metodo de inicialización
    @FXML
    private void initialize() throws IOException {
        //Home por default
        paneDefault.setVisible(true);
        paneDefault.toFront();
        //obtener valores en el Field del codigo del mostrador
        /*codigoProdMostrador.textProperty().addListener(observable -> {
            try {
                Operaciones_SQL op = new Operaciones_SQL();
                ArrayList<String> columns = new ArrayList<>();
                columns.add("Descripción");
                columns.add("Precio_Venta");
                ResultSet res = op.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProdMostrador.getText()));
                res.next();
                nombreProdMostrador.setText(res.getString("Descripción"));
                precioUnitMostrador.setText(res.getString("Precio_Venta"));
                int valorCantProduct = Integer.parseInt(cantProductoField.getText());
                totalPrecioMostrador.setText(String.valueOf(valorCantProduct * res.getInt(2)));
                precioProduct = res.getInt(2);
            } catch (SQLException | NullPointerException ex) {
                //codigoProdMostrador.setText("0");

            } catch (NumberFormatException e) {
                // precioUnitMostrador.setText("");
            }
        });*/
        /*codigoProductoDevolucion.textProperty().addListener(observable -> {
            try {
                Operaciones_SQL op = new Operaciones_SQL();
                ArrayList<String> columns = new ArrayList<>();
                columns.add("Descripción");
                columns.add("Precio_Venta");
                ResultSet res = op.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProdMostrador.getText()));
                res.next();
                nombreProdDevol.setText(res.getString("Descripción"));
                precioUnitDevol.setText(res.getString("Precio_Venta"));
                int valorCantProduct = Integer.parseInt(cantProductoDevolucionField.getText());
                totalPrecioDevol.setText(String.valueOf(valorCantProduct * res.getInt(2)));
                //precioProduct = res.getInt(2);
            } catch (SQLException | NullPointerException ex) {
                codigoProductoDevolucion.setText("0");

            } catch (NumberFormatException e) {
                // precioUnitMostrador.setText("");
            }
        });*/



        // Evento para cuando se ingrese la cantidad, calcular el valor
        //aca valida si  el Field de cantidad es llenado con un numero, aun no puedo hacer que el numero sea solo positivo



        //Tabla Ventas
        //Enlaza cada columna con el atributo que corresponde
        colCodigoVentas.setCellValueFactory(new PropertyValueFactory("codigoVenta"));
        colNombreVentas.setCellValueFactory(new PropertyValueFactory("nombreVenta"));
        colCantidadVentas.setCellValueFactory(new PropertyValueFactory("cantidadVentas"));
        colPrecioUndVentas.setCellValueFactory(new PropertyValueFactory("precioVentaUnd"));
        colPrecioTotalVentas.setCellValueFactory(cellData -> {
            ProductoVentas productoVentas = cellData.getValue();
            double total = productoVentas.getCantidadVentas() * productoVentas.getPrecioVentaUnd();
            return new SimpleDoubleProperty(total).asObject();
        });

        //Asocia la lista con la tabla
        tablaVentas.setItems(listaProductoVentas);

        //Actializa el total de venta
        listaProductoVentas.addListener((ListChangeListener<? super ProductoVentas>) c -> {
            double totalVentas = totalPrecioVentas();
            totalVentaMostrador.setText("$ " + String.format("%.2f", totalVentas));
        });

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

         //Tabla Gastos
         colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
         colValorGastos.setCellValueFactory(new PropertyValueFactory<>("valorGasto"));

         tablaGastosDiarios.setItems(listaGastos);

         listaGastos.addListener((ListChangeListener<? super Gastos>) c -> {
             double total = totalGastosDia();
             totalGastosDia.setText("$ " + String.format("%.2f", total));
         });
    }




    //Metodos

        //Ventana Home
    @FXML
    public void buttonHomeClickeado(){
        paneDefault.setVisible(true);
        paneDefault.toFront();
    }

        //Mostrador de venta
    @FXML
    public void buttonMostradorInterfazClickeado() {
        //en este metodo activa el panel de mostrador
        // y envia ese panel al frente
        mostradorPanel.toFront();
        mostradorPanel.setVisible(true);
        codigoProdMostrador.requestFocus();
        //Hacer que vaya al siguiente textfield al presionar enter
        codigoProdMostrador.setOnAction(event -> {
            cantProductoField.setText("1");
            cantProductoField.requestFocus();
        try {
            Operaciones_SQL op = new Operaciones_SQL();
            ArrayList<String> columns = new ArrayList<>();
            columns.add("Nombre");
            columns.add("Precio_Venta");
            ResultSet res = op.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProdMostrador.getText()));
            res.next();
            nombreProdMostrador.setText(res.getString("Nombre"));
            precioUnitMostrador.setText(res.getString("Precio_Venta"));
            int valorCantProduct = Integer.parseInt(cantProductoField.getText());
            totalPrecioMostrador.setText(String.valueOf(valorCantProduct * res.getInt(2)));
            precioProduct = res.getInt(2);
        } catch (SQLException | NullPointerException ex) {
            //codigoProdMostrador.setText("0");

        } catch (NumberFormatException e) {
            // precioUnitMostrador.setText("");
        }
        });
        cantProductoField.setOnAction(event -> {
            nextProductoButton.setStyle("-fx-background-color: #00254e;");
            nextProductoButton.requestFocus();});
    }




    @FXML
    public void buttonSiguienteMostradorClickeado() {
        nextProductoButton.setStyle("-fx-background-color: transparent;");
        codigoProdMostrador.requestFocus();
        //Tomar los datos
        String code = codigoProdMostrador.getText();
        String name = nombreProdMostrador.getText();
        int cant = Integer.parseInt(cantProductoField.getText());
        double precioUnd = Double.parseDouble(precioUnitMostrador.getText());

        //Crea un nuevo producto
        ProductoVentas productoVentas = new ProductoVentas(code, name, cant, precioUnd);
        listaProductoVentas.add(productoVentas);
        //Vacia los campos para escribir
        codigoProdMostrador.clear();
        nombreProdMostrador.setText(" ");
        cantProductoField.clear();
        precioUnitMostrador.setText("0");

    }
    //Calucla el total Venta
    @FXML
    public Double totalPrecioVentas(){
        Double totalVentas = 0.0;
        for(ProductoVentas productoVentas : listaProductoVentas){
            totalVentas += productoVentas.getCantidadVentas()*productoVentas.getPrecioVentaUnd();
        }
        return totalVentas;
    }

    @FXML
    public void buttonEdicionMostradorClickeado() {
        /*En este metodo hay que hacer una tabla donde se puedan eliminar productos
          que el cliente ya no desee*/
    }
    @FXML
    public void buttonFacturarClickeado() throws IOException {
        /*Aca debe se debe hacer un metodo para abrir el CambioView.fxml,
          ademas que envie los datos del total de venta a las label de ahi
          despues de esto cierre la ventana dependiendo si se oprimio el boton
          de facturar en el cambio.fxml*/

        //aca abre la ventana de cambioView
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
        //hacer que vaya al siguiente text field al presionar enter
        codigoProductoDevolucion.requestFocus();
        codigoProductoDevolucion.setOnAction(event -> {
            cantProductoDevolucionField.setText("1");
            cantProductoDevolucionField.requestFocus();
            try {
                Operaciones_SQL op = new Operaciones_SQL();
                ArrayList<String> columns = new ArrayList<>();
                columns.add("Nombre");
                columns.add("Precio_Venta");
                ResultSet res = op.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProductoDevolucion.getText()));
                res.next();
                nombreProdDevol.setText(res.getString("Nombre"));
                precioUnitDevol.setText(res.getString("Precio_Venta"));
                int valorCantProduct = Integer.parseInt(cantProductoDevolucionField.getText());
                //totalPrecioDevol.setText(String.valueOf(valorCantProduct * res.getInt(2)));
                //precioProduct = res.getInt(2);
            } catch (SQLException | NullPointerException ex) {
                codigoProductoDevolucion.setText("0");

            } catch (NumberFormatException e) {
                // precioUnitMostrador.setText("");
            }
        });
        cantProductoDevolucionField.setOnAction(event -> {
            nextProductDevolButton.setStyle("-fx-background-color: #00254e;");
            nextProductDevolButton.requestFocus();});
    }
    @FXML
    public  void buttonSiguienteDevolClickeado(){
        codigoProductoDevolucion.requestFocus();
        nextProductDevolButton.setStyle("-fx-background-color: transparent;");
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
    protected double value = totalPrecioDevolucion();

    public EmpleadoController(){
        this.value =value;
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
        tablaDevolucion.setItems(listaProductoDevos);
        List<ProductoDevo> productoDevosSave = new ArrayList<>(tablaDevolucion.getItems());
        productoDevosSave.forEach(productoDevo -> {
            //Metodo para llevar a la base datos


            System.out.println("Codigo: " + productoDevo.getCodigo());
            System.out.println("Cantidad: " + productoDevo.getCantidad());
            System.out.println("Precio Total: " + productoDevo.getPrecioUnd()*productoDevo.getCantidad());
        });
        tablaDevolucion.getItems().clear();
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
        motivoGastoField.requestFocus();
        motivoGastoField.setOnAction(event -> {valorGastoField.requestFocus();});
        valorGastoField.setOnAction(event -> {ingresoGastoButton.requestFocus();});
    }
    @FXML
    public void buttonGastosClickeado(){
        motivoGastoField.requestFocus();
        String motivo = motivoGastoField.getText();
        Double valor = Double.parseDouble(valorGastoField.getText());

        Gastos gastos = new Gastos(motivo, valor);
        listaGastos.add(gastos);

        motivoGastoField.clear();
        valorGastoField.clear();
    }
    @FXML
    public Double totalGastosDia(){
        Double totalGastos = 0.0;
        for(Gastos gastos: listaGastos){
            totalGastos += gastos.getValorGasto();
        }
        return totalGastos;
    }


}
