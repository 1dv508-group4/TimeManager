package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import main.animation.FadeInRightTransition;
import main.common.AlertMessage;
import main.common.ScreenController;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static main.common.StageManager.getStage;
import static main.controller.HomeFragment.myTime;
import static main.controller.HomeFragment.numberOfTimelines;
import static main.controller.ProjectsFragment.updated;

public class NewTimelineFragment {
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    @FXML private Button ButtonBack;
    @FXML private AnchorPane PaneMain;
    @FXML private TextField timelineTitle;
    @FXML private TextField timelineDescription;
    @FXML public DatePicker timelineStartDate;
    @FXML public DatePicker timelineEndDate;

    public void initialize() throws SQLException {
        ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
        ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));

        cancelBtn.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        cancelBtn.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        PaneMain.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                try {
                    saveTimelineDetails();
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
    @FXML
    public void back() throws IOException{ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void cancelCreate() throws IOException {ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void saveTimelineDetails() throws IOException,NumberFormatException {
        if (correctDuration(timelineStartDate.getValue(),timelineEndDate.getValue()) && !timelineTitle.getText().equals("")){
            myTime.setId(numberOfTimelines++);
            myTime.setTitle(timelineTitle.getText());
            myTime.setDescription(timelineDescription.getText());
            updated=true;
            ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
        }else{
            new FadeInRightTransition(timelineStartDate).play();
            new FadeInRightTransition(timelineTitle).play();
            new FadeInRightTransition(timelineEndDate).play(); // we could choose a better description for the alert of course.
            new AlertMessage("Wrong Duration","Please specify correct timeline duration", Alert.AlertType.WARNING);
        }

        exportToFile(myTime.toString());
    }

    private boolean correctDuration(LocalDate start, LocalDate end) { //this checks that end is older that the start.
        if(start == null||end==null || start.isAfter(end))return false;
        else{
            myTime.setStartDate(timelineStartDate.getValue());
            myTime.setEndDate(timelineEndDate.getValue());
            return true;
        }
    }

    private void exportToFile(String s) {
    }
}

