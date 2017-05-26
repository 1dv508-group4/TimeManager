package main.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.common.ScreenController;
import main.common.TimelineDB;
import main.model.Event;
import main.model.Timeline;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static main.common.StageManager.getStage;
import static main.controller.NewEventFragment.myEvent;
import static main.common.TimelineDB.myTime;


public class TimelineDetailsFragment {
    @FXML private Button ButtonBack;
    @FXML private AnchorPane myDisplay;
    @FXML private Button newEventButton;
    @FXML private ScrollPane scrollPane;
    @FXML private Separator separator;
    @FXML private AnchorPane PaneMain;
    @FXML private Button editButton;
    @FXML private Text title;
    @FXML private Label endDate;
    @FXML private Label startDate;
    @FXML private Label description;
    @FXML private ImageView timeline_image;
    @FXML private Button RemoveTimeline;
    @FXML private Button AddImage;
    @FXML private AnchorPane LeftPane;
    @FXML private Button exportButton;

    private Timeline display = myTime;
    private double lineHeight;
    private double lineStart;
    private double lineLength;
    private double distanceBetweenLines;
    private int timelinePeriodInDays;
    private Tooltip tooltip = new Tooltip();

    public void initialize() throws SQLException {
        ButtonBack.setOnMouseEntered(e -> getStage().getScene().setCursor(Cursor.HAND));
        ButtonBack.setOnMouseExited(e -> getStage().getScene().setCursor(Cursor.DEFAULT));

        // The height of the actual line is calculated based on the height of the Scrollpane:
        lineHeight = scrollPane.getPrefHeight() / 2;
        lineStart = 15; // to account for space near the left border of the scrollpane

        timelinePeriodInDays = (int) ChronoUnit.DAYS.between(display.getStartDate(),display.getEndDate());

//        if (timelinePeriodInDays > 7500) {
//            lineLength = 2000 + (0.1 * (timelinePeriodInDays - 7500));
//        } else
        lineLength = 2000;

        title.setText("Title: " + display.getTitle());
        startDate.setText("Start date: " + display.getStartDate().toString());
        endDate.setText("End date: " + display.getEndDate().toString());
        description.setText("Description: " + display.getDescription());

        displayTimeline();
        displayEvents();

        // The PaneMain is the parent pane. It holds the Scrollpane inside it and therefore must be bounded to the
        // changing size of the stage.
        PaneMain.prefHeightProperty().bind(getStage().heightProperty());
        PaneMain.prefWidthProperty().bind(getStage().widthProperty());

        // The area in which the Timeline is shown is a Scrollpane. If the user makes the screen wider, the width of the
        // scrollpane is bound to the changing width of the stage.
        // The subtraction is needed to account for the width of the ListView.
        scrollPane.prefWidthProperty().bind(getStage().widthProperty().subtract(255));
    }

    /*
     * The length of the line is always fixed to an X of 1600.
     */

    private void displayTimeline() {
        Line lineTimeline = new Line(lineStart,lineHeight,lineLength,lineHeight);
        myDisplay.getChildren().add(lineTimeline);

        Line beginVertical = new Line(lineStart,lineHeight-15,lineStart,lineHeight+15);
        Line endVertical = new Line(lineLength,lineHeight-15,lineLength,lineHeight+15);
        myDisplay.getChildren().addAll(beginVertical,endVertical);

        distanceBetweenLines = (lineLength - lineStart) / timelinePeriodInDays;
        int dayOfMonth = 0;

        if (timelinePeriodInDays < 60) {
            for (int i = 1; i < timelinePeriodInDays; i++) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);
                Label dayLabel = new Label(String.valueOf(display.getStartDate().plusDays(i).getDayOfMonth()));
                dayLabel.setStyle("-fx-font-style: italic; -fx-font-size: 10px");
                dayLabel.relocate(lineStart + (i * distanceBetweenLines),lineHeight + 7);
                myDisplay.getChildren().addAll(verticalLine, dayLabel);
            }
        } else if (timelinePeriodInDays < 300) {
            for (int i = 1; i < timelinePeriodInDays; i++) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 2, (i * distanceBetweenLines) + lineStart, lineHeight + 2);
                myDisplay.getChildren().addAll(verticalLine);
            }

            for (int i = 0; i < timelinePeriodInDays; i += 7) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);
                Label dayLabel;
                Label monthLabel;
                if (display.getStartDate().plusDays(i).getDayOfMonth() < dayOfMonth) {
                    String dayOfLine = String.valueOf(display.getStartDate().plusDays(i).getDayOfMonth());
                    dayLabel = new Label(dayOfLine);
                    dayLabel.setStyle("-fx-font-style: italic; -fx-font-size: 12px");

                    monthLabel = new Label(String.valueOf(display.getStartDate().plusDays(i).getMonth().toString()));
                    monthLabel.setStyle("-fx-opacity: 0.3; -fx-font-size: 20px; -fx-font-style: italic");
                    monthLabel.relocate(lineStart + (i * distanceBetweenLines) + 15,lineHeight + 3);
                    myDisplay.getChildren().add(monthLabel);
                } else {
                    dayLabel = new Label(String.valueOf(display.getStartDate().plusDays(i).getDayOfMonth()));
                    dayLabel.setStyle("-fx-font-style: italic; -fx-font-size: 10px");
                }
                dayLabel.relocate(lineStart + (i * distanceBetweenLines),lineHeight + 7);
                dayOfMonth = display.getStartDate().plusDays(i).getDayOfMonth();
                myDisplay.getChildren().addAll(verticalLine, dayLabel);
            }
        } else if (timelinePeriodInDays < 365 * 3) {
            for (int i = 1; i < timelinePeriodInDays; i+=7) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 2, (i * distanceBetweenLines) + lineStart, lineHeight + 2);
                myDisplay.getChildren().addAll(verticalLine);
            }

            int monthNumber = 0; //01 = january, 02 february etc.
            int nMonths = (int) ChronoUnit.MONTHS.between(display.getStartDate(),display.getEndDate());
            distanceBetweenLines = (lineLength - lineStart) / nMonths;

            for (int i = 0; i < nMonths; i ++) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);

                Label yearLabel;
                Label monthLabel = new Label(String.valueOf(display.getStartDate().plusMonths(i).getMonth().toString()));
                monthLabel.setStyle("-fx-opacity: 0.3; -fx-font-size: 10px; -fx-font-style: italic");
                monthLabel.relocate(lineStart + (i * distanceBetweenLines) + 5,lineHeight + 3);

                if (display.getStartDate().plusMonths(i).getMonthValue() < monthNumber) {
                    yearLabel = new Label(String.valueOf(display.getStartDate().plusMonths(i).getYear()));
                    yearLabel.relocate(lineStart + (i * distanceBetweenLines) + 5,lineHeight + 8);
                    yearLabel.setStyle("-fx-opacity: 0.06; -fx-font-size: 60px;");
                    myDisplay.getChildren().add(yearLabel);
                }
                monthNumber = display.getStartDate().plusMonths(i).getMonthValue();
                myDisplay.getChildren().addAll(verticalLine, monthLabel);
            }
        } else if (timelinePeriodInDays < 365 * 30) { // 1 years division
            int nYears = (int) ChronoUnit.YEARS.between(display.getStartDate(), display.getEndDate());
            distanceBetweenLines = (lineLength - lineStart) / nYears;

            for (int i = 0; i < nYears; i ++) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);

                Label yearLabel;
                yearLabel = new Label(String.valueOf(display.getStartDate().plusYears(i).getYear()));
                yearLabel.setStyle("-fx-opacity: 0.8; -fx-font-size: 12px; -fx-font-style: italic");
                yearLabel.relocate(lineStart + (i * distanceBetweenLines) + 5, lineHeight + 3);

                myDisplay.getChildren().addAll(verticalLine, yearLabel);
            }
        } else if (timelinePeriodInDays < 365 * 250) { // 5years division
            int nYears = (int) ChronoUnit.YEARS.between(display.getStartDate(),display.getEndDate());
            distanceBetweenLines = (lineLength - lineStart) / nYears;

            for (int i = 0; i < nYears; i+=5) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);

                Label yearLabel;
                yearLabel = new Label(String.valueOf(display.getStartDate().plusYears(i).getYear()));
                yearLabel.setStyle("-fx-opacity: 0.8; -fx-font-size: 12px; -fx-font-style: italic");
                yearLabel.relocate(lineStart + (i * distanceBetweenLines) + 5, lineHeight + 3);

                myDisplay.getChildren().addAll(verticalLine, yearLabel);
            }
        } else if (timelinePeriodInDays < 365 * 1250) { // 25years division
            int nYears = (int) ChronoUnit.YEARS.between(display.getStartDate(), display.getEndDate());
            distanceBetweenLines = (lineLength - lineStart) / nYears;

            for (int i = 0; i < nYears; i += 25) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);

                Label yearLabel;
                yearLabel = new Label(String.valueOf(display.getStartDate().plusYears(i).getYear()));
                yearLabel.setStyle("-fx-opacity: 0.8; -fx-font-size: 12px; -fx-font-style: italic");
                yearLabel.relocate(lineStart + (i * distanceBetweenLines) + 5, lineHeight + 3);

                myDisplay.getChildren().addAll(verticalLine, yearLabel);
            }
        } else if (timelinePeriodInDays < 365 * 3000) { // 50 years division
            int nYears = (int) ChronoUnit.YEARS.between(display.getStartDate(),display.getEndDate());
            distanceBetweenLines = (lineLength - lineStart) / nYears;

            System.out.println(nYears);
            for (int i = 0; i < nYears; i+=50) {
                Line verticalLine = new Line((i * distanceBetweenLines) + lineStart, lineHeight - 5, (i * distanceBetweenLines) + lineStart, lineHeight + 5);

                Label yearLabel;
                yearLabel = new Label(String.valueOf(display.getStartDate().plusYears(i).getYear()));
                yearLabel.setStyle("-fx-opacity: 0.8; -fx-font-size: 12px; -fx-font-style: italic");
                yearLabel.relocate(lineStart + (i * distanceBetweenLines) + 5, lineHeight + 3);

                myDisplay.getChildren().addAll(verticalLine, yearLabel);
            }
        }
    }

    private void displayEvents() {
        ArrayList<Event> events = myTime.getListOfEvents();
        Collections.sort(events);
        ArrayList<Event> tempEvents = new ArrayList<>(events.size());
        HashMap<Integer,Integer> hm = new HashMap<>(8);

        /**
         * For each event, I try to calculate the position of the event on the timeline.
         * I do this by getting the total length of the line, being the total period in days of the timeline.
         * I compare this with the days until the event takes place, given the date of the event and the start date of
         * the timeline.
         *
         * Dividing the days until the event with the total days of the timeline, I get the relative position of where to
         * put the event. This position is then used by creating a line on the timeline.
         *
         */

        for (Event e: events) {
            ContextMenu conMenu = new ContextMenu();
            MenuItem modify = new MenuItem("Edit");
            MenuItem delete = new MenuItem("Delete");
            conMenu.getItems().addAll(modify, delete);


            if (!e.isDurational()) {
                int key = e.getEvent_startDate().hashCode();
                if (hm.containsKey(key)) {
                    hm.replace(key,hm.get(key) + 1);
                } else
                    hm.put(e.getEvent_startDate().hashCode(),0);

                int daysUntilEvent = (int) ChronoUnit.DAYS.between(display.getStartDate(), e.getEvent_startDate());

                // Calculate position on line to put event.
                distanceBetweenLines = (lineLength - lineStart) / timelinePeriodInDays;//1600 * relativePositionOfEvent / 100;

                // Some metro style colors:
                String[] colors = {"#00a300", "#9f00a7", "#7e3878", "#00aba9", "#ffc40d", "#da532c", "#ee1111"};

                Pane contentPane = new Pane();
                Circle circle = new Circle(8, Color.web(colors[e.hashCode() % 7]));
                contentPane.getChildren().add(circle);

                circle.relocate(-8,8);
                circle.setOnMouseExited(event -> getStage().getScene().setCursor(Cursor.DEFAULT));
                circle.setOnContextMenuRequested(event -> conMenu.show(circle, event.getScreenX(), event.getScreenY()));
                circle.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        try {
                            myEvent = e;
                            ScreenController.setScreen(ScreenController.Screen.EVENT_DETAILS);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                circle.setOnMouseEntered(event -> {
                    circle.toFront();
                    getStage().getScene().setCursor(Cursor.HAND);
                    tooltip.setText("Title: "+myEvent.getEvent_title()+"\n"+"Description: \n"+myEvent.getEvent_description());
                    Tooltip.install(circle, tooltip);
                });

                modify.setOnAction(actionEvent -> {
                    System.out.println(e.getEvent_title());
                    try {
                        myEvent = e;
                        ScreenController.setScreen(ScreenController.Screen.EDIT_EVENT);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                delete.setOnAction(actionEvent -> {
                    System.out.println("Event Deleted or at least pretend :)");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("This will delete event: " + e.getEvent_title());
                    alert.setContentText("Are you ok with this?");

                    // Styling of alert:
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getScene().getStylesheets().add("css/menu_items.css");
                    ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
                    buttonBar.getButtons().forEach(b -> {
                        b.getStyleClass().clear();
                        b.getStyleClass().add("btn");
                    });

                    // After 'OK' is clicked:
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
                        myTime.deleteEvent(e);
                        myDisplay.getChildren().removeAll(contentPane);
                    } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                        alert.close();
                    }
                });

                AnchorPane.setLeftAnchor(contentPane, (daysUntilEvent * distanceBetweenLines) + lineStart);
                AnchorPane.setTopAnchor(contentPane, lineHeight - (hm.get(key) * 50) - 20);

                Label dateOfEvent = new Label(e.getEvent_startDate().toString());
                dateOfEvent.relocate(5, -2);
                dateOfEvent.setFont(Font.font(10));
                contentPane.getChildren().add(dateOfEvent);

                Label titleOfEvent = new Label(e.getEvent_title());
                titleOfEvent.relocate(5, -15);
                titleOfEvent.setFont(Font.font(12));
                contentPane.getChildren().add(titleOfEvent);

                myDisplay.getChildren().add(contentPane);
            } else {
                int i = 0;
                int max = 0;

                // Calculation for knowing which level to put a durational event on, in case of overlap.
                // Hashcodes are used to compare dates.
                for (Event tempEvent:tempEvents) {
                    if (e.getEvent_startDate().hashCode() <= tempEvent.getEvent_endDate().hashCode() &&
                            e.getEvent_endDate().hashCode() >= tempEvent.getEvent_startDate().hashCode()) {
                        if (tempEvent.getLevel() + 1 > i) {
                            i = tempEvent.getLevel() + 1;
                        }
                    }
                }
                e.setLevel(i);
                tempEvents.add(e);


                LocalDate eventStartDate = e.getEvent_startDate();
                LocalDate eventEndDate = e.getEvent_endDate();

                int daysUntilEvent = (int) ChronoUnit.DAYS.between(display.getStartDate(), eventStartDate);
                int eventDuration = (int) ChronoUnit.DAYS.between(eventStartDate,eventEndDate);

                // Calculate position on line to put event.
                distanceBetweenLines = (lineLength - lineStart) / timelinePeriodInDays;

                Pane contentPane = new Pane();
                AnchorPane.setLeftAnchor(contentPane, (daysUntilEvent * distanceBetweenLines) + lineStart);
                AnchorPane.setTopAnchor(contentPane, lineHeight + 30 + (e.getLevel() * 30));

                Rectangle rect = new Rectangle(0,0,eventDuration * distanceBetweenLines,20);
                contentPane.getChildren().add(rect);

                String[] colors = {"#00a300", "#9f00a7", "#7e3878", "#00aba9", "#ffc40d", "#da532c", "#ee1111"};

                rect.setFill(Color.web(colors[e.hashCode() % 7]));

                rect.setOnMouseExited(event -> getStage().getScene().setCursor(Cursor.DEFAULT));
                rect.setOnContextMenuRequested(event -> conMenu.show(rect, event.getScreenX(), event.getScreenY()));
                rect.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        try {
                            myEvent = e;
                            ScreenController.setScreen(ScreenController.Screen.EVENT_DETAILS);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                rect.setOnMouseEntered(event -> {
                    getStage().getScene().setCursor(Cursor.HAND);
                    tooltip.setText("Title: "+myEvent.getEvent_title()+"\n"+"Description: \n"+myEvent.getEvent_description());
                    Tooltip.install(rect, tooltip);
                });

                modify.setOnAction(actionEvent -> {
                    System.out.println(e.getEvent_title());
                    try {
                        myEvent = e;
                        System.out.println(myEvent.isDurational());
                        ScreenController.setScreen(ScreenController.Screen.EDIT_EVENT);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                delete.setOnAction(actionEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("This will delete event: " + e.getEvent_title());
                    alert.setContentText("Are you ok with this?");

                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getScene().getStylesheets().add("css/menu_items.css");
                    ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
                    buttonBar.getButtons().forEach(b -> {
                        b.getStyleClass().clear();
                        b.getStyleClass().add("btn");
                    });


                    Optional<ButtonType> decision = alert.showAndWait();
                    if (decision.isPresent() && (decision.get() == ButtonType.OK)) {
                        myTime.deleteEvent(e);
                        myDisplay.getChildren().removeAll(contentPane);
                    } else if (decision.isPresent() && decision.get() == ButtonType.CANCEL) {
                        alert.close();
                    }
                });


                Label titleOfEvent = new Label(e.getEvent_title());
                contentPane.getChildren().add(titleOfEvent);
                titleOfEvent.relocate(2, 1);
                titleOfEvent.setFont(Font.font(13));
                titleOfEvent.setTextFill(Color.WHITE);

                titleOfEvent.setOnMouseExited(event -> getStage().getScene().setCursor(Cursor.DEFAULT));
                titleOfEvent.setOnContextMenuRequested(event -> conMenu.show(titleOfEvent, event.getScreenX(), event.getScreenY()));
                titleOfEvent.setOnMouseEntered(event -> {
                    getStage().getScene().setCursor(Cursor.HAND);
                    tooltip.setText("Title: "+myEvent.getEvent_title()+"\n"+"Description: \n"+myEvent.getEvent_description());
                    Tooltip.install(titleOfEvent, tooltip);
                });
                titleOfEvent.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        try {
                            myEvent = e;
                            ScreenController.setScreen(ScreenController.Screen.EVENT_DETAILS);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                myDisplay.getChildren().add(contentPane);
            }
        }
    }

    @FXML
    public void back() throws IOException {ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);}

    @FXML
    public void addEvent() throws IOException {ScreenController.setScreen(ScreenController.Screen.NEW_EVENT);}

    @FXML
    void removeTimeline() throws IOException{
        myDisplay.getChildren().clear();
        LeftPane.getChildren().clear();
        TimelineDB.removeTimeline(display);
        HomeFragment.numberOfTimelines--;
        ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
    }
    @FXML
    public void editTimeline() throws IOException{ScreenController.setScreen(ScreenController.Screen.EDIT_TIMELINE);}

    @FXML
    public void exportTimeline() throws IOException{
        try{
            JAXBContext context = JAXBContext.newInstance(Timeline.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Timeline");
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                try {
                    m.marshal(display, file);
                } catch (JAXBException ex) {
                    System.out.println(ex.getMessage());
                }
                finally {
                    System.out.println("XML saved");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}