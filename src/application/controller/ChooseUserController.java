package application.controller;

import application.Main;
import javafx.fxml.FXML;

public class ChooseUserController {

  @FXML
  private void switchToMainPanelNovice() throws Exception {
    Main.getProfile().remove("code-generation");
    Main.getProfile().remove("code-optimization");
    Main.getProfile().remove("developer");
    Main.generatePanels();
  }

  @FXML
  private void switchToMainPanelTypical() throws Exception {
    Main.getProfile().add("code-generation");
    Main.getProfile().add("code-optimization");
    Main.getProfile().remove("developer");
    Main.generatePanels();
  }

  @FXML
  private void switchToMainPanelExpert() throws Exception {
    Main.getProfile().add("code-generation");
    Main.getProfile().add("code-optimization");
    Main.getProfile().add("developer");
    Main.generatePanels();
  }

  @FXML
  private void quitApp() {
    MainController main = new MainController();
    main.close();
  }

  @FXML
  private void showHelp() {
    MainController main = new MainController();
    main.onHelpAction();
  }
}
