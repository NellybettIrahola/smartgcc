package application.controller.optiontabs;

import application.Main;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;

/** Controller of the Linking Options View (for the tab) */
public class LinkingOptsController {

  /**
   * Builds the string with the library flags
   *
   * @return String with the libraries
   */
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
