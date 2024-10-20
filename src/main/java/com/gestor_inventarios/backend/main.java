package com.gestor_inventarios.backend;


public class main {
    public static void main(String[] args) {
        producto p = new producto();

        for (int i = 0; i < 100; i++){
            if (p.crearProducto("producto" + String.valueOf(i), "Esta es la descripción", "GR", 1,1,"2024-10-29", 120, 19,12)){
                System.out.println("El producto fue creado...");
            }else {
                System.out.println("No se creo el producto");
            }
        }



    }
}
