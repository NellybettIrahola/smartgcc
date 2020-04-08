package application.controller;

import application.views.TabProjectPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/** Listener to the projects tab pane */
public class ChangeListenerTabs implements ChangeListener<TabProjectPane> {
  MainController main;
  /**
   * Gives access to the main controller
   *
   * @param main Main Controller
   */
  public ChangeListenerTabs(MainController main) {
    this.main = main;
  }

  /** Loads the flags when a project is change */
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
