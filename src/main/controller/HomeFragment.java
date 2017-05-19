package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.common.ScreenController;
import main.model.Timeline;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.sun.xml.internal.ws.util.Pool.Unmarshaller;


public class HomeFragment {

    @FXML private Button createBtn;
    @FXML private Button loadBtn;
    public static ArrayList<Timeline> createdTimelines = new ArrayList<>();
    public static Timeline myTime = new Timeline(); // a timeline object that is used to create a time and keep track of the events added to a specific timeline.
    public static int numberOfTimelines; // holds a record of the number of created timelines

    public void initialize() throws SQLException {

    }

    @FXML
    public void createTimeline() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
    }

    @FXML
    public void loadTimeline() throws JAXBException, IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        myTime = importFromFile(file);
        ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);



    }

    private Timeline importFromFile(File file) throws JAXBException {
    	JAXBContext context = JAXBContext.newInstance(Timeline.class);
		javax.xml.bind.Unmarshaller unMarshaller = context.createUnmarshaller();

		return (Timeline) unMarshaller.unmarshal(file);

    }
}
