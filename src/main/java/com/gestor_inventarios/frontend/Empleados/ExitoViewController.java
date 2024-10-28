package com.gestor_inventarios.frontend.Empleados;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class ExitoViewController {
    @FXML
    private Button atrasButtonExito;

    public void atrasButtonExitoClickeado(){
        Stage stage = (Stage) atrasButtonExito.getScene().getWindow();
        stage.close();
    }
}
