package main.controller;

import static main.controller.HomeFragment.myTime;
import static main.controller.NewEventFragment.myEvent;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.common.AlertMessage;
import main.common.ScreenController;
import main.model.Event;

public class EditEventFragment {
    @FXML private Button cancelButton;
    @FXML private Button editButton;
    @FXML private TextField eventTitle;
    @FXML private DatePicker eventStartDate;
    @FXML private DatePicker eventEndDate;
    @FXML private CheckBox durational;
    @FXML private TextArea eventDescription;


    public void initialize() {
        eventTitle.setText(myEvent.getEvent_title());
        eventStartDate.setValue(myEvent.getEvent_startDate());
        eventEndDate.setValue(myEvent.getEvent_endDate());
        eventDescription.setText(myEvent.getEvent_description());

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

    public void editEvent() throws IOException {
        if (durational.isSelected()) {
            if (eventStartDate.getValue().isBefore(myTime.getEndDate()) && (eventStartDate.getValue().isAfter(myTime.getStartDate()) || eventStartDate.getValue().isEqual(myTime.getStartDate())) && (eventEndDate.getValue().isBefore(myTime.getEndDate()) || eventEndDate.getValue().isEqual(myTime.getEndDate())) && !eventTitle.getText().isEmpty()) {
                myEvent.setEvent_description(eventDescription.getText());
                myEvent.setEvent_title(eventTitle.getText());
                myEvent.setEvent_startDate(eventStartDate.getValue());
                myEvent.setEvent_endDate(eventEndDate.getValue());

                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            } else if (eventStartDate.getValue().isBefore(myTime.getStartDate())){
                new AlertMessage("Inccorect Date", "Please enter correct starting date", Alert.AlertType.WARNING);
            } else if (eventEndDate.getValue().isAfter(myTime.getEndDate())){
                new AlertMessage("Inccorect Date", "Please enter correct ending date", Alert.AlertType.WARNING);
            } else {
                new AlertMessage("Missing details", "Please enter title and date(s)", Alert.AlertType.WARNING);
            }
        } else {
            if (eventStartDate.getValue().isBefore(myTime.getEndDate()) && eventStartDate.getValue().isAfter(myTime.getStartDate()) && !eventTitle.getText().isEmpty()) {
                myEvent.setEvent_description(eventDescription.getText());
                myEvent.setEvent_title(eventTitle.getText());
                myEvent.setEvent_startDate(eventStartDate.getValue());
                ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
            } else {
                new AlertMessage("Missing details", "Please enter title and date(s)", Alert.AlertType.WARNING);
            }
        }
    }

}