package application.controller;

import java.io.File;
import java.io.IOException;

import application.Main;
import application.model.CommandExecute;
import application.model.Project;
import application.model.SmartModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	
	SmartModel smartModel;
	CommandExecute commandExecute;
	
	@FXML
    private Button runOption;
     
    @FXML
    private Button compileOption;
    
    @FXML
    private TabPane projectsPane;
    
    StackPane secondaryLayout;
    
	public MainController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	public void initialize() {
		
		this.smartModel=new SmartModel();
		this.commandExecute=new CommandExecute();
		Project pr=new Project("Project1","/home/nellybett/Desktop/hello.c ","","","/home/nellybett/Desktop/");
		this.smartModel.addProject(pr);
	}
    
	@FXML
	private void close(){
		System.exit(0);
	}
	
	@FXML
	private void buildProject(){
		
		SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
		Project prCompile=smartModel.getProject(selectionModel.getSelectedItem().getText());
		System.out.println(prCompile.getName());
		try {
			this.commandExecute.buildProject(prCompile,"");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void generateNewProjectPanel() {
		
		 this.secondaryLayout= new StackPane();

		 // Include elements of New Project Panel
		 Label secondLabel = new Label("I'm a Label on new Window");
		 
		 
		 //Adding elements
		 this.secondaryLayout.getChildren().add(secondLabel);
	     Scene secondScene = new Scene(this.secondaryLayout, 400, 400);

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
	private void openProject() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(Main.getStage());
		System.out.println(selectedDirectory.getAbsolutePath());
	}
	
}
