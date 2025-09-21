package Library;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Pole tekstowe
        TextField input = new TextField();
        input.setPromptText("Wpisz coś tutaj...");

        // Lista
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Książka 1", "Książka 2", "Książka 3");

        // Przycisk
        Button button = new Button("Dodaj do listy");
        button.setOnAction(e -> {
            String text = input.getText();
            if (!text.isEmpty()) {
                listView.getItems().add(text);
                input.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Pole nie może być puste!");
                alert.showAndWait();
            }
        });

        // Layout (VBox = pionowo)
        VBox layout = new VBox(10, input, button, listView);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");

        // Scena
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Demo JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 👉 metoda do wywołania GUI z konsoli
    public static void launchFX() {
        new Thread(() -> Application.launch(GuiTest.class)).start();
    }

    // 👉 metoda do zamknięcia GUI z poziomu Main.java
    public static void stopFX() {
        Platform.exit();
    }
}


