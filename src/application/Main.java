package application;

import application.controller.MainController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/** Main of the application */
public class Main extends Application {

  private static Scene scene;
  private static Stage primary;
  private static String profileFile;
  private static Set<String> profile;
  private static Parent tmp;
  private static MainController mainCtrl;

  /** Init function of the application */
  @Override
  public void init() throws Exception {
    super.init();
    String userHome = System.getProperty("user.home");
    profileFile = userHome + File.separator + "smartgccProfile.ini";
  }

  /**
   * Loads the profile of the user
   *
   * @return true or false
   */
  public boolean loadProfile() {
    try {
      System.out.println("profile load from: " + profileFile);
      profile = new HashSet(Files.readAllLines(Paths.get(profileFile)));
      for (String p : profile) {
        System.out.println("load profile: " + p);
      }
      return true;
    } catch (IOException e) {
      System.out.println("fresh profile required.");
      profile = new HashSet<String>();
      return false;
    }
  }

  /**
   * This saves the user profile (their preferences for the panels)
   *
   * @return true or false
   */
  public static boolean saveProfile() {
    String output = "";
    if (profile.contains("code-optimization")) {
      output += "code-optimization\n";
    }
    if (profile.contains("code-generation")) {
      output += "code-generation\n";
    }
    if (profile.contains("developer")) {
      output += "developer\n";
    }
    try {
      System.out.println("profile save to: " + profileFile);
      Files.write(Paths.get(profileFile), output.getBytes());
    } catch (IOException e) {
      System.out.println("profile save FAILED");
      return false;
    }
    System.out.println("profile save SUCCESS");
    return true;
  }

  /**
   * Getter for profile
   *
   * @return profile
   */
  public static Set<String> getProfile() {
    return profile;
  }

  /**
   * Calls the function in the main controller that generates the compiling option panels
   *
   * @throws IOException
   */
  public static void generatePanels() throws IOException {
    if (tmp == null) {
      FXMLLoader loader =
          new FXMLLoader(Main.class.getResource("/application/views/MainPanel.fxml"));
      Main.getScene().setRoot(loader.load());
      mainCtrl = loader.getController();
      mainCtrl.generatePanels();
    } else {
      restoreWorkspace();
      mainCtrl.generatePanels();
    }
  }

  /** Start method of the application */
  @Override
  public void start(Stage stage) throws IOException {
    boolean profileOk = loadProfile();
    if (profileOk) {
      Main.primary = stage;
      stage.setMinWidth(400);
      stage.setMinHeight(400);
      scene = new Scene(new Pane(), 1280, 720);
      stage.setScene(scene);
      stage.show();
      stage.setTitle("SmartGCC by Team 2");
      Main.generatePanels();
    } else {
      Main.primary = stage;
      scene = new Scene(loadFXML("/application/views/ChooseUser"), 1280, 720);
      stage.setScene(scene);
      stage.setTitle("SmartGCC by Team 2");
      stage.show();
    }
  }

  /**
   * Change the principal view
   *
   * @param fxml the view
   * @throws IOException
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  /**
   * Gets the main scene
   *
   * @return scene
   */
  public static Scene getScene() {
    return scene;
  }

  /**
   * Get the primary stage
   *
   * @return stage
   */
  public static Stage getStage() {
    return primary;
  }

  /**
   * LoadS the fxml file TO CHANGE THE VIEW
   *
   * @param fxml
   * @return
   * @throws IOException
   */
  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }
  /**
   * Main function
   *
   * @param args provided arguments
   */
  public static void main(String[] args) {
    launch();
  }

  /** Save the workspace */
  public static void saveWorkspace() {
    tmp = getScene().getRoot();
  }

  /** Loads the workspace */
  public static void restoreWorkspace() {
    getScene().setRoot(tmp);
  }
}
