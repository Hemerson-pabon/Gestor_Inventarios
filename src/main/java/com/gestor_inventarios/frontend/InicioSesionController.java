package com.gestor_inventarios.frontend;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.backend.usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class InicioSesionController {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField FieldUsuario;
    @FXML
    private TextField FieldContrasena;

    @FXML
    protected void BotonSesionPresionado(){
        // Acciones al presionar el botón de ingresar
        FieldUsuario.getText();
        FieldContrasena.getText();
        Stage stageActual = (Stage) FieldUsuario.getScene().getWindow();
        usuario us = new usuario();
        if (us.validarUsuario(FieldUsuario.getText(), FieldContrasena.getText())){
            try {
                Operaciones_SQL op = new Operaciones_SQL();
                ResultSet rs = op.Select("usuarios", new ArrayList<>(Arrays.asList("Tipo_Usuario")),"user = '" + FieldUsuario.getText() + "'");
                rs.next();
                switch (rs.getString("Tipo_usuario")){
                    case "Admin":
                        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Administrador/Admin.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.centerOnScreen();
                        stage.setTitle("Administrador");
                        stage.setScene(scene);
                        stage.show();
                        stageActual.close();
                        break;

                        case "Empleado":
                            FXMLLoader fxmlLoader1 = new FXMLLoader(main.class.getResource("Empleados/Empleado.fxml"));
                            Scene scene1 = new Scene(fxmlLoader1.load());
                            Stage stage1 = new Stage();
                            stage1.centerOnScreen();
                            stage1.setTitle("Administrador");
                            stage1.setScene(scene1);
                            stage1.show();
                            stageActual.close();
                            break;
                }
            }catch (SQLException e){
                System.err.println("Error: " + e.getMessage());
            } catch (IOException e) {
            }
        }

    }


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
