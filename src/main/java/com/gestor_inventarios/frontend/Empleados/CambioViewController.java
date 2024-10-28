package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.frontend.main;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class CambioViewController extends EmpleadoController {
    String eleccion;

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
    private Button facturarButton;
    @FXML
    private Button cancelButton;

    //TextField de la pantalla
    @FXML
    private TextField pagoField;
    @FXML
    private ComboBox <String> metodosComboBox;

    //Eventos de los botones de la pantalla
        //cancelButton
    @FXML
    public void initialize(){
        metodosComboBox.setItems(FXCollections.observableArrayList("Efectivo","Nequi", "Daviplata","Ahorro a la mano","Tarjeta debito","Tarjeta credito"));
    }

    @FXML
    public void botonCerrarClickeado(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void facturarButtonClick() throws IOException {

        int newStock = 1;
        int productoEleccion = 2;

        Operaciones_SQL op = new Operaciones_SQL();

        ArrayList <String> columns = new ArrayList<>();
        columns.add("Stock");
        ArrayList<Object> values = new ArrayList<>();
        values.add(newStock);


        if((op.Update("Productos", columns, values, "ID_Producto = 2") == 1)){//
            System.out.println("Update realizado");
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/ExitoView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registro Exitoso");
            stage.show();
        }else{
            //Abre la ventana de alerta por un error en el update
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/AlertaView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Error");
            stage.show();
        }
        //cierra la ventana
        Stage stage = (Stage) facturarButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void metodosAction(){
        verificadorField();
    }

    public int seleccionMetodoUsuario(){
        eleccion = metodosComboBox.getValue();
        switch (eleccion){
            case "Efectivo":
                return 1;
            case "Nequi":
            return 2;
            case "Daviplata":
                return 3;
            case "Ahorro a la mano":
                return 4;
            case "Tarjeta debito":
                return 5;
            case "Tarjeta credito":
                return 6;


        }
        return 0;
    }

    public void verificadorField(){
        int elector = seleccionMetodoUsuario();
        if (elector != 1){
            pagoField.setDisable(true);
            mostrarTotalPrecio();
            //pagoField.setText();  //asignarle el valor de la label de valor total

        }else{
            pagoField.setDisable(false);
            int valorIngresado = Integer.parseInt(pagoField.getText());
            int valorVenta = Integer.parseInt(getTotalPrecioMostradorText());
            int valorCambio = valorIngresado - valorVenta;
            cambioLabel.setText(String.valueOf(valorCambio));
            //aca se debe hacer la resta del valor ingresado por el cambio y el valor total de venta
            //tambien debe enviar a la label del cambio
        }
    }
    public void mostrarTotalPrecio() {
        String totalPrecio = getTotalPrecioMostradorText();
        pagoField.setText(totalPrecio);
    }





    //CambioViewController ventanaCambio = new CambioViewController();
    /*public int Insert(String table, ArrayList<String> columns, ArrayList<Object> values){
        // Construcción del string para insertar datos
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table);
        sql.append(" (");
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                sql.append(columns.get(i));
            }else{
                sql.append(columns.get(i)).append(", ");
            }
        }
        sql.append(") VALUES (");

        for (int i = 0; i < values.size(); i++) {
            if (i == values.size() - 1) {
                sql.append("?");
            }
            else {
                sql.append("?, ");
            }
        }
        sql.append(")");
        try{
            ST = cn.getConexion().prepareStatement(sql.toString());
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                switch (value) {
                    case String s -> ST.setString(i + 1, s);  // Para cadenas
                    case Integer integer -> ST.setInt(i + 1, integer);  // Para enteros
                    case Double v -> ST.setDouble(i + 1, v);  // Para números decimales
                    case Boolean b -> ST.setBoolean(i + 1, b);  // Para valores booleanos
                    case null, default -> ST.setObject(i + 1, value);  // Para otros tipos de datos
                }
            }

            return ST.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error al intentar consultar datos: " + e.getMessage());
        }
        finally {
            cn.close();
        }
        return 0;
    }*/

}
