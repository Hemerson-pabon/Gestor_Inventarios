package com.gestor_inventarios.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

public class EmpleadoViewController {
    //Declaraciones
    //Mostrador de caja
        //panel
    @FXML
    private Pane mostradorPanel;
        //Label's
    @FXML
    private Label labelNombreProducto;
    @FXML
    private Label precioUnitLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label totalVentaLabel;
    @FXML
    private Label vendedorLabel;
        //Button's
    @FXML
    private Button facturarButton;
        //Checkbox's
    @FXML
    private CheckBox facturarCheckout;
    @FXML
    private CheckBox ventaCheckout;
        //Text field's
    @FXML
    private TextField codProductoField;
    @FXML
    private TextField cantProductoField;

    //Cierre de caja
        //panel
    @FXML
    private Pane cierreCajaPanel;
        //Label's
    @FXML
    private Label nequiTotalLabel;
    @FXML
    private Label daviplataTotalLabel;
    @FXML
    private Label datafonoTotalLabel;
    @FXML
    private Label confirmacionLabel;
    @FXML
    private Label vendedorCierreCajaLabel;
        //TextFields's
    @FXML
    private TextField efectivoTextField;
        //Button's
    @FXML
    private Button cierreCajaButton;

    //Devolucion
        //panel
    @FXML
    private Pane devolucionPanel;
        //Label's
    @FXML
    private Label vendedorDevolucionLabel;
    @FXML
    private Label totalProductDevolucionLabel;
    @FXML
    private Label precioUnitDevolucionLabel;
    @FXML
    private Label nombreProductoDevolucionLabel;
    @FXML
    private Label totalFacturaDevolucionLabel;
        //TextField's
    @FXML
    private TextField codigoFacturaField;
    @FXML
    private TextField cantProductoDevolucionField;
        //Button's
    @FXML
    private Button devolverButton;

    ////////////////////////////////////////////////////////////////////////////////
    //metodos
        //botones aparecer paneles
    public void buttonDevolucionClickeado(){
        devolucionPanel.toFront();
    }
    public void buttonCierreClickeado(){
        cierreCajaPanel.toFront();
    }
    public void buttonMostradorClickeado(){
        mostradorPanel.toFront();
    }

}
