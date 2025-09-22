/**
 * 
 */
/**
 * 
 */
module wlasneProjekty {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires jdk.httpserver;

    opens Library to javafx.fxml;
    exports Library;
}

//siea