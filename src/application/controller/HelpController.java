package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HelpController implements Initializable{
	

    @FXML
    private Label compileproject;

    @FXML
    private Label text;
    
	@FXML
    void compileProject(MouseEvent event) {
		text.setText("1. Create a project \n\n2. Click Build icon \n\n3. View the output at the provided directoty location ");
    }
	
	@FXML
    void createProject(MouseEvent event) {
		text.setText("1. Inside SmartGCC select the menu item File > New >Project.... or click on the new project icon to open the New Project wizard \n\n2. Provide the location of the directory where you can check the results \n\n3. Provide the project name and click the + symbol to the file you want work on \n\n4. Click Create");
    }

	@FXML
	void buildProject(MouseEvent event) {
		text.setText("1. Create a project \n\n2. Click the Build button or press ctrl+b");
	}
	

    @FXML
    void saveProject(MouseEvent event) {
		text.setText("1. Create a project \n\n 2. Click on save icon on main panel \n\n3. Provide location  \n\n4. Click save in the dialog box ");
    }
    
    @FXML
    void options(MouseEvent event) {
		text.setText("1. Create a project \n\n2. Build the code using Build icon on the main panel \n\n3. Choose the required options \n\n4. Click Build");
    }
    
    @FXML
    void userType(MouseEvent event) {
		text.setText("Change the user type by clicling change user type icon on the main panel and select your type of user");
    }

    @FXML
    void compilerOptions(MouseEvent event) {
    	text.setText("");
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
