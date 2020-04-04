package application;

import application.controller.ChooseUserController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** JavaFX App */
public class Main extends Application {

  private static Scene scene;
  private static Stage primary;
  static ChooseUserController cuController;

  @Override
  public void start(Stage stage) throws IOException {
    this.primary = stage;
    scene = new Scene(loadFXML("/application/views/ChooseUser"), 1280, 720);
    scene.getStylesheets().add("/application/css/smartgcc.css");
    stage.setScene(scene);
    stage.show();
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
    cuController = fxmlLoader.getController();
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }
}
