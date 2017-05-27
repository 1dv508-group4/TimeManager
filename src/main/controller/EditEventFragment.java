package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import main.common.AlertMessage;
import main.common.ScreenController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static main.common.TimelineDB.myTime;
import static main.controller.NewEventFragment.myEvent;

public class EditEventFragment {
    @FXML private Button cancelButton;
    @FXML private Button editButton;
    @FXML private TextField eventTitle;
    @FXML private DatePicker eventStartDate;
    @FXML private DatePicker eventEndDate;
    @FXML private CheckBox durational;
    @FXML private TextArea eventDescription;
    @FXML private AnchorPane PaneMain;


    public void initialize() {
        eventStartDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("M/d/yyyy");
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
                    new AlertMessage("Wrong date format", "The date was entered the wrong way. Correct way:\nM/d/yyyy", Alert.AlertType.ERROR);
                    return null;
                }
            }
        });

        eventStartDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                eventStartDate.setValue(eventStartDate.getConverter().fromString(eventStartDate.getEditor().getText()));
            }
        });

        eventEndDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("M/d/yyyy");
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
                    new AlertMessage("Wrong date format", "The date was entered the wrong way. Correct way:\nM/d/yyyy", Alert.AlertType.ERROR);
                    return null;
                }
            }
        });

        eventEndDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                eventEndDate.setValue(eventEndDate.getConverter().fromString(eventEndDate.getEditor().getText()));
            }
        });

        eventTitle.setText(myEvent.getEvent_title());
        eventStartDate.setValue(myEvent.getEvent_startDate());
        System.out.println(myEvent.isDurational());

        if (myEvent.isDurational()) {
            durational.setSelected(myEvent.isDurational());
            eventEndDate.setVisible(true);
            eventEndDate.setDisable(false);
        }
        eventEndDate.setValue(myEvent.getEvent_endDate());
        eventDescription.setText(myEvent.getEvent_description());

        PaneMain.setOnKeyPressed(e->{
        	if(e.getCode() == KeyCode.ENTER){

				try {
					editEvent();
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

        	}
        });
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
                myEvent.setDurational(durational.isSelected());
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
                myEvent.setDurational(durational.isSelected());
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