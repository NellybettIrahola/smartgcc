package application.controller;

import application.Main;
import application.model.CommandExecute;
import application.model.Project;
import application.model.SmartModel;
import application.views.RulesTextField;
import application.views.TabProjectPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

  SmartModel smartModel;
  CommandExecute commandExecute;
  LinkedList<TabProjectPane> projects;
  @FXML private Button runOption;

  @FXML private Button compileOption;

  @FXML private TabPane panelCompilingOptions;

  int type;

  @FXML private TabPane projectsPane;

  // inject tab content
  @FXML private Tab optimizationTab;

  @FXML Tab textResult;

  @FXML private OptimizationPanelController optimizationPanelController;

  @FXML private Tab debugTab;

  @FXML private OptimizationPanelController debugPanelController;

  StackPane secondaryLayout;

  TextField inputProjectDirectory;
  RulesTextField inputProjectName;
  TextArea textAreaFiles;
  TextArea textAreaLibraries;
  private TextArea textAreaResult;

  public MainController() {}

  @FXML
  public void initialize() {
    System.out.println("maincontroller initialized");
    this.smartModel = new SmartModel();
    this.commandExecute = new CommandExecute();
    this.projects = new LinkedList<TabProjectPane>();
    this.textAreaResult = new TextArea();
    this.textAreaResult.setEditable(false);
    this.textResult.setContent(textAreaResult);
  }

  @FXML
  private void close() {
    System.exit(0);
  }

  @FXML
  private void buildProject() {
    if (projectsPane.getTabs().size() > 0) {
      SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
      Project prCompile = smartModel.getProject(selectionModel.getSelectedItem().getText());
      // System.out.println(prCompile.getName());
      try {
        Set<Node> m = Main.getScene().getRoot().lookupAll(".simple-opt");
        List<String> args = new ArrayList<String>();
        String argsStr = " ";
        for (Node mm : m) {
          if (mm instanceof CheckBox && ((CheckBox) mm).isSelected()) {
            args.add(((CheckBox) mm).getText());
            argsStr = argsStr + ((CheckBox) mm).getText() + " ";
            System.out.println(((CheckBox) mm).getText());
          }
        }

        String[] result = this.commandExecute.buildProject(prCompile, argsStr);
        this.textAreaResult.setText(
            this.textAreaResult.getText()
                + "\n"
                + "The executed command was:\n"
                + result[0]
                + "\n"
                + "The result was:\n"
                + result[1]);
      } catch (Exception e) {
        System.out.println("Error in execution");
      }
    } else {
      Alert alertLibrary = new Alert(AlertType.ERROR);
      alertLibrary.setTitle("Error Dialog");
      alertLibrary.setContentText("Please create a project.");
      alertLibrary.showAndWait();
    }
  }

  @FXML
  private void generateNewProjectPanel() {

    this.secondaryLayout = new StackPane();

    // Project Directory
    Label labelProjectDirectory = new Label("Project Directory:");

    inputProjectDirectory = new TextField();
    inputProjectDirectory.setDisable(true);

    Button submit = new Button("Select");

    DirectoryChooser directoryChooser = new DirectoryChooser();

    EventHandler<ActionEvent> event =
        new EventHandler<ActionEvent>() {

          public void handle(ActionEvent e) {

            // get the file selected
            File selectedDirectory = directoryChooser.showDialog(Main.getStage());

            if (selectedDirectory != null) {
              inputProjectDirectory.setText(selectedDirectory.getAbsolutePath());
            }
          }
        };

    submit.setOnAction(event);

    HBox hbProjectDirectory = new HBox();
    hbProjectDirectory.getChildren().addAll(labelProjectDirectory, inputProjectDirectory, submit);
    hbProjectDirectory.setSpacing(10);

    // Project Name
    Label labelProjectName = new Label("Project Name:");
    inputProjectName = new RulesTextField();
    inputProjectName.setMinSize(150, 25);
    HBox hbProjectName = new HBox();
    hbProjectName.getChildren().addAll(labelProjectName, inputProjectName);
    hbProjectName.setSpacing(10);

    // Files
    HBox hbFileOptions = this.createFileOptions("");

    // Libraries
    HBox hbLibrariesOptions = this.createLibraries("");

    // Create and Cancel Button
    Button createButton = new Button("Create");
    Button cancelButton = new Button("Cancel");

    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
          }
        });

    createButton.setOnAction(this::createProject);

    HBox hbFinalButtons = new HBox();
    hbFinalButtons.getChildren().addAll(createButton, cancelButton);
    hbFinalButtons.setAlignment(Pos.BASELINE_CENTER);
    hbFinalButtons.setSpacing(10);

    VBox newProject = new VBox();
    newProject
        .getChildren()
        .addAll(
            hbProjectDirectory,
            hbProjectName,
            hbFileOptions,
            textAreaFiles,
            hbLibrariesOptions,
            textAreaLibraries,
            hbFinalButtons);
    newProject.setPadding(new Insets(10, 50, 50, 50));
    newProject.setSpacing(10);

    // Adding elements
    this.secondaryLayout.getChildren().add(newProject);
    Scene secondScene = new Scene(this.secondaryLayout, 500, 500);

    // New window (Stage)
    Stage newWindow = new Stage();
    newWindow.initModality(Modality.WINDOW_MODAL);
    newWindow.initOwner(Main.getStage());
    newWindow.setTitle("New Project");
    newWindow.setScene(secondScene);

    // Set position of second window, related to primary window.
    newWindow.setX(Main.getStage().getX() + 300);
    newWindow.setY(Main.getStage().getY() + 100);

    newWindow.show();
  }

  @FXML
  public void createProject(ActionEvent event) {

    LinkedList<String> sourceFiles = this.getSourceFiles(this.textAreaFiles.getText());
    LinkedList<String> objectFiles = this.getObjectFiles(this.textAreaFiles.getText());
    LinkedList<String> libraries = this.createLibraryVariables();
    if (sourceFiles != null && objectFiles != null && libraries != null) {
      if (this.inputProjectDirectory.getText().equals("")
          || this.inputProjectName.getText().equals("")
          || this.textAreaFiles.getText().equals("")) {
        Alert alertLibrary = new Alert(AlertType.ERROR);
        alertLibrary.setTitle("Error Dialog");
        alertLibrary.setHeaderText("The project was not created");
        alertLibrary.setContentText("Please provide the mandatory fields.");
        alertLibrary.showAndWait();

      } else {
        if (this.smartModel.createProject(
                this.inputProjectName.getText(),
                sourceFiles,
                objectFiles,
                libraries,
                this.inputProjectDirectory.getText()
                    + File.separator
                    + this.inputProjectName.getText())
            == -1) {
          Alert alertLibrary = new Alert(AlertType.ERROR);
          alertLibrary.setTitle("Error Dialog");
          alertLibrary.setHeaderText("The project was not created");
          alertLibrary.setContentText(
              "Please change the project name, there is a project with that name");
          alertLibrary.showAndWait();
        } else {
          // System.out.println();
          Stage stage = (Stage) this.inputProjectName.getScene().getWindow();
          if (this.createProjectDirectory() == 0) {
            this.createProjectOnView(this.smartModel.getProject(this.inputProjectName.getText()));
            stage.close();
          }
        }
      }
    }
  }

  public HBox createFileOptions(String textFile) {
    LinkedList<String> extensions =
        new LinkedList<String>(Arrays.asList("c", "h", "C", "cpp", "CPP", "c++", "cp", "cxx"));
    Label labelFiles = new Label("Dependency Files");
    Button addFile = new Button("+");
    Button deleteFile = new Button("-");

    textAreaFiles = new TextArea();
    textAreaFiles.setText(textFile);
    textAreaFiles.setEditable(false);
    if (textAreaFiles.getText().contentEquals("")) deleteFile.setDisable(true);

    FileChooser selectFile = new FileChooser();
    EventHandler<ActionEvent> eventAddFiles =
        new EventHandler<ActionEvent>() {

          public void handle(ActionEvent e) {

            // get the file selected
            File selectedFile = selectFile.showOpenDialog(Main.getStage());

            if (selectedFile != null) {
              String selectedFilePath = selectedFile.getAbsolutePath();

              String extension =
                  selectedFilePath.substring(
                      selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());

              if ((selectedFilePath.lastIndexOf(".") + 1) != 0
                  && (extensions.contains(extension))) {
                textAreaFiles.setText(
                    textAreaFiles.getText() + selectedFile.getAbsolutePath() + "\n");
                deleteFile.setDisable(false);
              } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Bad File extension");
                alert.setContentText("Please select only files with gcc valid extensions.");
                alert.showAndWait();
              }
            }
          }
        };

    addFile.setOnAction(eventAddFiles);

    deleteFile.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            String textAreaModify = textAreaFiles.getText();

            if (!textAreaModify.contentEquals(""))
              textAreaModify = textAreaModify.substring(0, (textAreaModify.lastIndexOf("\n")));
            textAreaModify = textAreaModify.substring(0, (textAreaModify.lastIndexOf("\n") + 1));
            textAreaFiles.setText(textAreaModify);
            if (textAreaModify.equals("")) deleteFile.setDisable(true);
          }
        });

    HBox hbFileOptions = new HBox();
    hbFileOptions.getChildren().addAll(labelFiles, addFile, deleteFile);
    hbFileOptions.setSpacing(10);
    return hbFileOptions;
  }

  public HBox createLibraries(String textLibraries) {
    Label labelLibraries = new Label("Dependency Libraries");
    Button addLibrary = new Button("+");
    Button deleteLibrary = new Button("-");

    textAreaLibraries = new TextArea();
    textAreaLibraries.setEditable(false);
    textAreaLibraries.setText(textLibraries);
    if (textAreaLibraries.getText().equals("")) deleteLibrary.setDisable(true);

    FileChooser selectLibrary = new FileChooser();
    EventHandler<ActionEvent> eventAddLibraries =
        new EventHandler<ActionEvent>() {

          public void handle(ActionEvent e) {

            // get the library selected
            File selectedLibrary = selectLibrary.showOpenDialog(Main.getStage());

            if (selectedLibrary != null) {
              String selectedLibraryPath = selectedLibrary.getAbsolutePath();
              String extensionLibrary =
                  selectedLibraryPath.substring(
                      selectedLibraryPath.lastIndexOf(".") + 1, selectedLibraryPath.length());

              if ((selectedLibraryPath.lastIndexOf(".") + 1) != 0
                  && (extensionLibrary.equals("o")
                      || extensionLibrary.equals("a")
                      || extensionLibrary.equals("so"))) {
                textAreaLibraries.setText(
                    textAreaLibraries.getText() + selectedLibrary.getAbsolutePath() + "\n");
                deleteLibrary.setDisable(false);
              } else {
                Alert alertLibrary = new Alert(AlertType.INFORMATION);
                alertLibrary.setTitle("Information Dialog");
                alertLibrary.setHeaderText("Bad Library extension");
                alertLibrary.setContentText(
                    "Please select only files with this extensions: .a, .so or .o");
                alertLibrary.showAndWait();
              }
            }
          }
        };

    addLibrary.setOnAction(eventAddLibraries);

    deleteLibrary.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            String textAreaModify = textAreaLibraries.getText();

            if (!textAreaModify.contentEquals(""))
              textAreaModify = textAreaModify.substring(0, (textAreaModify.lastIndexOf("\n")));
            textAreaModify = textAreaModify.substring(0, (textAreaModify.lastIndexOf("\n") + 1));
            textAreaLibraries.setText(textAreaModify);
            if (textAreaModify.equals("")) deleteLibrary.setDisable(true);
          }
        });

    HBox hbLibrariesOptions = new HBox();
    hbLibrariesOptions.getChildren().addAll(labelLibraries, addLibrary, deleteLibrary);
    hbLibrariesOptions.setSpacing(10);
    return hbLibrariesOptions;
  }

  public void updateProject(String name, String files, String librariesInput) {

    LinkedList<String> sourceFiles = this.getSourceFiles(this.textAreaFiles.getText());
    LinkedList<String> objectFiles = this.getObjectFiles(this.textAreaFiles.getText());
    LinkedList<String> libraries = this.createLibraryVariables();
    if (sourceFiles != null && objectFiles != null && libraries != null) {
      if (this.textAreaFiles.getText().equals("")) {
        Alert alertLibrary = new Alert(AlertType.ERROR);
        alertLibrary.setTitle("Error Dialog");
        alertLibrary.setHeaderText("The project was not created");
        alertLibrary.setContentText("Please provide the mandatory fields.");
        alertLibrary.showAndWait();

      } else {
        Project pr = this.smartModel.updateProject(name, sourceFiles, objectFiles, libraries);
        ScrollPane scroll = this.setProjectInfo(pr);
        for (TabProjectPane tab : this.projects) {
          if (tab.getText().equals(pr.getName())) {
            tab.setScroll(scroll);
            tab.setContent(scroll);
          }
        }

        Stage stage = (Stage) this.textAreaFiles.getScene().getWindow();
        stage.close();
      }
    }
  }

  @FXML
  public void saveListProject() {
    FileOutputStream fileOutputStream;
    try {

      fileOutputStream =
          new FileOutputStream(
              new File("saveProject" + File.separator + "listOfProjects.txt").getAbsolutePath());
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(this.smartModel.getListProjects());
      objectOutputStream.flush();
      objectOutputStream.close();
    } catch (Exception e) {
      System.out.println("Problems saving project");
    }
  }

  @FXML
  public void saveProject() {
    FileOutputStream fileOutputStream;
    String name = this.projectsPane.getSelectionModel().getSelectedItem().getText();
    Project pr = this.smartModel.getProject(name);
    try {
      String directory = pr.getProjectLocation() + File.separator + pr.getName() + "SmartGcc";
      System.out.println(directory);
      fileOutputStream = new FileOutputStream(new File(directory).getAbsolutePath());
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(pr);
      objectOutputStream.flush();
      objectOutputStream.close();
    } catch (Exception e) {
      System.out.println("Problems saving project");
    }
  }

  public void loadListOfProject() {
    FileInputStream fileInputStream;
    LinkedList<Project> listProject = null;
    try {
      fileInputStream =
          new FileInputStream(
              new File("saveProject" + File.separator + "listOfProjects.txt").getAbsolutePath());
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      listProject = (LinkedList<Project>) objectInputStream.readObject();

      objectInputStream.close();
    } catch (Exception e) {
      System.out.println("No file found");
    } finally {
      if (listProject == null) this.smartModel.setListProjects(new LinkedList<Project>());
      else {
        this.smartModel.setListProjects(listProject);
        for (Project pr : listProject) {
          this.createProjectOnView(pr);
        }
      }
    }
  }

  /**
   * Creates the edit Project Panel
   *
   * @param pr
   */
  private void generateEditProjectPanel(Project pr) {

    this.secondaryLayout = new StackPane();

    // Project Directory
    Label labelProjectDirectory = new Label("Project Directory:");

    inputProjectDirectory = new TextField();
    inputProjectDirectory.setText(pr.getProjectLocation());
    inputProjectDirectory.setDisable(true);

    HBox hbProjectDirectory = new HBox();
    hbProjectDirectory.getChildren().addAll(labelProjectDirectory, inputProjectDirectory);
    hbProjectDirectory.setSpacing(10);

    // Project Name
    Label labelProjectName = new Label("Project Name:");
    inputProjectName = new RulesTextField();
    inputProjectName.setMinSize(150, 25);
    inputProjectName.setText(pr.getName());
    inputProjectName.setDisable(true);
    HBox hbProjectName = new HBox();
    hbProjectName.getChildren().addAll(labelProjectName, inputProjectName);
    hbProjectName.setSpacing(10);

    // Files
    String textFiles =
        CommandExecute.generateStringFromList(pr.getSourceFiles())
            + CommandExecute.generateStringFromList(pr.getObjectFiles());
    HBox hbFileOptions = this.createFileOptions(textFiles);

    // Libraries
    String textLibraries = CommandExecute.generateStringFromList(pr.getLibraries());
    HBox hbLibrariesOptions = this.createLibraries(textLibraries);

    // Create and Cancel Button
    Button createButton = new Button("Update");
    Button cancelButton = new Button("Cancel");

    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
          }
        });

    createButton.setOnAction(
        e -> updateProject(pr.getName(), textAreaFiles.getText(), textAreaLibraries.getText()));

    HBox hbFinalButtons = new HBox();
    hbFinalButtons.getChildren().addAll(createButton, cancelButton);
    hbFinalButtons.setAlignment(Pos.BASELINE_CENTER);
    hbFinalButtons.setPadding(new Insets(10, 0, 0, 0));
    hbFinalButtons.setSpacing(10);

    VBox newProject = new VBox();
    newProject
        .getChildren()
        .addAll(
            hbProjectDirectory,
            hbProjectName,
            hbFileOptions,
            textAreaFiles,
            hbLibrariesOptions,
            textAreaLibraries,
            hbFinalButtons);
    newProject.setPadding(new Insets(10, 50, 50, 50));
    newProject.setSpacing(10);

    // Adding elements
    this.secondaryLayout.getChildren().add(newProject);
    Scene secondScene = new Scene(this.secondaryLayout, 500, 500);

    // New window (Stage)
    Stage newWindow = new Stage();
    newWindow.initModality(Modality.WINDOW_MODAL);
    newWindow.initOwner(Main.getStage());
    newWindow.setTitle("Update Project");
    newWindow.setScene(secondScene);

    // Set position of second window, related to primary window.
    newWindow.setX(Main.getStage().getX() + 300);
    newWindow.setY(Main.getStage().getY() + 100);

    newWindow.show();
  }

  public ScrollPane setProjectInfo(Project project) {
    ScrollPane scrollPane = new ScrollPane();

    Button update = new Button("Edit");
    update.setOnAction(e -> generateEditProjectPanel(project));
    HBox buttonBox = new HBox(update);
    buttonBox.setPadding(new Insets(30, 0, 0, 0));

    String styleBold = "-fx-font-weight: bold";

    Label projectName = new Label("Project Name:");
    projectName.setPadding(new Insets(20, 0, 0, 0));
    Label directoryName = new Label("Project Directory:");
    directoryName.setPadding(new Insets(20, 0, 0, 0));
    Label projectFiles = new Label("Project Files:");
    projectFiles.setPadding(new Insets(20, 0, 0, 0));
    Label projectLibraries = new Label("Project Libraries:");
    projectLibraries.setPadding(new Insets(20, 0, 0, 0));

    projectName.setStyle(styleBold);
    directoryName.setStyle(styleBold);
    projectFiles.setStyle(styleBold);
    projectLibraries.setStyle(styleBold);

    VBox leftControl = new VBox();
    leftControl
        .getChildren()
        .addAll(
            projectName,
            new Label(project.getName()),
            directoryName,
            new Label(project.getProjectLocation()),
            projectFiles,
            new Label(
                CommandExecute.generateStringFromList(project.getSourceFiles())
                    + CommandExecute.generateStringFromList((project.getObjectFiles()))),
            projectLibraries,
            new Label(CommandExecute.generateStringFromList(project.getLibraries())),
            buttonBox);

    scrollPane.setContent(leftControl);
    return scrollPane;
  }

  public void createProjectOnView(Project project) {

    TabProjectPane tab = new TabProjectPane(project.getName());
    ScrollPane scrollPane = this.setProjectInfo(project);
    scrollPane.setMinSize(300, 300);
    tab.setContent(scrollPane);
    tab.setScroll(scrollPane);
    tab.setOnCloseRequest(
        e -> {
          String name = tab.getText();
          System.out.println(name);
          this.smartModel.deleteProject(name);
          this.projects.remove(tab);
        });
    this.projects.add(tab);
    this.projectsPane.getTabs().add(tab);
    this.loadCompilingOptionPanels(project);
  }

  public void loadCompilingOptionPanels(Project pr) {
    this.loadOptimization(pr);
    this.loadDebugging(pr);
  }

  public void loadOptimization(Project pr) {}

  public void loadDebugging(Project pr) {}

  public int createProjectDirectory() {
    File file =
        new File(
            this.inputProjectDirectory.getText()
                + File.separator
                + this.inputProjectName.getText());

    if (!file.exists()) {
      if (file.mkdir()) {
        System.out.println("Directory is created!");
      }
    } else {
      Alert alertLibrary = new Alert(AlertType.ERROR);
      alertLibrary.setTitle("Error Dialog");
      alertLibrary.setHeaderText("The project was not created");
      alertLibrary.setContentText(
          "Please change the project name, there is a directory with the project name you introduce.");
      alertLibrary.showAndWait();
      this.smartModel.deleteProject(this.inputProjectName.getText());
      return -1;
    }

    return 0;
  }

  public LinkedList<String> getSourceFiles(String files) {
    String filesArray[] = files.split("\n");
    LinkedList<String> sourceFiles = new LinkedList<String>();

    for (int i = 0; i < filesArray.length; i++) {
      if ((!filesArray[i].equals(""))
          && !(filesArray[i]
              .substring(filesArray[i].lastIndexOf(".") + 1, filesArray[i].length())
              .contentEquals("o"))) {
        if (sourceFiles.contains(filesArray[i])) {
          Alert alertFileRep = new Alert(AlertType.ERROR);
          alertFileRep.setTitle("Error Dialog");
          alertFileRep.setHeaderText("The project was not created");
          alertFileRep.setContentText("You repeated files.");
          alertFileRep.showAndWait();
          return null;
        } else {
          sourceFiles.add(filesArray[i]);
        }
      }
    }

    return sourceFiles;
  }

  /**
   * Produces a list of object files
   *
   * @param files
   * @return
   */
  public LinkedList<String> getObjectFiles(String files) {
    String filesArray[] = files.split("\n");
    LinkedList<String> objectFiles = new LinkedList<String>();

    for (int i = 0; i < filesArray.length; i++) {
      if ((!filesArray[i].equals(""))
          && (filesArray[i]
              .substring(filesArray[i].lastIndexOf(".") + 1, filesArray[i].length())
              .contentEquals("o"))) {
        if (objectFiles.contains(filesArray[i])) {
          Alert alertFileRep = new Alert(AlertType.ERROR);
          alertFileRep.setTitle("Error Dialog");
          alertFileRep.setHeaderText("The project was not created");
          alertFileRep.setContentText("You repeated files.");
          alertFileRep.showAndWait();
          return null;
        } else {
          objectFiles.add(filesArray[i]);
        }
      }
    }

    return objectFiles;
  }

  /**
   * Needs to be implemented
   *
   * @return
   */
  public LinkedList<String> createLibraryVariables() {
    return new LinkedList<String>();
  }

  @FXML
  private void onSpecialAction() {
    Node n = Main.getScene().lookup(".linking-libraries");
    Set<Node> m = Main.getScene().getRoot().lookupAll(".simple-opt");
    List<String> args = new ArrayList<String>();
    for (Node mm : m) {
      if (mm instanceof CheckBox && ((CheckBox) mm).isSelected()) {
        args.add(((CheckBox) mm).getText());
        System.out.println(((CheckBox) mm).getText());
      }
    }
  }

  @FXML
  private int openProject() {
    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(Main.getStage());
    int i = 0;

    if (selectedFile != null) {
      String name =
          selectedFile
              .getAbsolutePath()
              .substring(selectedFile.getAbsolutePath().lastIndexOf(File.separator) + 1)
              .split("SmartGcc")[0];
      System.out.println(name);
      for (Tab tab : this.projectsPane.getTabs()) {
        if (tab.getText().equals(name)) {
          i = 1;
        }
      }

      if (i == 1) {
        Alert alertLibrary = new Alert(AlertType.ERROR);
        alertLibrary.setTitle("Error Dialog");
        alertLibrary.setContentText("The project is already open.");
        alertLibrary.showAndWait();
        return -1;
      }

      Project pr = null;
      FileInputStream fileInputStream;
      try {
        fileInputStream =
            new FileInputStream(new File(selectedFile.getAbsolutePath()).getAbsolutePath());
        System.out.println(selectedFile.getAbsolutePath());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        pr = (Project) objectInputStream.readObject();

        if (this.smartModel.addProject(pr) == -1) {
          Alert alertLibrary = new Alert(AlertType.ERROR);
          alertLibrary.setTitle("Error Dialog");
          alertLibrary.setContentText("The project was not saved. It can not be open.");
          alertLibrary.showAndWait();
          return -1;
        } else {
          this.createProjectOnView(pr);
        }

        objectInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 0;
  }
}
