package com.gestor_inventarios.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertaController {

    private String Mensaje;

    @FXML
    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
        LabelMensaje.setText(Mensaje);
    }

    @FXML
    private Label LabelMensaje;

    @FXML
    private Button BtnCerrar;

    @FXML
    protected void BotonCerrarAlertaClickeado(){
        Stage stage = (Stage) BtnCerrar.getScene().getWindow();
        stage.close();
    }

}
