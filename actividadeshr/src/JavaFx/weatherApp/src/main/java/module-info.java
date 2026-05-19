module org.example.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens org.example.weatherapp to javafx.fxml;
    exports org.example.weatherapp;
    exports org.example.weatherapp.controlador;
    opens org.example.weatherapp.controlador to javafx.fxml;
}