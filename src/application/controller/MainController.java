package application.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import application.Main;
import application.model.CommandExecute;
import application.model.Project;
import application.model.SmartModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	@FXML
    private Button runOption;
     
    @FXML
    private Button compileOption;
    
    @FXML
    private TabPane projectsPane;
    
    StackPane secondaryLayout;
    
    //Elements to create project
    TextField inputProjectDirectory;
    RulesTextField inputProjectName;
    TextArea textAreaFiles;
    TextArea textAreaLibraries;
    
	public MainController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	public void initialize() {
		
		this.smartModel=new SmartModel();
		this.commandExecute=new CommandExecute();
	}
    
	@FXML
	private void close(){
		System.exit(0);
	}
	
	@FXML
	private void buildProject(){
		
		SingleSelectionModel<Tab> selectionModel = projectsPane.getSelectionModel();
		Project prCompile=smartModel.getProject(selectionModel.getSelectedItem().getText());
		//System.out.println(prCompile.getName());
		try {
			this.commandExecute.buildProject(prCompile,"");
		} catch (Exception e) {
			System.out.println("the selected tab doesn't exist in the projects list");
		}
	}
	
	@FXML
	private void generateNewProjectPanel() {
		
		 this.secondaryLayout= new StackPane();
		 
		 // Include elements of New Project Panel
		 
		 //Project Directory
		 Label labelProjectDirectory = new Label("Project Directory:");
		
		 inputProjectDirectory = new TextField ();
		 inputProjectDirectory.setDisable(true);
		 
		 Button submit = new Button("Select");
		 
		 DirectoryChooser directoryChooser = new DirectoryChooser();
		 
		 EventHandler<ActionEvent> event =  
		            new EventHandler<ActionEvent>() { 
		  
		                public void handle(ActionEvent e) 
		                { 
		  
		                    // get the file selected 
		                	File selectedDirectory=directoryChooser.showDialog(Main.getStage()); 
		  
		                    if (selectedDirectory != null) { 
		                    	inputProjectDirectory.setText(selectedDirectory.getAbsolutePath()); 
		                    } 
		                } 
		            }; 
		            
		 submit.setOnAction(event);
		 
		 
		 HBox hbProjectDirectory = new HBox();
		 hbProjectDirectory.getChildren().addAll(labelProjectDirectory, inputProjectDirectory, submit);
		 hbProjectDirectory.setSpacing(10);
		 
		 //Project Name
		 Label labelProjectName = new Label("Project Name:");
		 inputProjectName = new RulesTextField();
		 inputProjectName.setMinSize(150, 25);
		 HBox hbProjectName = new HBox();
		 hbProjectName.getChildren().addAll(labelProjectName, inputProjectName);
		 hbProjectName.setSpacing(10);
		 
		 //Files
		 Label labelFiles = new Label("Dependency Files");
		 Button addFile = new Button("+");
		 Button deleteFile =new Button("-");
		 deleteFile.setDisable(true);
		 textAreaFiles = new TextArea();
		 textAreaFiles.setDisable(true);
		 FileChooser selectFile=new FileChooser();
		 EventHandler<ActionEvent> eventAddFiles =  
		            new EventHandler<ActionEvent>() { 
		  
		                public void handle(ActionEvent e) 
		                { 
		  
		                    // get the file selected 
		                	File selectedFile=selectFile.showOpenDialog(Main.getStage()); 
		  
		                    if (selectedFile != null) {
		                    	String selectedFilePath=selectedFile.getAbsolutePath();
		                    	
		                    	String extension=selectedFilePath.substring(selectedFilePath.lastIndexOf(".")+1,selectedFilePath.length());
		                    	
		                    	if((selectedFilePath.lastIndexOf(".")+1)!=0 && (extension.equals("o") || extension.equals("c") || extension.equals("h"))){
		                    		textAreaFiles.setText(textAreaFiles.getText()+selectedFile.getAbsolutePath()+"\n");
		                    		deleteFile.setDisable(false);
		                    	}else {
		                    		Alert alert = new Alert(AlertType.INFORMATION);
		                    		alert.setTitle("Information Dialog");
		                    		alert.setHeaderText("Bad File extension");
		                    		alert.setContentText("Please select only files with this extensions: .h, .c or .o");
		                    		alert.showAndWait();
		                    	}
		                    	
		                    } 
		                } 
		            };
		 
		 addFile.setOnAction(eventAddFiles);
		 
		 deleteFile.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String textAreaModify=textAreaFiles.getText();
			    	
			    	if(!textAreaModify.contentEquals(""))
			    		textAreaModify=textAreaModify.substring(0,(textAreaModify.lastIndexOf("\n")));
						textAreaModify=textAreaModify.substring(0,(textAreaModify.lastIndexOf("\n")+1));
						textAreaFiles.setText(textAreaModify);
						if(textAreaModify.equals(""))
							deleteFile.setDisable(true);
					 
			    }
			});
		 
		 
		 HBox hbFileOptions = new HBox();
		 hbFileOptions.getChildren().addAll(labelFiles, addFile, deleteFile);
		 hbFileOptions.setSpacing(10);
		 
		 //Libraries
		 
		 Label labelLibraries = new Label("Dependency Libraries");
		 Button addLibrary = new Button("+");
		 Button deleteLibrary =new Button("-");
		 deleteLibrary.setDisable(true);
		 textAreaLibraries = new TextArea();
		 textAreaLibraries.setDisable(true);
		 FileChooser selectLibrary=new FileChooser();
		 EventHandler<ActionEvent> eventAddLibraries =  
		            new EventHandler<ActionEvent>() { 
		  
		                public void handle(ActionEvent e) 
		                { 
		  
		                    // get the library selected 
		                	File selectedLibrary=selectLibrary.showOpenDialog(Main.getStage()); 
		  
		                    if (selectedLibrary != null) {
		                    	String selectedLibraryPath=selectedLibrary.getAbsolutePath();
		                    	String extensionLibrary=selectedLibraryPath.substring(selectedLibraryPath.lastIndexOf(".")+1,selectedLibraryPath.length());
		                    	
		                    	if((selectedLibraryPath.lastIndexOf(".")+1)!=0 && (extensionLibrary.equals("o") || extensionLibrary.equals("a") || extensionLibrary.equals("so"))){
		                    		textAreaLibraries.setText(textAreaLibraries.getText()+selectedLibrary.getAbsolutePath()+"\n");
		                    		deleteLibrary.setDisable(false);
		                    	}else {
		                    		Alert alertLibrary = new Alert(AlertType.INFORMATION);
		                    		alertLibrary.setTitle("Information Dialog");
		                    		alertLibrary.setHeaderText("Bad Library extension");
		                    		alertLibrary.setContentText("Please select only files with this extensions: .a, .so or .o");
		                    		alertLibrary.showAndWait();
		                    	}
		                    	
		                    } 
		                } 
		            };
		 
		 addLibrary.setOnAction(eventAddLibraries);
		 
		 deleteLibrary.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String textAreaModify=textAreaLibraries.getText();
			    	
			    	if(!textAreaModify.contentEquals(""))
			    		textAreaModify=textAreaModify.substring(0,(textAreaModify.lastIndexOf("\n")));
						textAreaModify=textAreaModify.substring(0,(textAreaModify.lastIndexOf("\n")+1));
						textAreaLibraries.setText(textAreaModify);
						if(textAreaModify.equals(""))
							deleteLibrary.setDisable(true);
					 
			    }
			});
		 
		 
		 HBox hbLibrariesOptions = new HBox();
		 hbLibrariesOptions.getChildren().addAll(labelLibraries, addLibrary, deleteLibrary);
		 hbLibrariesOptions.setSpacing(10);
		 
		 //Create and Cancel Button
		 Button createButton = new Button("Create");
		 Button cancelButton = new Button("Cancel");
		 
		 cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	Stage stage = (Stage) cancelButton.getScene().getWindow();
			    	stage.close();
			    }
			});
		 
		 createButton.setOnAction(this::createProject);
		
		 HBox hbFinalButtons = new HBox();
		 hbFinalButtons.getChildren().addAll(createButton, cancelButton);
		 hbFinalButtons.setAlignment(Pos.BASELINE_CENTER);
		 hbFinalButtons.setSpacing(10);
		 
		 VBox newProject=new VBox();
		 newProject.getChildren().addAll(hbProjectDirectory,hbProjectName,hbFileOptions,textAreaFiles,hbLibrariesOptions,textAreaLibraries,hbFinalButtons);
		 newProject.setPadding(new Insets(10, 50, 50, 50));
		 newProject.setSpacing(10);
		 
		 
		 //Adding elements
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
		
		String sourceFiles=this.getSourceFiles(this.textAreaFiles.getText());
		String objectFiles=this.getObjectFiles(this.textAreaFiles.getText());
		String libraries=this.createLibraryVariables();
		if(!sourceFiles.equals("-1") && !objectFiles.equals("-1") && !libraries.equals("-1")) {
			if(this.inputProjectDirectory.getText().equals("") || this.inputProjectName.getText().equals("") || this.textAreaFiles.getText().equals("")) {
				Alert alertLibrary = new Alert(AlertType.ERROR);
				alertLibrary.setTitle("Error Dialog");
				alertLibrary.setHeaderText("The project was not created");
				alertLibrary.setContentText("Please provide the mandatory fields.");
				alertLibrary.showAndWait();

			}else {
				if(this.smartModel.createProject(this.inputProjectName.getText(), sourceFiles, objectFiles, libraries,this.inputProjectDirectory.getText()+File.separator+this.inputProjectName.getText())==-1) {
					Alert alertLibrary = new Alert(AlertType.ERROR);
					alertLibrary.setTitle("Error Dialog");
					alertLibrary.setHeaderText("The project was not created");
					alertLibrary.setContentText("Please change the project name, there is a project with that name");
					alertLibrary.showAndWait();
				}else {
					Stage stage = (Stage) this.inputProjectName.getScene().getWindow();
					if(this.createProjectDirectory()==0) {
						Tab tab = new Tab(this.inputProjectName.getText());
						this.projectsPane.getTabs().add(tab);
						stage.close();
					}
				}
			}
		}
		//System.out.println(this.smartModel.getListProjects().getFirst().getName());
		//System.out.println(this.smartModel.getListProjects().getFirst().getSourceFiles());
		//System.out.println(this.smartModel.getListProjects().getFirst().getProjectLocation());
	}
	
	public int createProjectDirectory() {
		File file = new File(this.inputProjectDirectory.getText()+File.separator+this.inputProjectName.getText());
        
		if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } 
		}else{
            Alert alertLibrary = new Alert(AlertType.ERROR);
        	alertLibrary.setTitle("Error Dialog");
        	alertLibrary.setHeaderText("The project was not created");
        	alertLibrary.setContentText("Please change the project name, there is a directory with the project name you introduce.");
        	alertLibrary.showAndWait();
        	this.smartModel.deleteProject(this.inputProjectName.getText());
        	return -1;
         }
        
		return 0;
	}
	
	public String getSourceFiles(String files) {
		String filesArray[]=files.split("\n");
		LinkedList<String> sourceFiles=new LinkedList<String>();
		String sourceFilesStr="";
		for(int i=0;i<filesArray.length;i++) {	
			if((!filesArray[i].equals("")) && !(filesArray[i].substring(filesArray[i].lastIndexOf(".")+1,filesArray[i].length()).contentEquals("o"))) {
				if(sourceFiles.contains(filesArray[i])) {
					Alert alertFileRep = new Alert(AlertType.ERROR);
					alertFileRep.setTitle("Error Dialog");
					alertFileRep.setHeaderText("The project was not created");
					alertFileRep.setContentText("You repeated files.");
					alertFileRep.showAndWait();
	        		return "-1";
				}else {
					sourceFiles.add(filesArray[i]);
				}
			}
		}
		
		for(int i=0;i<sourceFiles.size();i++)
			sourceFilesStr=sourceFilesStr+sourceFiles.get(i)+" ";
		
		
		//System.out.println(sourceFilesStr);
		return sourceFilesStr;
	}
	
	public String getObjectFiles(String files) {
		String filesArray[]=files.split("\n");
		LinkedList<String> objectFiles=new LinkedList<String>();
		String objectFilesStr="";
		for(int i=0;i<filesArray.length;i++) {	
			if((!filesArray[i].equals("")) && (filesArray[i].substring(filesArray[i].lastIndexOf(".")+1,filesArray[i].length()).contentEquals("o"))) {
				if(objectFiles.contains(filesArray[i])) {
					Alert alertFileRep = new Alert(AlertType.ERROR);
					alertFileRep.setTitle("Error Dialog");
					alertFileRep.setHeaderText("The project was not created");
					alertFileRep.setContentText("You repeated files.");
					alertFileRep.showAndWait();
					return "-1";
				}else {
					objectFiles.add(filesArray[i]);
				}
			}
		}
		
		for(int i=0;i<objectFiles.size();i++)
			objectFilesStr=objectFilesStr+objectFiles.get(i)+" ";
		
		return objectFilesStr;
	}
	
	public String createLibraryVariables() {
		return "";
	}
	
	@FXML
	private void openProject() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(Main.getStage());
		System.out.println(selectedDirectory.getAbsolutePath());
	}
	
}
