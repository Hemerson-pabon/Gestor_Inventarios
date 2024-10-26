package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.frontend.main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class MetodosPagoViewController {

    @FXML
    private Button atrasButton;

    @FXML
    private Button siguienteButton;

    @FXML
    private CheckBox nequiChekout;

    @FXML
    private CheckBox daviplataCheckout;

    @FXML
    private CheckBox ahorroCheckout;

    @FXML
    private CheckBox debitoCheckout;

    @FXML
    private CheckBox creditoCheckout;
    //metodos
    @FXML
    public void atrasButtonClickeado(){
        Stage stage = (Stage) atrasButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void siguienteButtonClickeado(){
        Stage stage = (Stage) siguienteButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void nequiAction(){}

    @FXML
    public void daviplataAction(){}

    @FXML
    public void ahorroAction(){}

    @FXML
    public void debitoAction(){}

    @FXML
    public void creditoAction(){}


}
