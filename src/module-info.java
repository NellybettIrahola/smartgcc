module smartgcc {
  requires transitive javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.base;

  opens application to
      javafx.fxml;
  opens application.controller to
      javafx.fxml;

  exports application;
}
