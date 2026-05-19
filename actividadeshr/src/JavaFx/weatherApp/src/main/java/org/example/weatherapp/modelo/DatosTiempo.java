package org.example.weatherapp.modelo;

public class DatosTiempo {
    private String ciudad;
    private double temperatura;
    private double humedad;
    private double viento;
    private double sensacionTermica;
    private int codigoClima;

    public DatosTiempo(String ciudad, double temperatura, double humedad, double viento, double sensacionTermica, int codigoClima) {
        this.ciudad = ciudad;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.viento = viento;
        this.sensacionTermica = sensacionTermica;
        this.codigoClima = codigoClima;

    }

    public String getCiudad() { return ciudad; }
    public double getTemperatura() { return temperatura; }
    public double getHumedad() { return humedad; }
    public double getViento() { return viento; }
    public double getSensacionTermica() { return sensacionTermica; }
    public int getCodigoClima() { return codigoClima; }


}