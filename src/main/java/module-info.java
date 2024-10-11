module src.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires fontawesomefx;
    requires jdk.compiler;
    requires com.google.gson;
    requires java.sql;


    opens src.librarysystem to javafx.fxml;
    exports src.librarysystem;
}