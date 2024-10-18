package com.gestor_inventarios.frontend;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class InicioSesionController {

    private double xOffset = 0;
    private double yOffset = 0;


    // Eventos para mover la ventana
    @FXML
    protected void handleMousePressed(MouseEvent event) {
        // Obtener la ventana actual
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();

        // Capturar las coordenadas iniciales del ratón
        xOffset = event.getScreenX() - stage.getX();
        yOffset = event.getScreenY() - stage.getY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        // Obtener la ventana actual
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();

        // Calcular la nueva posición de la ventana
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        // Obtener la ventana actual
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
    }

    @FXML
    protected void OnBotonCerrar() {
        System.exit(0);
    }
}
