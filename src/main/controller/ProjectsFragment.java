package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.common.ScreenController;
import main.db.Timelines;
import main.model.Timeline;

import java.io.IOException;

import static main.common.StageManager.getStage;
import static main.controller.HomeFragment.numberOfTimelines;
import static main.db.Timelines.myTime;


public class ProjectsFragment {
    @FXML private AnchorPane projectPane;
    @FXML private Button ButtonBack;
    @FXML private Label all;
    @FXML private ScrollPane scrollProjects;
    @FXML private ImageView timelineIcon;
    @FXML private Pane projectDisplay;

    private double panePosition=73.0;
    public static boolean updated=false;

    public void initialize(){
        try {
            ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
            ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));

            all.setText("All("+numberOfTimelines+")");
            projectDisplay.getStyleClass().add("timeline-pane");

            for (Timeline t : Timelines.getCreatedTimelines()) createPanes(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void back() throws IOException{ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void eventAdd() {

    }

    @FXML
    private void createPanes(Timeline repoTime) throws IOException {
        Pane nw = new Pane();
        nw.setId("Project"+(numberOfTimelines+1));
        panePosition+=280;
        nw.setLayoutX(panePosition);
        nw.setLayoutY(120.0);
        nw.setPrefHeight(377.0);
        nw.setPrefWidth(265.0);
        nw.getStyleClass().add("timeline-pane");
        Label title = new Label(repoTime.getTitle());
        Label num = new Label(repoTime.getNumberOfEvents()+"");
        title.setLayoutX(87.0);
        title.setLayoutY(271.0);
        title.setTextFill(Color.BLACK);
        title.setFont(Font.font("Segoe UI", 16));

        num.setFont(Font.font("Segoe UI", 16));
        num.setTextFill(Color.BLACK);
        num.setLayoutY(223.0);
        num.setLayoutX(100.0);
        nw.getChildren().addAll(title,num);
        projectPane.getChildren().add(nw);

        nw.setOnMouseClicked(e -> {
            try {
                myTime = repoTime;
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @FXML
    public void newTimeline(MouseEvent mouseEvent) throws IOException {
        ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
    }
}
