package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Timeline {

	private ArrayList<Event> listOfEvents = new ArrayList<Event>();;
	private static String title, description;
	private LocalDate startDate;
	private LocalDate endDate;
	private static int id;

	public Timeline(){}

	public Timeline(Event event){
		listOfEvents.add(event);
	}

	public Timeline (String title, LocalDate start, LocalDate end,String desc) {
		title = title;
		startDate = start;
		endDate = end;
		description = desc;
	}

    public static String getTitle() {
        return title;
    }

    public static int getId(){return id;}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public static String getDescription() {
		return description;
	}

	public ArrayList<Event> getListOfEvents() {
		return listOfEvents;
	}

	public void setTitle(String _reference) {
		this.title = _reference;
	}

	public void setId(int id){this.id=id;}

	public void setStartDate(LocalDate _initDate) {
		this.startDate = _initDate;
	}

	public void setEndDate(LocalDate end) {
		this.endDate = end;
	}

	public void setDescription(String _details) {this.description = _details;}

	public int size() {
		return listOfEvents.size();
	}

	public boolean isEmpty() {
		return (size() == 0);
	}

	public void addEvent(Event point) {
		listOfEvents.add(point);
	}

	public void deleteEvent(Event toDelete){
		listOfEvents.remove(toDelete);
	}

	public String toString() {
		return title;
	}

}
