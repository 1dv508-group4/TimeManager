package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.common.ScreenController;
import java.io.IOException;
import java.sql.SQLException;

import static main.common.StageManager.getStage;
import static main.controller.NewTimelineFragment.numberOfTimelines;

public class ProjectsFragment {

    @FXML private Button ButtonBack;
    @FXML private Label icon,inProgress,all,completed;
    @FXML private ScrollPane scrollProjects;
    @FXML private ImageView timelineIcon;
    @FXML private Pane projectDisplay;
    public void initialize(){
        ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
        ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));
        all.setText("All("+numberOfTimelines+")");
        projectDisplay.setStyle(
                "-fx-background-color: lightgrey; " +
                        "-fx-background-insets: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );

    }
    // we need to figure out how can the system remember which screen i was in!!

    @FXML
    public void back() throws IOException{ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void eventAdd() {

    }

    @FXML
    public void newTimeline() {
    }
}
