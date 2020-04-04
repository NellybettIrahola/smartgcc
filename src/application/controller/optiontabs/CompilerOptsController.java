package application.controller.optiontabs;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

public class CompilerOptsController {
	
	@FXML private Pane compilerOptsPanel;
	
	@FXML private ComboBox fabiVersion;
	
	@FXML
	private void initialize() {
		System.out.println("compiler initialize");
		fabiVersion.getItems().removeAll(fabiVersion.getItems());
		fabiVersion.getItems().addAll("Option A", "Option B", "Option C");
		fabiVersion.getSelectionModel().select("Option B");
		
	}
}
