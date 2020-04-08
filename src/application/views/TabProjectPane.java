package application.views;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

/** Tab created to assign a scroll pane to each tab */
public class TabProjectPane extends Tab {
  private ScrollPane scroll;

  public TabProjectPane(String name) {
    super(name);
  }

  ScrollPane getScroll() {
    return scroll;
  }

  public void setScroll(ScrollPane scroll) {
    this.scroll = scroll;
  }
}
