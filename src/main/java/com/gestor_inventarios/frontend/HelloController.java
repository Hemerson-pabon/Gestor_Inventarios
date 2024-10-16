package com.gestor_inventarios.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;



public class HelloController {
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEdicion;

    @FXML
    private Button btnEntrada;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnVentas;

    @FXML
    private Pane pnlEdicion;

    @FXML
    private Pane pnlEntrada;

    @FXML
    private void handleClicker(ActionEvent actionEvent) {

        if (actionEvent.getSource() == btnEdicion) {
            pnlEdicion.toFront();
        }
        if (actionEvent.getSource() == btnEntrada){
            pnlEntrada.toFront();
        }

}
}