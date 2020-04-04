package application.controller.optiontabs;

import application.Main;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;

public class LinkingOptsController {
  public String buildLibs() {
    List<String> args = new ArrayList<String>();
    String argsStr = "";
    // One-Item-Per-Line Collection
    TextArea libs = (TextArea) Main.getScene().lookup(".linking-libraries");
    String[] libsSet = libs.getText().split("\n");
    for (String libStr : libsSet) {
      if (libStr.strip().length() > 0) {
        args.add("-l " + libStr);
        argsStr = argsStr + "-l " + libStr + " ";
      }
    }
    return argsStr;
  }
}
