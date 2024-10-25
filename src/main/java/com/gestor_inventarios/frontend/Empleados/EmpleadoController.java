package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class EmpleadoController {
    //Declaraciones de objetos
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
            totalPrecioMostrador.setText(String.valueOf(resultado));
            totalVentaMostrador.setText(String.valueOf(totalVenta));
        }

    }


    //Metodos


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
        /*en esta opcion se debe enviar el producto y sus relacionados
          a una tabla que se muestre por debajo */
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
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/CambioView.fxml"));
            //Parent root = loader.load();
            // Crear una nueva escena y un nuevo stage (ventana)
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Cambio");
            //stage.setScene(new Scene(root));
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
        /*en esta opcion se debe enviar el producto y sus relacionados
          a una tabla que se muestre por debajo */
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
