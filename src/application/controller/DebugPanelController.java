package application.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class DebugPanelController implements Initializable {
  @FXML private Pane debugPanel;

  @FXML private ComboBox format;

  @FXML private ComboBox level;

  @FXML private Button debugger;
  
  RadioButton checked=new RadioButton();
  ObservableList<String> list1 = FXCollections.observableArrayList("Stabs", "DWARF", "COFF", "XCOFF");
  ObservableList<String> list2 = FXCollections.observableArrayList("Level0", "Level1", "Level2", "Level3");

@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
	format.setItems(list1);
    level.setItems(list2);
}

public LinkedList<String>getDebugFlags() {
	// TODO Auto-generated method stub
	LinkedList<String> options=new LinkedList<String>();
	  String debugLevel = checked.getText();	  
	  System.out.println(debugLevel);
	  options.add(debugLevel);
	  return options;
}

}