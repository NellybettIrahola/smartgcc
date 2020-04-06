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

/** JavaFX App */
public class Main extends Application {

  private static Scene scene;
  private static Stage primary;
  private static String profileFile;
  private static Set<String> profile;
  private static Parent tmp;
  private static MainController mainCtrl;

  @Override
  public void init() throws Exception {
    super.init();
    String userHome = System.getProperty("user.home");
    profileFile = userHome + File.separator + "smartgccProfile.ini";
  }

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

  public static Set<String> getProfile() {
    return profile;
  }

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
      Main.generatePanels();
    } else {
      Main.primary = stage;
      scene = new Scene(loadFXML("/application/views/ChooseUser"), 1280, 720);
      stage.setScene(scene);
      stage.show();
    }
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  public static Scene getScene() {
    return scene;
  }

  public static Stage getStage() {
    return primary;
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }

  public static void saveWorkspace() {
    tmp = getScene().getRoot();
  }

  public static void restoreWorkspace() {
    getScene().setRoot(tmp);
  }
}
