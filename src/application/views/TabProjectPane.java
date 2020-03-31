package application.views;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

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
