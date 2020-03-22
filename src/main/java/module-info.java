module smartgcc {
    requires javafx.controls;
    requires javafx.fxml;

    opens SOEN6751 to javafx.fxml;
    exports SOEN6751;
}
