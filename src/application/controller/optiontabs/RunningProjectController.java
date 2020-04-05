package application.controller.optiontabs;

import application.controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RunningProjectController {
  MainController main;
  @FXML public TextArea inputExecute;
  @FXML public TextArea resultExecute;

  public void setMain(MainController main) {
    this.main = main;
  }
}
