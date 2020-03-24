package application.controller;

import java.io.IOException;

import application.model.CommandExecute;
import application.model.Project;
import application.model.SmartModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {
	
	SmartModel smartModel;
	CommandExecute commandExecute;
	
	@FXML
    private Button runOption;
     
    @FXML
    private Button compileOption;
    
    @FXML
    private TabPane projectsPane;
    
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
	private void compileProject(){
		
		SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
		Project prCompile=smartModel.getProject(selectionModel.getSelectedItem().getText());
		System.out.println(prCompile.getName());
		try {
			this.commandExecute.compilingProject(prCompile,"");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
