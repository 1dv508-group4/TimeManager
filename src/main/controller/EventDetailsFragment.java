package main.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import main.common.ScreenController;
import main.model.Event;
import static main.controller.NewEventFragment.myEvent;


public class EventDetailsFragment {

    @FXML private Label eventStart;
    @FXML private Label eventEnd;
    @FXML private Label eventEndLabel;
    @FXML private TextField eventDesc;
    @FXML private Text EventTitle;
    @FXML ImageView imageview;


    public void initialize() throws SQLException {
    	EventTitle.setText(myEvent.getEvent_title());
    	eventStart.setText(myEvent.getEvent_startDate().toString());
    	if (myEvent.isDurational()) {
            eventEndLabel.setVisible(true);
    	    eventEnd.setVisible(true);
            eventEnd.setText(myEvent.getEvent_endDate().toString());
        }
    	eventDesc.setText(myEvent.getEvent_description());
    }

    @FXML
    void back(ActionEvent event) throws IOException {ScreenController.setScreen(ScreenController.Screen.TIMELINE_DETAILS);}

    @FXML
    void addEvent(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg", "*.JPEG", "*.jpeg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageview.setImage(image);
        } catch (IOException ex) {ex.printStackTrace();}
    }
}
