package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.common.StageManager;

public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        new StageManager(primaryStage);
    }
    public static void main(String[] args) {launch(args);
    }
}