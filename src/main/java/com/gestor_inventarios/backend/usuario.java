package com.gestor_inventarios.backend;
import io.micrometer.observation.ObservationPredicate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class usuario {
    private String user;
    private String rawpass;
    private String encodedpass;
    private String Tipo_Usuario;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();



    // Método para crear un usuario en la base de datos

    public boolean crearUsuario(String user, String rawpass){
        Operaciones_SQL op = new Operaciones_SQL();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("user");
        columns.add("password");
        ArrayList<Object> values = new ArrayList<>();
        values.add(user);
        encriptarContraseña(rawpass);
        values.add(encodedpass);
        return op.Insert("usuarios", columns, values) == 1;
    }

    // Método para encriptar la contraseña

    public String encriptarContraseña(String rawpass){
        return encoder.encode(rawpass);
    }

    // Método para validar usuario

    public boolean validarUsuario(String user, String password){
        Operaciones_SQL op = new Operaciones_SQL();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("user");
        columns.add("password");
        ResultSet res = op.Select("usuarios",columns, "user = " + user );
        try {
            while (res.next()) {
                res.getString(1);
                if (Objects.equals(res.getString(1), user) && validarContraseña(user, password)){
                    return true;
                }
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    // Método para validar la contraseña

    private boolean validarContraseña(String user, String password){
        Operaciones_SQL op = new Operaciones_SQL();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("password");
        ResultSet res = op.Select("usuarios", columns, "user = " + user );
        try {
            res.next();
            return encoder.matches(rawpass, res.getString(1));
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    // Método para eliminar usuario

    public boolean eliminarUsuario(String user){
        Operaciones_SQL op = new Operaciones_SQL();
        return op.Delete("usuarios", "user = " + user) == 1;
    }

}
