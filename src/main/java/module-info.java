module com.example.finproject {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.example.finproject to javafx.fxml;
    exports com.example.finproject;
}