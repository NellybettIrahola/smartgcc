package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ChooseUserController {

  @FXML Button typical;
  MainController main;

  @FXML
  private void switchToMainPanelTypical() throws Exception {

    generatePanels(1);
  }

  @FXML
  private void switchToMainPanelNovice() throws Exception {

    generatePanels(0);
  }

  @FXML
  private void switchToMainPanelExpert() throws Exception {

    generatePanels(2);
  }

  public void generatePanels(int type) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/MainPanel.fxml"));

    this.main = new MainController();
    loader.setController(main);

    Main.getScene().setRoot(loader.load());
    this.main.initialize();
    this.main.createPanels(type);
  }
}
