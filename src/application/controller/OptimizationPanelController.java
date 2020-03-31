package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class OptimizationPanelController {

  @FXML private Pane optimizationPanel;

  @FXML private RadioButton radioButtonO0;

  @FXML private RadioButton radioButtonO1;

  @FXML private RadioButton radioButtonO2;

  @FXML private RadioButton radioButtonO3;

  @FXML private RadioButton radioButtonOs;

  @FXML private RadioButton radioButtonOg;

  @FXML private RadioButton radioButtonOfast;
  ToggleGroup group;

  @FXML
  private void initialize() {
    group = new ToggleGroup();
    radioButtonO0.setToggleGroup(group);
    radioButtonO1.setToggleGroup(group);
    radioButtonO2.setToggleGroup(group);
    radioButtonO3.setToggleGroup(group);
    radioButtonOs.setToggleGroup(group);
    radioButtonOg.setToggleGroup(group);
    radioButtonOfast.setToggleGroup(group);
  }
}
