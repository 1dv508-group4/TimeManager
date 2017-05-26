package main.common;

import main.model.Timeline;

import java.util.ArrayList;

public class TimelineDB {
    private static ArrayList<Timeline> createdTimelines = new ArrayList<>();
    public static Timeline myTime;

    public static void addTimeline(Timeline t) {
        createdTimelines.add(t);
    }

    public static void removeTimeline(Timeline t) {
        createdTimelines.remove(t);
    }

    public static ArrayList<Timeline> getCreatedTimelines() {
        return createdTimelines;
    }
}
