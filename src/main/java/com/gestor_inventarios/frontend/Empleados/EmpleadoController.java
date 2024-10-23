package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.producto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    // Metodo de inicialización

    @FXML
    private void initialize() {
        codigoProdMostrador.textProperty().addListener((observable -> {
            try {
                producto p = new producto();
                ArrayList<String> columns = new ArrayList<>();
                columns.add("Descripción");
                columns.add("Precio_Venta");
                ResultSet res = p.Select("productos", columns, "ID_Producto = " + Integer.parseInt(codigoProdMostrador.getText()));
                res.next();
                System.out.println("Nombre: " + res.getString(1));
                System.out.println("Precio: " + res.getString(2));
            }catch (SQLException | NullPointerException ex){
                codigoProdMostrador.setText("0");
            } catch (NumberFormatException e){
                LabelCostoI.setText("");
            }
        }));
    }


    //Metodos
        //Mostrador de venta
    public void buttonMostradorInterfazClickeado(){
        mostradorPanel.toFront();
    }
    public void buttonSiguienteMostradorClickeado(){}
    public void buttonEdicionMostradorClickeado(){}
    public void buttonMostradorClickeado(){}

        //Devolucion de producto
    public void buttonDevolucionInterfazClickeado(){
        devolucionPanel.toFront();
    }
    public  void buttonSiguienteDevolClickeado(){}
    public void buttonEdicionDevolClickeado(){}
    public void buttonDevolucionClickeado(){}

        //Cierre de caja
    public void buttonCierreInterfazClickeado(){
        cierreCajaPanel.toFront();
    }
    public void buttonCierreClickeado(){}

        //Gastos de caja
    public void buttonGastosInterfazClickeado(){
        gastosPanel.toFront();
    }
    public void buttonGastosClickeado(){}


}
