package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.common.ScreenController;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class HomeFragment {

    @FXML private Button createBtn;
    @FXML private Button loadBtn;

    public void initialize() throws SQLException {

    }

    @FXML
    public void createTimeline() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
    }

    @FXML
    public void loadTimeline() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        importFromFile(file);
    }

    private void importFromFile(File file) {
    
    }
}
