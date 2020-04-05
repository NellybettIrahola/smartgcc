package application.controller;

import application.views.TabProjectPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChangeListenerTabs implements ChangeListener<TabProjectPane> {
  MainController main;

  public ChangeListenerTabs(MainController main) {
    this.main = main;
  }

  @Override
  public void changed(
      ObservableValue<? extends TabProjectPane> observable,
      TabProjectPane oldValue,
      TabProjectPane newValue) {
    if (oldValue != null && newValue != null) {
      main.loadSelectedElements(newValue.getText());
    }
  }
}
