package GestionArchivos.crud;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

class Libro {
    String titulo, autor, ano;

    public Libro(String titulo, String autor, String ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public String toFileString() {
        return titulo + "|" + autor + "|" + ano;
    }
}

class GestorBiblioteca {
    private String ruta = "src/GestionArchivos/crud/biblioteca.txt";

    public void crearLibro(Libro libro) {
        try {
            FileWriter writer = new FileWriter(ruta, true);
            writer.write(libro.toFileString() + "\n");
            writer.close();
            System.out.println("Libro registrado.");
        } catch (Exception e) {
            System.out.println("Error"); }
    }
    public void leerLibros() {
        try {
            Scanner sc = new Scanner(new File(ruta));
            System.out.println("\n=== CATÁLOGO ===");
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] datos = linea.split("\\|");
                if (datos.length == 3) {
                    System.out.println("Título: " + datos[0] + " - Autor: " + datos[1] + " - Año: " + datos[2]);
                }
            }
            sc.close();
        } catch (Exception e) { System.out.println("La biblioteca está vacía o hay un error."); }
    }



    public void actualizarLibro(String tituloBuscar, Libro libroActualizado) {
        try {
            String nuevoContenido = "";
            Scanner sc = new Scanner(new File(ruta));

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (linea.startsWith(tituloBuscar + "|")) {
                    nuevoContenido += libroActualizado.toFileString() + "\n";
                } else {
                    nuevoContenido += linea + "\n";
                }
            }
            sc.close();

            FileWriter writer = new FileWriter(ruta);
            writer.write(nuevoContenido);
            writer.close();
            System.out.println("Proceso de actualización finalizado.");

        } catch (Exception e) { System.out.println("Error al actualizar."); }
    }

    public void eliminarLibro(String tituloEliminar) {
        try {
            String nuevoContenido = "";
            Scanner sc = new Scanner(new File(ruta));

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (!linea.startsWith(tituloEliminar + "|")) {
                    nuevoContenido += linea + "\n";
                }
            }
            sc.close();

            FileWriter writer = new FileWriter(ruta);
            writer.write(nuevoContenido);
            writer.close();
            System.out.println("Libro eliminado.");

        } catch (Exception e) { System.out.println("Error al eliminar."); }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorBiblioteca gestor = new GestorBiblioteca();

        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n=== Biblioteca ===");
            System.out.println("1. Añadir | 2. Ver | 3. Actualizar | 4. Eliminar | 5. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.print("Título: ");
                String t = sc.nextLine();
                System.out.print("Autor: ");
                String a = sc.nextLine();
                System.out.print("Año: ");
                String y = sc.nextLine();
                gestor.crearLibro(new Libro(t, a, y));
            }
            else if (opcion == 2) {
                gestor.leerLibros();
            }
            else if (opcion == 3) {
                System.out.print("Título actual a actualizar: ");
                String buscar = sc.nextLine();
                System.out.print("Nuevo Título: ");
                String nt = sc.nextLine();
                System.out.print("Nuevo Autor: ");
                String na = sc.nextLine();
                System.out.print("Nuevo Año: ");
                String ny = sc.nextLine();
                gestor.actualizarLibro(buscar, new Libro(nt, na, ny));
            }
            else if (opcion == 4) {
                System.out.print("Título exacto del libro a eliminar: ");
                gestor.eliminarLibro(sc.nextLine());
            }
        }
        sc.close();
    }
}