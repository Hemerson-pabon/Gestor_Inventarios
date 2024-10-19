package com.gestor_inventarios.backend;


public class main {
    public static void main(String[] args) {
        producto p = new producto();
        // p.crearProducto("producto1", "jaksñdfjñasdfjasdjfklasjdfkñadsf", "Lt", 1,1,"2024-10-29", 1200, 19,2)
        if (p.eliminarProducto(0)){
            System.out.println("El producto fue eliminado");
        }else {
            System.out.println("No se elimino el producto");
        }


    }
}
