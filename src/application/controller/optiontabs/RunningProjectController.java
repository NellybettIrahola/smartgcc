package application.controller.optiontabs;

import application.controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/** Controller of the Running project view (for the tab) */
public class RunningProjectController {
  MainController main;
  @FXML public TextArea inputExecute;
  @FXML public TextArea resultExecute;

  /**
   * Sets the main controller as an attribute
   *
   * @param main the main controller
   */
  public void setMain(MainController main) {
    this.main = main;
  }
}
