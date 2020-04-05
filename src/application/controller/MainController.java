package application.controller;

import application.Main;
import application.controller.optiontabs.CodegenOptsController;
import application.controller.optiontabs.CompilerOptsController;
import application.controller.optiontabs.DebuggingOptsController;
import application.controller.optiontabs.DeveloperOptsController;
import application.controller.optiontabs.ExecuteOptsController;
import application.controller.optiontabs.LinkingOptsController;
import application.controller.optiontabs.OptimizationOptsController;
import application.controller.optiontabs.RunningProjectController;
import application.model.CommandExecute;
import application.model.Project;
import application.model.SmartModel;
import application.views.RulesTextField;
import application.views.TabProjectPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

  SmartModel smartModel;
  public CommandExecute commandExecute;
  LinkedList<TabProjectPane> projects;
  @FXML private Button runOption;
  @FXML private Button compileOption;
  @FXML private TabPane panelCompilingOptions;

  int type;

  @FXML private TabPane projectsPane;
  @FXML Tab textResult;

  StackPane secondaryLayout;

  TextField inputProjectDirectory;
  RulesTextField inputProjectName;
  TextArea textAreaFiles;
  private TextArea textAreaResult;

  // Option-Tab Components and Controllers
  @FXML private Parent compilerOpts;
  @FXML private CompilerOptsController compilerOptsController;
  @FXML private Parent linkingOpts;
  @FXML private LinkingOptsController linkingOptsController;
  @FXML private Parent debuggingOpts;
  @FXML private DebuggingOptsController debuggingOptsController;
  @FXML private Parent executeOpts;
  @FXML private ExecuteOptsController executeOptsController;
  @FXML private Parent codegenOpts;
  @FXML private CodegenOptsController codegenOptsController;
  @FXML private Parent optimizationOpts;
  @FXML private OptimizationOptsController optimizationOptsController;
  @FXML private Parent developerOpts;
  @FXML private DeveloperOptsController developerOptsController;
  @FXML private ChooseUserController chooseUserController;
  @FXML public RunningProjectController runningProjectController;

  // Tabs
  @FXML private Tab developerOptions;
  @FXML private Tab optimizationTab;
  @FXML private Tab codeGenerationOptions;
  @FXML private Tab codeExecuteOptions;
  @FXML private Tab debuggingOptions;
  @FXML private Tab linkingOptions;
  @FXML private Tab compilerOptions;
  // Check Menu Items
  @FXML private CheckMenuItem compilingCheck;
  @FXML private CheckMenuItem linkingCheck;
  @FXML private CheckMenuItem executingCheck;
  @FXML private CheckMenuItem debuggingCheck;
  @FXML private CheckMenuItem codeGenerationCheck;
  @FXML private CheckMenuItem codeOptimizationCheck;
  @FXML private CheckMenuItem developerCheck;

  public MainController() {}

  @FXML
  public void initialize() {
    System.out.println("maincontroller initialized");
    this.smartModel = new SmartModel();
    this.commandExecute = new CommandExecute(this);
    this.projects = new LinkedList<TabProjectPane>();
    this.textAreaResult = new TextArea();
    this.textAreaResult.setEditable(false);
    this.textResult.setContent(textAreaResult);
    this.runningProjectController.setMain(this);
  }

  @FXML
  private void close() {
    //    optimizationPanelController.getOptimizationFlags();
    //    debugPanelController.getDebugFlags();
    System.exit(0);
  }

  private String buildArgStr() {
    List<String> args = new ArrayList<String>();
    String argsStr = "";
    // "Simple-Opt" Collection
    Set<Node> m = Main.getScene().getRoot().lookupAll(".simple-opt");
    for (Node mm : m) {
      // Checkbox
      boolean isCheckBoxSelected = mm instanceof CheckBox && ((CheckBox) mm).isSelected();
      if (isCheckBoxSelected) {
        args.add(((CheckBox) mm).getText());
        argsStr = argsStr + ((CheckBox) mm).getText() + " ";
      }
      // ToggleButton (includes RadioButton)
      boolean isToggleButtonSelected =
          mm instanceof ToggleButton && ((ToggleButton) mm).isSelected();
      if (isToggleButtonSelected) {
        args.add(((ToggleButton) mm).getText());
        argsStr = argsStr + ((ToggleButton) mm).getText() + " ";
      }
    }
    return argsStr;
  }

  @FXML
  private void buildProject() {
    if (projectsPane.getTabs().size() > 0) {
      SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
      Project prCompile = smartModel.getProject(selectionModel.getSelectedItem().getText());
      addFlagsToProject(prCompile);
      // System.out.println(prCompile.getName());
      String argStr = buildArgStr();
      String libs = linkingOptsController.buildLibs();
      try {
        String[] result = this.commandExecute.buildProject(prCompile, argStr, libs);
        this.textAreaResult.setText(
            this.textAreaResult.getText()
                + "\n"
                + "The executed command was:\n"
                + result[0]
                + "\n"
                + "The operation was:\n"
                + result[1]
                + "The result was:\n"
                + result[2]);
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
  private void onNewProjectAction() {

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
        .addAll(hbProjectDirectory, hbProjectName, hbFileOptions, textAreaFiles, hbFinalButtons);
    newProject.setPadding(new Insets(10, 50, 50, 50));
    newProject.setSpacing(10);

    // Adding elements
    this.secondaryLayout.getChildren().add(newProject);
    Scene secondScene = new Scene(this.secondaryLayout, 500, 400);

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
        new LinkedList<String>(Arrays.asList("c", "h", "o", "C", "cpp", "CPP", "c++", "cp", "cxx"));
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

  public void updateProject(String name, String files) {

    LinkedList<String> sourceFiles = this.getSourceFiles(this.textAreaFiles.getText());
    LinkedList<String> objectFiles = this.getObjectFiles(this.textAreaFiles.getText());

    if (sourceFiles != null && objectFiles != null) {
      if (this.textAreaFiles.getText().equals("")) {
        Alert alertLibrary = new Alert(AlertType.ERROR);
        alertLibrary.setTitle("Error Dialog");
        alertLibrary.setHeaderText("The project was not created");
        alertLibrary.setContentText("Please provide the mandatory fields.");
        alertLibrary.showAndWait();

      } else {
        Project pr = this.smartModel.updateProject(name, sourceFiles, objectFiles);
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

  public void updateProjectArgs() {
    if (this.projectsPane.getTabs().size() > 0) {
      String projectFlags = this.buildArgStr();
      String name = this.projectsPane.getSelectionModel().getSelectedItem().getText();
      Project pr = this.smartModel.getProject(name);
      pr.setAllFlags(projectFlags);
      System.out.println(name);
      System.out.println(pr.allFlags());
    }
  }

  @FXML
  public void onSaveProjectAction() {
    if (this.projectsPane.getTabs().size() > 0) {
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
    } else {
      Alert alertLibrary = new Alert(AlertType.ERROR);
      alertLibrary.setTitle("Error Dialog");
      alertLibrary.setHeaderText("Please create a Project");
      alertLibrary.setContentText("Please create a project first.");
      alertLibrary.showAndWait();
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

    createButton.setOnAction(e -> updateProject(pr.getName(), textAreaFiles.getText()));

    HBox hbFinalButtons = new HBox();
    hbFinalButtons.getChildren().addAll(createButton, cancelButton);
    hbFinalButtons.setAlignment(Pos.BASELINE_CENTER);
    hbFinalButtons.setPadding(new Insets(10, 0, 0, 0));
    hbFinalButtons.setSpacing(10);

    VBox newProject = new VBox();
    newProject
        .getChildren()
        .addAll(hbProjectDirectory, hbProjectName, hbFileOptions, textAreaFiles, hbFinalButtons);
    newProject.setPadding(new Insets(10, 50, 50, 50));
    newProject.setSpacing(10);

    // Adding elements
    this.secondaryLayout.getChildren().add(newProject);
    Scene secondScene = new Scene(this.secondaryLayout, 500, 400);

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

    projectName.setStyle(styleBold);
    directoryName.setStyle(styleBold);
    projectFiles.setStyle(styleBold);

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
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Confirmation Dialog");
          alert.setHeaderText("Please confirm this action.");
          alert.setContentText("Are you sure you want to delete the project?");

          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            String name = tab.getText();
            System.out.println(name);
            this.smartModel.deleteProject(name);
            this.projects.remove(tab);
          } else {
            e.consume();
          }
        });

    this.projectsPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(e -> this.updateProjectArgs());
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
  private int onOpenProjectAction() {
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

  private void addFlagsToProject(Project project) {
    // add optimization options to the project
    //    project.setOptimizationFlags(optimizationPanelController.getOptimizationFlags());
    //
    //    project.setDebugFlags(debugPanelController.getDebugFlags());
  }

  @FXML
  void handleAllOptions(ActionEvent event) {

    CheckMenuItem sourceButton = (CheckMenuItem) event.getSource();
    String compilationPanel = sourceButton.getText();

    switch (compilationPanel) {
      case "Compiler Options":
        if (containTab(this.compilerOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.compilerOptions);
        if (!sourceButton.isSelected() && containTab(this.compilerOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.compilerOptions);
        break;
      case "Linking Options":
        if (containTab(this.linkingOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.linkingOptions);
        if (!sourceButton.isSelected() && containTab(this.linkingOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.linkingOptions);
        break;
      case "Execute Options":
        if (containTab(this.codeExecuteOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.codeExecuteOptions);
        if (!sourceButton.isSelected() && containTab(this.codeExecuteOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.codeExecuteOptions);
        break;
      case "Debugging Options":
        if (containTab(this.debuggingOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.debuggingOptions);
        if (!sourceButton.isSelected() && containTab(this.debuggingOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.debuggingOptions);
        break;
      case "Code Generation Options":
        if (containTab(this.codeGenerationOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.codeGenerationOptions);
        if (!sourceButton.isSelected() && containTab(this.codeGenerationOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.codeGenerationOptions);
        break;
      case "Code Optimization Options":
        if (containTab(this.optimizationTab.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.optimizationTab);
        if (!sourceButton.isSelected() && containTab(this.optimizationTab.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.optimizationTab);
        break;
      case "Developer Options":
        if (containTab(this.developerOptions.getText()) == -1 && sourceButton.isSelected())
          this.panelCompilingOptions.getTabs().add(this.developerOptions);
        if (!sourceButton.isSelected() && containTab(this.developerOptions.getText()) == 0)
          this.panelCompilingOptions.getTabs().remove(this.developerOptions);
        break;
      default:
        break;
    }
  }

  public int containTab(String name) {

    for (Tab tab : this.panelCompilingOptions.getTabs()) {
      if (tab.getText().contentEquals(name)) return 0;
    }
    return -1;
  }

  @FXML
  public void executeProject() {
    System.out.println("Entre");
    if (projectsPane.getTabs().size() > 0) {
      SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
      Project prRun = smartModel.getProject(selectionModel.getSelectedItem().getText());
      this.commandExecute.setInputRun(this.runningProjectController.inputExecute.getText());
      try {
        String[] results = this.commandExecute.runProgram(prRun);
        this.runningProjectController.resultExecute.setText(
            this.runningProjectController.resultExecute.getText()
                + "Errors:\n"
                + results[0]
                + "\n"
                + "Results:\n"
                + results[1]
                + "\n");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void generatePanels(int i) {

    if (i == 0) {
      this.developerCheck.setSelected(false);
      this.codeGenerationCheck.setSelected(false);
      this.codeOptimizationCheck.setSelected(false);
      this.panelCompilingOptions.getTabs().remove(this.developerOptions);
      this.panelCompilingOptions.getTabs().remove(this.codeGenerationOptions);
      this.panelCompilingOptions.getTabs().remove(this.optimizationTab);

    } else if (i == 1) {
      this.panelCompilingOptions.getTabs().remove(this.developerOptions);
      this.developerCheck.setSelected(false);
    }
  }
}
