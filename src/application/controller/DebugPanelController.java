package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

public class DebugPanelController {
  @FXML private Pane debugPanel;

  @FXML private ComboBox format;

  @FXML private ComboBox level;

  @FXML private Button debugger;

  @FXML
  private void initialize() {}
}
