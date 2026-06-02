package org.example;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorProductos gestor = new GestorProductos(sc);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("           MENÚ HIPERMERCADO            ");
            System.out.println("========================================");
            System.out.println("  [1] Devolver un producto por su ID");
            System.out.println("  [2] Crear un producto nuevo");
            System.out.println("  [3] Filtrar productos por su categoría");
            System.out.println("  [4] Eliminar un producto");
            System.out.println("  [5] Mostrar productos de una marca");
            System.out.println("  [6] Mostrar productos por sección");
            System.out.println("  [7] Filtrar por stock mínimo");
            System.out.println("  [8] Cambiar precio de un producto");
            System.out.println("  [9] Ordenar productos por precio");
            System.out.println("  [0] Salir del programa");
            System.out.println("=======================================-");
            System.out.print("> Elige una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            System.out.println();

            switch(opcion){
                case 1:
                    gestor.devolverProducto();
                    break;
                case 2:
                    gestor.crearProducto();
                    break;
                case 3:
                    gestor.filtrarPorCategoria();
                    break;
                case 4:
                    gestor.eliminarProducto();
                    break;
                case 5:
                    gestor.mostrarPorMarca();
                    break;
                case 6:
                    gestor.mostrarPorSecciones();
                    break;
                case 7:
                    gestor.mostrarPorStock();
                    break;
                case 8:
                    gestor.cambiarPrecioProducto();
                    break;
                case 9:
                    gestor.ordenarPorPrecioProducto();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }

        sc.close();
    }
}