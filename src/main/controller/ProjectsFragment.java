package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import main.common.ScreenController;
import java.io.IOException;
import java.sql.SQLException;

import static main.common.StageManager.getStage;
import static main.controller.NewTimelineFragment.numberOfTimelines;

public class ProjectsFragment {
    @FXML private AnchorPane projectPane;
    @FXML private Button ButtonBack;
    @FXML private Label icon,inProgress,all,completed;
    @FXML private ScrollPane scrollProjects;
    @FXML private ImageView timelineIcon;
    @FXML private Pane projectDisplay;
    private double panePosition=73.0;

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
    public void newTimeline() throws IOException {
        Pane nw = new Pane();
        nw.setId("Project"+numberOfTimelines++);
        panePosition+=280;
        nw.setLayoutX(panePosition);
        nw.setLayoutY(120.0);
        nw.setPrefHeight(377.0);
        nw.setPrefWidth(265.0);

        nw.setStyle(
                "-fx-background-color: lightgrey; " +
                        "-fx-background-insets: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );
        Label title = new Label("Title Missing");

        title.setLayoutX(87.0);
        title.setLayoutY(271.0);
        title.setTextFill(Color.web("#0076a3"));
        title.setFont(Font.font("Segoe UI", 16));
        nw.getChildren().add(title);
        projectPane.getChildren().add(nw);
        numberOfTimelines++;  // is a problem waiting to happen.
        //ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);// still on going.
    }
}
