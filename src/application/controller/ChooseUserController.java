package application.controller;

import application.Main;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ChooseUserController {
	
	 @FXML
	 Button typical;
	
    @FXML
    private void switchToMainPanelTypical() throws Exception {
  
			generatePanels(1);
	
    }
    
    @FXML
    private void switchToMainPanelNovice() throws Exception {

			generatePanels(0);
	
    }
    
    @FXML
    private void switchToMainPanelExpert() throws Exception {

			generatePanels(2);
	
    }
    
    
    public void generatePanels(int type) throws Exception {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/MainPanel.fxml"));
    	MainController main=new MainController();
    	loader.setController(main);
    	Main.getScene().setRoot(loader.load());
    	MainController controller = (MainController)loader.getController();
    	main.initialize();
    	main.createPanels(type);

    	
    	
    }
}
