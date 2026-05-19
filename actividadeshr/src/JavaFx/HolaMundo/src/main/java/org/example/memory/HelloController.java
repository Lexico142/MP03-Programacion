package org.example.memory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label mensajeLabel;

    @FXML
    protected void onHelloButtonClick() {
        mensajeLabel.setText("Hola Mundo");
    }
}
