package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import main.model.Timeline;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static main.common.StageManager.getStage;
import static main.common.TimelineDB.myTime;


public class EditTimelineFragment implements Initializable {
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    @FXML private Button ButtonBack;
    @FXML private AnchorPane PaneMain;
    @FXML private TextField timelineTitle;
    @FXML private TextField timelineDescription;
    @FXML private DatePicker timelineStartDate;
    @FXML private DatePicker timelineEndDate;
    Timeline display = myTime;
    static int numberOfTimelines=0;
    private boolean isCreated=false;


    public void initialize() throws SQLException {
        ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
        ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));

        cancelBtn.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        cancelBtn.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        timelineStartDate.setConverter(new StringConverter<LocalDate>() {
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

        timelineStartDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                timelineStartDate.setValue(timelineStartDate.getConverter().fromString(timelineStartDate.getEditor().getText()));
            }
        });

        timelineEndDate.setConverter(new StringConverter<LocalDate>() {
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

        timelineEndDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                timelineEndDate.setValue(timelineEndDate.getConverter().fromString(timelineEndDate.getEditor().getText()));
            }
        });

        PaneMain.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER)
                try {
                    saveTimelineDetails();
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }
        });
    }
    @FXML
    public void back() throws IOException{ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void cancelCreate() throws IOException {ScreenController.setScreen(ScreenController.Screen.HOME);}

    @FXML
    public void saveTimelineDetails() throws IOException,NumberFormatException {
        if (correctDuration(timelineStartDate.getValue(),timelineEndDate.getValue()) && (!timelineTitle.getText().equals("") ||timelineTitle!=null)){
            display.setId(numberOfTimelines++);
            display.setTitle(timelineTitle.getText());
            display.setDescription(timelineDescription.getText());
            isCreated=true;
            //change button save with button display and cancel with a delete button if user clicks display we move to timeline view fragment (projects fragment)

            ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);
        }else{
            new FadeInRightTransition(timelineStartDate).play();
            new FadeInRightTransition(timelineEndDate).play(); // we could choose a better description for the alert of course.
            AlertMessage msg = new AlertMessage("Wrong Duration","Please specify correct timeline duration", Alert.AlertType.WARNING);
        }
    }

    private boolean correctDuration(LocalDate start, LocalDate end) { //this checks that end is older that the start.
        //Alex null check is done here
    	if( start==null || end==null)
        	return false;
        else {
    	    if(start.isAfter(end) || start.isEqual(end) )
    	        return false;
            else {
                display.setStartDate(timelineStartDate.getValue());
                display.setEndDate(timelineEndDate.getValue());
                return true;
            }
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timelineTitle.setText(display.getTitle());
		timelineDescription.setText(display.getDescription());
		timelineStartDate.setValue(display.getStartDate());
		timelineEndDate.setValue(display.getEndDate());
		
	}
}

