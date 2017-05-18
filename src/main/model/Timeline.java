package main.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;



@XmlType(propOrder = {"title", "startDate", "endDate","description", "listOfEvents", "id"})
@XmlRootElement(name = "Timeline")
@XmlAccessorType(XmlAccessType.FIELD)
public class Timeline {

	@XmlElement(name = "Event")
	private ArrayList<Event> listOfEvents = new ArrayList<Event>();;
	@XmlElement(name = "Title")
	private String title;
	@XmlElement(name = "Description")
	private String description;
	@XmlJavaTypeAdapter(value = LocalDatetoXMLAdapter.class)
	@XmlElement(name = "StartDate")
	private LocalDate startDate;
	@XmlJavaTypeAdapter(value = LocalDatetoXMLAdapter.class)
	@XmlElement(name = "EndDate")
	private LocalDate endDate;
	@XmlElement(name= "Id")
	private  int id;

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

	public void setId(int id){this.id=id;} // this should be done using a loop that runs through the createdTimelines list and gives the timeline it's id.
	// or be done upon creation

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
		return getTitle().equals("") || getStartDate().equals("")|| getEndDate().equals("");
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
