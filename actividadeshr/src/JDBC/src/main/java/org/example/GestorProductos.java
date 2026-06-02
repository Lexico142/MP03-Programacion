package org.example;

import java.sql.*;
import java.util.Scanner;

public class GestorProductos {
    private Scanner sc;

    public GestorProductos(Scanner sc) {
        this.sc = sc;
    }

    public void devolverProducto() {
        System.out.println("=== BUSCAR PRODUCTO POR ID ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID del producto: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM productos WHERE id_producto = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n=== Resultados ===");
            boolean encontrado = false;
            while (rs.next()) {
                encontrado = true;
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Desc:   " + rs.getString("descripcion"));
                System.out.println("Precio: " + rs.getFloat("precio") + " euros");
                System.out.println("Stock:  " + rs.getInt("stock") + " uds");
            }
            if(!encontrado) System.out.println("No se ha encontrado ningún producto con ese ID.");

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    public void crearProducto() {
        System.out.println("=== CREAR NUEVO PRODUCTO ===");
        System.out.println("Por favor, rellena todos los campos del producto:");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            System.out.print("ID de la sección (Ej: 1): ");
            int id_seccion = sc.nextInt();

            System.out.print("ID de la marca (Ej: 3): ");
            int id_marca = sc.nextInt();

            System.out.print("Precio unitario (euros): ");
            float precio = sc.nextFloat();

            System.out.print("Precio por Kg o Litro (euros): ");
            float precio_kg_l = sc.nextFloat();

            System.out.print("Peso neto (g) o volumen (ml): ");
            float peso_ml = sc.nextFloat();
            sc.nextLine();

            System.out.print("Unidad de medida (g, kg, ml, l, ud, pack): ");
            String unidad_medida = sc.nextLine();

            System.out.print("Stock inicial (uds): ");
            int stock = sc.nextInt();

            System.out.print("Descuento (%): ");
            int descuento_pct = sc.nextInt();

            System.out.print("¿Es novedad? (1 = Sí, 0 = No): ");
            int es_novedad = sc.nextInt();

            System.out.print("¿Es producto BIO? (1 = Sí, 0 = No): ");
            int es_bio = sc.nextInt();

            System.out.print("¿Es sin gluten? (1 = Sí, 0 = No): ");
            int sin_gluten = sc.nextInt();

            System.out.print("¿Es vegano? (1 = Sí, 0 = No): ");
            int vegano = sc.nextInt();

            System.out.print("Calorías por 100g: ");
            int calorias = sc.nextInt();
            sc.nextLine();

            System.out.print("Fecha de caducidad (YYYY-MM-DD) o ENTER si no tiene: ");
            String fecha_caducidad = sc.nextLine();

            System.out.print("Código de barras (13 dígitos): ");
            String codigo = sc.nextLine();

            System.out.print("¿Está activo? (1 = Sí, 0 = No): ");
            int activo = sc.nextInt();

            String sql = "INSERT INTO productos (nombre, descripcion, id_seccion, id_marca, precio, precio_kg_l, peso_ml, unidad_medida, stock, descuento_pct, es_novedad, es_bio, sin_gluten, vegano, calorias_100g, fecha_caducidad, codigo_barras, activo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, id_seccion);
            ps.setInt(4, id_marca);
            ps.setFloat(5, precio);
            ps.setFloat(6, precio_kg_l);
            ps.setFloat(7, peso_ml);
            ps.setString(8, unidad_medida);
            ps.setInt(9, stock);
            ps.setInt(10, descuento_pct);
            ps.setInt(11, es_novedad);
            ps.setInt(12, es_bio);
            ps.setInt(13, sin_gluten);
            ps.setInt(14, vegano);
            ps.setInt(15, calorias);

            if(fecha_caducidad.trim().isEmpty()) {
                ps.setNull(16, Types.DATE);
            } else {
                ps.setDate(16, Date.valueOf(fecha_caducidad));
            }

            ps.setString(17, codigo);
            ps.setInt(18, activo);

            int filas = ps.executeUpdate();
            if(filas > 0) {
                System.out.println("¡Producto guardado correctamente en la base de datos!");
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("No se ha podido crear el producto: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error en el formato de la fecha. Usa YYYY-MM-DD.");
        }
    }

    public void filtrarPorCategoria() {
        System.out.println("=== FILTRAR POR CATEGORÍA ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID de la categoría (sección): ");
            int idSeccion = sc.nextInt();

            String sql = "SELECT * FROM productos WHERE id_seccion = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idSeccion);

            ResultSet rs = ps.executeQuery();
            System.out.println("\n=== Resultados ===");
            while (rs.next()) {
                System.out.println("- [" + rs.getInt("id_producto") + "] " + rs.getString("nombre") + " - " + rs.getFloat("precio") + " euros");
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar por categoría.");
        }
    }

    public void eliminarProducto() {
        System.out.println("=== ELIMINAR PRODUCTO ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID del producto a borrar: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM productos WHERE id_producto = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto borrado con éxito.");
            } else {
                System.out.println("No se ha encontrado ningún producto con ese ID.");
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al intentar borrar: " + e.getMessage());
        }
    }

    public void mostrarPorMarca() {
        System.out.println("=== MOSTRAR POR MARCA ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID de la marca: ");
            int idMarca = sc.nextInt();

            String sql = "SELECT * FROM productos WHERE id_marca = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMarca);

            ResultSet rs = ps.executeQuery();
            System.out.println("\n=== Resultados ===");
            while (rs.next()) {
                System.out.println("- [" + rs.getInt("id_producto") + "] " + rs.getString("nombre"));
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar marcas.");
        }
    }

    public void mostrarPorSecciones() {
        System.out.println("=== MOSTRAR POR SECCIÓN ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID de la seccion: ");
            int idSeccion = sc.nextInt();

            String sql = "SELECT * FROM productos WHERE id_seccion = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idSeccion);

            ResultSet rs = ps.executeQuery();
            System.out.println("\n=== Resultados ===");
            while (rs.next()) {
                System.out.println("- [" + rs.getInt("id_producto") + "] " + rs.getString("nombre"));
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar secciones.");
        }
    }

    public void mostrarPorStock() {
        System.out.println("=== FILTRAR POR STOCK ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce la cantidad mínima de stock: ");
            int cantidad = sc.nextInt();

            String sql = "SELECT * FROM productos WHERE stock >= ? ORDER BY stock";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, cantidad);

            ResultSet rs = ps.executeQuery();
            System.out.println("\n=== Resultados ===");
            while (rs.next()) {
                System.out.println("- [" + rs.getInt("id_producto") + "] " + rs.getString("nombre") +  " (Stock: " + rs.getInt("stock") + ")");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al comprobar el stock.");
        }
    }

    public void cambiarPrecioProducto() {
        System.out.println("=== ACTUALIZAR PRECIO ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("Introduce el ID del producto a actualizar: ");
            int id = sc.nextInt();

            System.out.print("Introduce el nuevo precio: ");
            float precio = sc.nextFloat();

            String sql = "UPDATE productos SET precio = ? WHERE id_producto = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setFloat(1, precio);
            ps.setInt(2, id);

            int filas = ps.executeUpdate();
            if(filas > 0){
                System.out.println("Precio actualizado con éxito.");
            } else {
                System.out.println("El producto no existe.");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el precio.");
        }
    }

    public void ordenarPorPrecioProducto() {
        System.out.println("=== ORDENAR POR PRECIO ===");
        try {
            Connection conexion = ConexionDB.conectar();

            System.out.print("¿Orden ascendente (1) o descendente (2)?: ");
            int orden = sc.nextInt();

            String sql = "SELECT * FROM productos ORDER BY precio " + (orden == 1 ? "ASC" : "DESC");
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=== Resultados ===");
            while (rs.next()) {
                System.out.println("- [" + rs.getInt("id_producto") + "] " + rs.getString("nombre") + " - " + rs.getFloat("precio") + " euros");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al ordenar productos.");
        }
    }
}