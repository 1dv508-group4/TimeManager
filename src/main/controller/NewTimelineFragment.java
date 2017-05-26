package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import main.animation.FadeInRightTransition;
import main.common.AlertMessage;
import main.common.ScreenController;
import main.common.TimelineDB;
import main.model.Timeline;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static main.common.StageManager.getStage;
import static main.controller.HomeFragment.numberOfTimelines;
import static main.common.TimelineDB.myTime;

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

        timelineStartDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }
            @Override
            public LocalDate fromString(String dateString) {
                if(dateString==null || dateString.trim().isEmpty())
                    return null;
                try{
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
                catch(Exception e){
                    new AlertMessage("Wrong date format", "The date was entered the wrong way. Correct way:\nMM/dd/yyyy", Alert.AlertType.ERROR);
                    return null;
                }
            }
        });

        timelineStartDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                timelineStartDate.setValue(timelineStartDate.getConverter().fromString(timelineStartDate.getEditor().getText()));
            }
        });

        timelineEndDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }
            @Override
            public LocalDate fromString(String dateString) {
                if(dateString==null || dateString.trim().isEmpty())
                    return null;
                try{
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
                catch(Exception e){
                    //Bad date value entered
                    return null;
                }
            }
        });

        timelineEndDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                timelineEndDate.setValue(timelineEndDate.getConverter().fromString(timelineEndDate.getEditor().getText()));
            }
        });

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
        if (correctDate()) {
            myTime = new Timeline(timelineTitle.getText(),timelineStartDate.getValue(),timelineEndDate.getValue(),timelineDescription.getText());
            myTime.setId(numberOfTimelines++);
            TimelineDB.addTimeline(myTime);
            ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
        } else {
            new FadeInRightTransition(timelineStartDate).play();
            new FadeInRightTransition(timelineTitle).play();
            new FadeInRightTransition(timelineEndDate).play(); // we could choose a better description for the alert of course.
            new AlertMessage("Wrong Duration","Please specify correct timeline duration", Alert.AlertType.WARNING);
        }
    }

    private boolean correctDate() {
        return !((timelineStartDate.getValue() == null || timelineEndDate.getValue() == null
                || timelineStartDate.getValue().isAfter(timelineEndDate.getValue()))
                && (!timelineTitle.getText().equals("") ||timelineTitle!=null));
    }
}

