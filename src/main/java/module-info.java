module src.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.google.gson;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires jcommander;
    requires org.apache.poi.ooxml;
  requires jakarta.mail;
  requires java.desktop;
    requires fontawesomefx;
  requires jbcrypt;

  opens src.librarysystem to javafx.fxml;
    exports src.librarysystem;
}