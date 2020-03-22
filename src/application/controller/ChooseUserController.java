package application.controller;

import application.Main;

import java.io.IOException;
import javafx.fxml.FXML;

public class ChooseUserController {

    @FXML
    private void switchToMainPanel() throws IOException {
        Main.setRoot("/application/views/MainPanel");
    }
}
