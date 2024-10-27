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
    public void nequiAction(){
        if (nequiChekout.isSelected()) {
            daviplataCheckout.setSelected(false);
            ahorroCheckout.setSelected(false);
            debitoCheckout.setSelected(false);
            creditoCheckout.setSelected(false);
        }
    }

    @FXML
    public void daviplataAction(){
        if (daviplataCheckout.isSelected()) {
            nequiChekout.setSelected(false);
            ahorroCheckout.setSelected(false);
            debitoCheckout.setSelected(false);
            creditoCheckout.setSelected(false);
        }
    }

    @FXML
    public void ahorroAction(){
        if (ahorroCheckout.isSelected()) {
            daviplataCheckout.setSelected(false);
            nequiChekout.setSelected(false);
            debitoCheckout.setSelected(false);
            creditoCheckout.setSelected(false);
    }

    }

    @FXML
    public void debitoAction(){
        if (debitoCheckout.isSelected()) {
            daviplataCheckout.setSelected(false);
            ahorroCheckout.setSelected(false);
            nequiChekout.setSelected(false);
            creditoCheckout.setSelected(false);
        }
    }

    @FXML
    public void creditoAction(){
        if (creditoCheckout.isSelected()) {
            daviplataCheckout.setSelected(false);
            ahorroCheckout.setSelected(false);
            debitoCheckout.setSelected(false);
            nequiChekout.setSelected(false);
        }
    }

    //MetodosPagoViewController ventanaMetodos = new MetodosPagoViewController();






}
