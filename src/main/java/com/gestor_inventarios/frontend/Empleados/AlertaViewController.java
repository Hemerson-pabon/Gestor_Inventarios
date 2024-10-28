package com.gestor_inventarios.frontend.Empleados;

import javafx.fxml.FXML;
import  javafx.scene.control.Button;
import javafx.stage.Stage;

public class AlertaViewController {
    @FXML
    private Button closeButtonError;

    //metodos
     public void closeButtonErrorClickeado(){
         Stage stage = (Stage) closeButtonError.getScene().getWindow();
         stage.close();
     }


}
