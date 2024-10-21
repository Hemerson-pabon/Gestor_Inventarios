package com.gestor_inventarios.frontend.Empleados;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class FacturacionViewController {
    //label's de la pantalla
    @FXML
    private Label labelNombreProducto;

    @FXML
    private Label precioUnitLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label vendedorLabel;

    @FXML
    private Label totalVentaLabel;

    //textField's de la pantalla
    @FXML
    private TextField codProductoField;

    @FXML
    private TextField cantProductoField;

    //button's de la pantalla
    @FXML
    private Button atrasButton;

    @FXML
    private Button facturarButton;

    //checkBox's de la pantalla
    @FXML
    private CheckBox facturaCheckout;

    @FXML
    private CheckBox ventaCheckout;;

    @FXML
    protected void buttonMostradorClickeado(){
        System.out.println("Mostrando");
    }


}
