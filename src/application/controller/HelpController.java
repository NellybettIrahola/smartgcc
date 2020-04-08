package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/** Controller of the help panel */
public class HelpController implements Initializable {

  @FXML private Label compileproject;

  @FXML private Label text;

  /**
   * Instructions to compile a project
   *
   * @param event
   */
  @FXML
  void compileProject(MouseEvent event) {
    text.setText(
        "1. Create a project \n\n2. Click Build icon \n\n3. View the output at the provided directoty location ");
  }

  /**
   * Instructions to create a Project
   *
   * @param event
   */
  @FXML
  void createProject(MouseEvent event) {
    text.setText(
        "1. Inside SmartGCC select the menu item File > New >Project.... or click on the new project icon to open the New Project wizard \n\n2. Provide the location of the directory where you can check the results \n\n3. Provide the project name and click the + symbol to the file you want work on \n\n4. Click Create");
  }

  /**
   * Instruction to build a project
   *
   * @param event
   */
  @FXML
  void buildProject(MouseEvent event) {
    text.setText("1. Create a project \n\n2. Click the Build button or press ctrl+b");
  }

  /**
   * Instructions to save a project
   *
   * @param event
   */
  @FXML
  void saveProject(MouseEvent event) {
    text.setText(
        "1. Create a project \n\n 2. Click on save icon on main panel \n\n3. Provide location  \n\n4. Click save in the dialog box ");
  }

  /**
   * Available options for a project
   *
   * @param event
   */
  @FXML
  void options(MouseEvent event) {
    text.setText(
        "1. Create a project \n\n2. Build the code using Build icon on the main panel \n\n3. Choose the required options \n\n4. Click Build");
  }

  /**
   * Instructions to change the user type
   *
   * @param event
   */
  @FXML
  void userType(MouseEvent event) {
    text.setText(
        "Change the user type by clicling change user type icon on the main panel and select your type of user");
  }

  /**
   * Erase text
   *
   * @param event
   */
  @FXML
  void compilerOptions(MouseEvent event) {
    text.setText("");
  }

  /** Initialize controller */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
  }
}
