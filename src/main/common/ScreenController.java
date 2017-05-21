package main.common;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ScreenController {

    /**
     * Manages the handling of screens. Controller classes call the setScreen method to update the screen.
     */
    public enum Screen {
    	  SPLASH ("../view/splash_fragment.fxml"), // Splash Screen.
          MENU ("../view/menu_fragment.fxml"),// Should attempt to get the number of time_lines created and the number of times you commit to them in proportion to time.
          HOME ("../view/home_fragment.fxml"), // should show  System functions <<create>> or <<load>>.
          NEW_TIMELINE("../view/new_timeline_fragment.fxml"), // one time_line
          NEW_EVENT ("../view/new_event_fragment.fxml"),
          MY_PROJECTS ("../view/projects_fragment.fxml"), // multiple time_lines
          TIMELINE_DETAILS ("../view/timelineDetails_fragment.fxml"),
          ABOUT ("../view/about_fragment.fxml"),// Team member information.
          eventDetailsfragment("../view/EventDetailsfragment.fxml"),
      	  EDIT("../view/edit_timeline_fragment.fxml");
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
