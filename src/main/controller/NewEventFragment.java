package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.common.AlertMessage;
import main.common.ScreenController;
import main.model.Event;

import java.io.IOException;

import static main.controller.NewTimelineFragment.myTime;

public class NewEventFragment {

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField eventTitle;
    @FXML private DatePicker eventStartDate;
    @FXML private DatePicker eventEndDate;
    @FXML private CheckBox durational;
    static Event myEvent=  new Event();

    public void initialize() {
        // Sets visibility of EndDate if Checkbox for durational event is used:
        durational.selectedProperty().addListener((observable, oldValue, newValue) -> {
            eventEndDate.setDisable(oldValue);
            eventEndDate.setVisible(newValue);
            if (newValue)
                eventStartDate.setPromptText("Start date...");
            else
                eventStartDate.setPromptText("Event date...");
        });

    }

    public void back() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void cancelNewEvent() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void saveEvent() throws IOException {
        if (durational.isSelected()) {
            if (eventStartDate.getValue().isBefore(myTime.getEndDate()) & eventStartDate.getValue().isAfter(myTime.getStartDate()) & !eventTitle.getText().isEmpty()) {
                myEvent = new Event(eventTitle.getText(), "TEST DESCRIPTION", eventStartDate.getValue(), eventEndDate.getValue());
                myTime.addEvent(myEvent);
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            } else {
                new AlertMessage("Missing details", "Please enter title and date(s)", Alert.AlertType.WARNING);
            }
        } else {
            if (eventStartDate.getValue().isBefore(myTime.getEndDate()) & eventStartDate.getValue().isAfter(myTime.getStartDate()) & !eventTitle.getText().isEmpty()) {
                myEvent = new Event(eventTitle.getText(), "TEST DESCRIPTION", eventStartDate.getValue());
                myTime.addEvent(myEvent);
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            } else {
                new AlertMessage("Missing details", "Please enter title and date(s)", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    public void durationalEvent(MouseEvent mouseEvent) {
    }
}