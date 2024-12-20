module com.gestor_inventarios.gestor_inventarios {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires micrometer.observation;
    requires spring.security.crypto;
    requires mysql.connector.j;


    opens com.gestor_inventarios.frontend.Empleados to javafx.fxml, javafx.base;
    opens com.gestor_inventarios.frontend.Administrador to javafx.fxml;
    opens com.gestor_inventarios.frontend to javafx.fxml;
    exports com.gestor_inventarios.backend;
    exports com.gestor_inventarios.frontend;
}