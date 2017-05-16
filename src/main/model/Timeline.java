package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Timeline {

	private ArrayList<Event> listOfEvents = new ArrayList<Event>();;
	private String title, description;
	private LocalDate startDate;
	private LocalDate endDate;
	private  int id;

	public Timeline(){}

	public Timeline(Event event){
		listOfEvents.add(event);
	}

	public Timeline (String title, LocalDate start, LocalDate end,String desc) {
		this.title = title;
		startDate = start;
		endDate = end;
		description = desc;
	}

    public  String getTitle() {
        return title;
    }

    public  int getId(){return id;}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public  String getDescription() {
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
		return this.getId()+","+this.getTitle()+","+this.getStartDate()+","+this.getEndDate()+","+this.getDescription()+","+addEvents();
	}

    private String addEvents() {
	    String events="";
	    for(int i =0;i<listOfEvents.size();i++){
            events += i+","+listOfEvents.get(i).toString() + ",";
        }
    return events;
	}

    public int getNumberOfEvents() {
        return listOfEvents.size();
    }
}
