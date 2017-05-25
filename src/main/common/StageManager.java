package main.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private static Stage stage;
    private static AnchorPane pane;

    public StageManager(Stage stage) throws IOException {
        StageManager.stage=stage;
        stage.setTitle("Time Management System");
        stage.setMinWidth(600.0);
        Parent root= FXMLLoader.load(ScreenController.class.getResource("/fxml/menu_fragment.fxml"));

        Scene s = new Scene(root);
        s.getStylesheets().add("css/menu_items.css"); // css for design
        stage.setScene(s);
        stage.show();
    }

    public static Stage getStage() {return stage;}

    static void setRoot(Parent root) {StageManager.stage.getScene().setRoot(root);}

    public static void setPane(AnchorPane pane) {StageManager.pane=pane;}

    public static AnchorPane getPane() {
        return pane;
    }

    static void setPaneFragment(Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }
}
