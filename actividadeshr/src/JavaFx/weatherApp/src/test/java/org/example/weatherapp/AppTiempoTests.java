package org.example.weatherapp;

import org.example.weatherapp.controlador.Controlador;
import org.example.weatherapp.modelo.ModeloGestorApi;
import org.junit.jupiter.api.Test;
import org.example.weatherapp.modelo.DatosTiempo;

import static org.junit.jupiter.api.Assertions.*;

public class AppTiempoTests {
    @Test
    public void testDatosTiempoConstructor() {
        DatosTiempo datos = new DatosTiempo("Madrid", 25.0, 60.0, 10.0, 23.0, 0);
        assertEquals("Madrid", datos.getCiudad());
        assertEquals(25.0, datos.getTemperatura(), 0.001);
        assertEquals(60.0, datos.getHumedad(), 0.001);
        assertEquals(10.0, datos.getViento(), 0.001);
        assertEquals(23.0, datos.getSensacionTermica(), 0.001);
        assertEquals(0, datos.getCodigoClima());
    }

    @Test
    public void testEstiloFondoDespejadoDistintoQLluvia() {
        String estiloDespejado = Controlador.getEstilo(0);
        String estiloLluvia = Controlador.getEstilo(61);
        assertNotEquals(estiloDespejado, estiloLluvia);
    }

    @Test
    public void testEmojiTormenta() {
        String emoji = Controlador.getEmoji(99);
        assertEquals("⛈", emoji);
    }

    @Test
    public void testHumedadEnRangoCorrecto() throws Exception {
        ModeloGestorApi gestor = new ModeloGestorApi();
        DatosTiempo datos = gestor.obtenerDatos("Madrid");
        assertTrue(datos.getHumedad() >= 0 && datos.getHumedad() <= 100);
    }

    @Test
    public void testCiudadConEspacios() throws Exception {
        ModeloGestorApi gestor = new ModeloGestorApi();
        DatosTiempo datos = gestor.obtenerDatos("Nueva York");
        assertNotNull(datos);
    }
}

