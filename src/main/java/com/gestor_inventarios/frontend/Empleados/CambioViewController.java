package com.gestor_inventarios.frontend.Empleados;

import com.gestor_inventarios.backend.Operaciones_SQL;
import com.gestor_inventarios.backend.inventario;
import com.gestor_inventarios.frontend.main;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private Double totalVenta;

    private TableView<ProductoVentas> tablaVentas;

    //Eventos de los botones de la pantalla
        //cancelButton
    @FXML
    public void initialize(){
        metodosComboBox.setItems(FXCollections.observableArrayList("Efectivo","Nequi", "Daviplata","Ahorro a la mano","Tarjeta debito","Tarjeta credito"));
        metodosComboBox.setValue("Efectivo");
        pagoField.requestFocus();
        pagoField.setOnAction(event -> {
            facturarButton.requestFocus();
            cambioLabel.setText("$ " + String.format("%.2f", calcularCambio()));

        });
    }
    boolean verificador;

    @FXML
    public void botonCerrarClickeado(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void setTablaVentas(TableView<ProductoVentas> tablaVentas) {
        this.tablaVentas = tablaVentas;
    }
    @FXML
    public void facturarButtonClick() throws IOException {
        if (tablaVentas != null) {
            // Crea un array con los productos facturados
            List<ProductoVentas> productosFacturados = new ArrayList<>(tablaVentas.getItems());

            productosFacturados.forEach(productoVentas -> {
                System.out.println("Código: " + productoVentas.getCodigoVenta());
                System.out.println("Cantidad: " + productoVentas.getCantidadVentas());

                try {
                    Operaciones_SQL op = new Operaciones_SQL();
                    ArrayList<String> columns1 = new ArrayList<>();
                    columns1.add("Stock_Actual");
                    int codigo = Integer.parseInt(productoVentas.getCodigoVenta());
                    ResultSet res = op.Select("inventario", columns1,"ID_Producto = " + codigo );
                    res.next();
                    int stockActual = res.getInt("Stock_Actual");

                    ArrayList<String> columns2 = new ArrayList<>();
                    columns2.add("Stock_Actual");
                    int stockCambio = stockActual - productoVentas.getCantidadVentas();
                    ArrayList<Object> values1 = new ArrayList<>();
                    values1.add(stockCambio);
                    if(stockActual >= productoVentas.getCantidadVentas()){
                        if(op.Update("inventario", columns2, values1,"ID_Producto = " + codigo ) >= 1 ){
                            System.out.println("Epa la arepa");
                            inventario mov = new inventario("Sucursal1");
                            mov.registrarMovimiento("Venta",codigo,productoVentas.getCantidadVentas());
                            //Abre la ventana de registro exitoso
                            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/ExitoView.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Registro Exitoso");
                            stage.show();
                        }else{
                            System.out.println("Error");
                            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/AlertaView.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Error");
                            stage.show();
                        }
                    }else{
                        System.out.println("Stock insuficiente");
                        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Empleados/AlertaView.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Error///Stock insuficiente");
                        stage.show();
                    }





                    //res.next();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



                // Aquí puedes llamar al método para guardar en la base de datos
            });
        }
        // Limpia la tabla de ventas de la ventana principal
        tablaVentas.getItems().clear();
        Stage stage = (Stage) facturarButton.getScene().getWindow();
        stage.close();
    }

    public boolean getVerificador(){
        return verificador;
    }


    @FXML
    public void metodosAction(){
        verificadorField();
    }
    @FXML
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
            mostrarTotalPrecio(totalVenta);
            cambioLabel.setText(String.valueOf(totalVenta));
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

    @FXML
    public void mostrarTotalPrecio(double totalVenta) {
        this.totalVenta = totalVenta;
        valorLabel.setText("$ " + String.format("%.2f", totalVenta));
    }

    @FXML
    public Double calcularCambio(){
        Double cambio = Double.parseDouble(pagoField.getText()) - totalVenta;
        return cambio;
    }



}
