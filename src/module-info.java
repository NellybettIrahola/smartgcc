module smartgcc {
  requires transitive javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.base;

  opens application to
      javafx.fxml;
  opens application.controller to
      javafx.fxml;
  opens application.controller.optiontabs to
      javafx.fxml;

  exports application;
  exports application.controller;
  exports application.controller.optiontabs;
}
