module src.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires fontawesomefx;
    requires jdk.compiler;


    opens src.librarysystem to javafx.fxml;
    exports src.librarysystem;
}