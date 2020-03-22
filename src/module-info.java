module smartgcc {
    requires javafx.controls;
    requires javafx.fxml;

    opens application to javafx.fxml;
    opens application.controller to javafx.fxml;
    exports application;
}
