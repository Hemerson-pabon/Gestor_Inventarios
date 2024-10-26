package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioViewController {
    //Panel por defecto
    @FXML
    private Pane cambioPane;

    //label's de la pantalla
    @FXML
    private Label valorLabel;

    @FXML
    private Label cambioLabel;

    //button's de la pantalla
    @FXML
    private Button OtrosMetodosButton;

    @FXML
    private Button facturarButton;

    @FXML
    private Button cancelButton;

    //checkbox de la pantalla
    @FXML
    private CheckBox efectivoCheckout;

    //TextField de la pantalla
    @FXML
    private TextField pagoField;

    //Eventos de los botones de la pantalla
        //cancelButton
    @FXML
    public void botonCerrarClickeado() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void OtrosMetodosButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/MetodosPagoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Metodos de pago");
        stage.show();
    }
    @FXML
    public void facturarButtonClick(){}
    @FXML
    public void efectivoCheckClick(){}
}
