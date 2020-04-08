package application.controller;

import application.Main;
import javafx.fxml.FXML;

/** Controller for the user type view */
public class ChooseUserController {

  /**
   * Change to Novice configuration
   *
   * @throws Exception
   */
  @FXML
  private void switchToMainPanelNovice() throws Exception {
    Main.getProfile().remove("code-generation");
    Main.getProfile().remove("code-optimization");
    Main.getProfile().remove("developer");
    Main.generatePanels();
  }

  /**
   * Change to typical user configuration
   *
   * @throws Exception
   */
  @FXML
  private void switchToMainPanelTypical() throws Exception {
    Main.getProfile().add("code-generation");
    Main.getProfile().add("code-optimization");
    Main.getProfile().remove("developer");
    Main.generatePanels();
  }

  /**
   * Change to expert user configuration
   *
   * @throws Exception
   */
  @FXML
  private void switchToMainPanelExpert() throws Exception {
    Main.getProfile().add("code-generation");
    Main.getProfile().add("code-optimization");
    Main.getProfile().add("developer");
    Main.generatePanels();
  }

  /** Function to close the application calling the Main Controller */
  @FXML
  private void quitApp() {
    MainController main = new MainController();
    main.close();
  }

  /** Shows the help panel calling the main controller */
  @FXML
  private void showHelp() {
    MainController main = new MainController();
    main.onHelpAction();
  }
}
