package com.gestor_inventarios.backend;


public class main {
    public static void main(String[] args) {
        usuario user = new usuario("user", "124");
        if (user.validarUsuario()){
            System.out.println("Usuario es valido...");
        }


    }
}
