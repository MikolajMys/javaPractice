module com.example.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.javaPractice to javafx.fxml;
    exports com.example.javaPractice;
}