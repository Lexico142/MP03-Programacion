package org.example.weatherapp.modelo;

import java.net.URI;
import java.net.http.*;

public class ModeloGestorApi {

    public DatosTiempo obtenerDatos(String ciudad) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name="
                + ciudad.replace(" ", "%20") + "&count=1&language=es&format=json";

        HttpRequest geoRequest = HttpRequest.newBuilder().uri(URI.create(geoUrl)).build();
        HttpResponse<String> geoResponse = client.send(geoRequest, HttpResponse.BodyHandlers.ofString());
        String geoJson = geoResponse.body();

        if (!geoJson.contains("\"latitude\":")) {
            throw new Exception("Ciudad no encontrada");
        }

        int latIdx = geoJson.indexOf("\"latitude\":") + 11;
        int lonIdx = geoJson.indexOf("\"longitude\":") + 12;
        String lat = geoJson.substring(latIdx, geoJson.indexOf(",", latIdx));
        String lon = geoJson.substring(lonIdx, geoJson.indexOf(",", lonIdx));

        int nameIdx = geoJson.indexOf("\"name\":\"") + 8;
        String nombreReal = geoJson.substring(nameIdx, geoJson.indexOf("\"", nameIdx));

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat
                + "&longitude=" + lon
                + "&current=temperature_2m,relative_humidity_2m,apparent_temperature,wind_speed_10m,weather_code"
                + "&timezone=auto";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String weatherJson = response.body();

        int inicioDatos = weatherJson.indexOf("\"current\":") ;
        String current = weatherJson.substring(inicioDatos);

        int tempIdx = current.indexOf("\"temperature_2m\":") + 17;
        double temp = Double.parseDouble(current.substring(tempIdx, current.indexOf(",", tempIdx)));

        int humIdx = current.indexOf("\"relative_humidity_2m\":") + 23;
        double humedad = Double.parseDouble(current.substring(humIdx, current.indexOf(",", humIdx)));

        int sensIdx = current.indexOf("\"apparent_temperature\":") + 23;
        double sensacion = Double.parseDouble(current.substring(sensIdx, current.indexOf(",", sensIdx)));

        int vientoIdx = current.indexOf("\"wind_speed_10m\":") + 17;
        double viento = Double.parseDouble(current.substring(vientoIdx, current.indexOf(",", vientoIdx)));

        int codigoIdx = current.indexOf("\"weather_code\":") + 15;
        int codigoClima = Integer.parseInt(current.substring(codigoIdx, current.indexOf("}", codigoIdx)).trim());

        return new DatosTiempo(nombreReal, temp, humedad, viento, sensacion, codigoClima);
    }
}