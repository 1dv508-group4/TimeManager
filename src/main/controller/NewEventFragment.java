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
    @FXML private DatePicker eventDate;
    @FXML private TextArea eventDescription;
    static Event myEvent=  new Event();



    public void back() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void cancelNewEvent() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
    }

    @FXML
    public void saveEvent() throws IOException {

        if (eventDate.getValue().isBefore(myTime.getEndDate()) & eventDate.getValue().isAfter(myTime.getStartDate()) & eventTitle != null) {
            myEvent = new Event(eventTitle.getText(), eventDescription.getText(), eventDate.getValue());
            myTime.addEvent(myEvent);
            ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
        } else {
            new AlertMessage("Wrong Duration", "Please specify correct event duration", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void durationalEvent() {

    }
}