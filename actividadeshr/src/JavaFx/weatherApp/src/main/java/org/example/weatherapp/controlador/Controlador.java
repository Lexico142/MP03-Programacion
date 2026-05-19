package org.example.weatherapp.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.weatherapp.modelo.DatosTiempo;
import org.example.weatherapp.modelo.ModeloGestorApi;

public class Controlador {

    @FXML private TextField textoCiudadField;
    @FXML private Label errorLabel;
    @FXML private Label nombreCiudadLabel;
    @FXML private Label temperaturaLabel;
    @FXML private Label humedadLabel;
    @FXML private Label velocidadVientoLabel;
    @FXML private Label sensacionLabel;
    @FXML private VBox rootVBox;
    @FXML private Label climaEmojiLabel;

    private ModeloGestorApi gestorApi = new ModeloGestorApi();

    @FXML
    public void controlarBusqueda() {
        String ciudadBuscada = textoCiudadField.getText();

        if (ciudadBuscada == null || ciudadBuscada.trim().isEmpty()) {
            errorLabel.setText("Por favor, introduce una ciudad.");
            return;
        }


        errorLabel.setText("");

        try {
            DatosTiempo datos = gestorApi.obtenerDatos(ciudadBuscada.trim());
            int codigo = datos.getCodigoClima();
            climaEmojiLabel.setText(getEmoji(codigo));
            rootVBox.setStyle(getEstilo(codigo));
            nombreCiudadLabel.setText(datos.getCiudad());
            temperaturaLabel.setText("Temperatura: " + datos.getTemperatura() + " °C");
            humedadLabel.setText("Humedad: " + datos.getHumedad() + " %");
            velocidadVientoLabel.setText("Viento: " + datos.getViento() + " km/h");
            sensacionLabel.setText("Sensación térmica: " + datos.getSensacionTermica() + " °C");


        } catch (Exception e) {
            errorLabel.setText("No se pudo encontrar la ciudad o hubo un error.");
        }

    }

    public static String getEmoji(int codigo) {
        if (codigo == 0) return "☀";
        if (codigo <= 3) return "⛅";
        if (codigo <= 48) return "🌫";
        if (codigo <= 67) return "🌧";
        if (codigo <= 77) return "❄";
        if (codigo <= 82) return "🌦";
        return "⛈";
    }

    public static String getEstilo(int codigo) {
        if (codigo == 0)  return "-fx-background-color: #FFD700;";
        if (codigo <= 3)  return "-fx-background-color: #B0C4DE;";
        if (codigo <= 48) return "-fx-background-color: #A9A9A9;";
        if (codigo <= 67) return "-fx-background-color: #4682B4;";
        if (codigo <= 77) return "-fx-background-color: #E0FFFF;";
        if (codigo <= 82) return "-fx-background-color: #6495ED;";
        return "-fx-background-color: #483D8B;";
    }
}