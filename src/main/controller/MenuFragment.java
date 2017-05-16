package main.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import main.common.ScreenController;
import main.common.StageManager;

import java.io.IOException;

import static main.common.StageManager.getStage;


public class MenuFragment {


    @FXML private Button ButtonLogout;
    @FXML private Button ButtonResize;
    @FXML private ListView<String> ListMenu;
    @FXML private AnchorPane PaneFragment;

    private Rectangle2D rectangle2D;
    private double width;
    private double height;

    public void initialize() {
        StageManager.setPane(PaneFragment);
        PaneFragment.prefWidthProperty().bind(getStage().widthProperty().subtract(150));

        rectangle2D = Screen.getPrimary().getVisualBounds();
        width=0.1;
        height=0.1;

        ListMenu.getItems().addAll("Home","My Projects","My Timeline","About Team");
        ListMenu.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ListMenu.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ListMenu.getSelectionModel().select(0);
        ListMenu.requestFocus();
        try {
            modeListMenu(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modeListMenu(MouseEvent event) throws IOException {
        switch(ListMenu.getSelectionModel().getSelectedIndex()){
            case 0:
                ScreenController.setScreen(ScreenController.Screen.HOME);
                break;
            case 1:
                ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
                break;
            case 2:
                ScreenController.setScreen(ScreenController.Screen.NEW_TIMELINE);
                break;
            case 3:
                ScreenController.setScreen(ScreenController.Screen.ABOUT);
                break;
            default:
                break;
        }
    }

    @FXML
    public void modeLogout() throws IOException {
        ScreenController.setScreen(ScreenController.Screen.SPLASH); //TODO: Replace this a screen detailing the project and a button to home
    }
}
