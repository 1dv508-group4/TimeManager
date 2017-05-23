package main.common;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ScreenController {

    /**
     * Manages the handling of screens. Controller classes call the setScreen method to update the screen.
     */
    public enum Screen {
        SPLASH ("/fxml/splash_fragment.fxml"), // Splash Screen.
        MENU ("/fxml/menu_fragment.fxml"),// Should attempt to get the number of time_lines created and the number of times you commit to them in proportion to time.
        ABOUT ("/fxml/about_fragment.fxml"),// Team member information.
        HOME ("/fxml/home_fragment.fxml"), // should show  System functions <<create>> or <<load>>.
        NEW_TIMELINE("/fxml/new_timeline_fragment.fxml"), // one time_line
        NEW_EVENT ("/fxml/new_event_fragment.fxml"),
        MY_PROJECTS ("/fxml/projects_fragment.fxml"), // multiple time_lines
        TIMELINE_DETAILS ("/fxml/timelineDetails_fragment.fxml"),
        EVENT_DETAILS ("/fxml/EventDetailsfragment.fxml"),
        EDIT_TIMELINE("/fxml/edit_timeline_fragment.fxml"),
        EDIT_EVENT("/fxml/edit_event_fragment.fxml");

        private String resourceLocation;

        Screen(String resourceLocation) {
        this.resourceLocation = resourceLocation;
        }

    public String getResourceLocation() {
            return resourceLocation;
        }
    }

    public ScreenController(){
    }

    public static void setScreen(Screen screen)throws IOException {
        if (screen.resourceLocation.equals(Screen.SPLASH.getResourceLocation()) || screen.resourceLocation.equals(Screen.MENU.getResourceLocation()))
            StageManager.setRoot(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
        else
            StageManager.setPaneFragment(FXMLLoader.load(ScreenController.class.getResource(screen.getResourceLocation())));
    }
}
