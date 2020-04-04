package application.controller;

import java.util.LinkedList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class OptimizationPanelController {

  @FXML private Pane optimizationOptsPanel;

  @FXML private RadioButton radioButtonO0;

  @FXML private RadioButton radioButtonO1;

  @FXML private RadioButton radioButtonO2;

  @FXML private RadioButton radioButtonO3;

  @FXML private RadioButton radioButtonOs;

  @FXML private RadioButton radioButtonOg;

  @FXML private RadioButton radioButtonOfast;

  RadioButton checked = new RadioButton();

  ToggleGroup group = new ToggleGroup();

  @FXML
  private void initialize() {
    radioButtonO0.setToggleGroup(group);
    radioButtonO1.setToggleGroup(group);
    radioButtonO2.setToggleGroup(group);
    radioButtonO3.setToggleGroup(group);
    radioButtonOs.setToggleGroup(group);
    radioButtonOg.setToggleGroup(group);
    radioButtonOfast.setToggleGroup(group);

    group
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {

              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton checked = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
              }
            });
  }

  public LinkedList<String> getOptimizationFlags() {
    LinkedList<String> options = new LinkedList<String>();
    String optimizationLevel = checked.getText();
    System.out.println(optimizationLevel);
    options.add(optimizationLevel);
    return options;
  }
}
