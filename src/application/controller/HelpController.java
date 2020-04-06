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
		text.setText("Compile a project");
    }
	
	@FXML
    void createProject(MouseEvent event) {
		text.setText("Create a project");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
