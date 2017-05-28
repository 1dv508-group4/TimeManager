import main.model.Event;
import main.model.Timeline;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TimelineTest {
	String t1 = "2017-05-01";
	String t2 = "2017-05-30";
	LocalDate date1 = LocalDate.parse(t1.subSequence(0, 10));
	LocalDate date2 = LocalDate.parse(t2.subSequence(0, 10));

	Timeline timeline1 = new Timeline("title1", date1, date2,"DescriptionTL 1");
	Event e1 = new Event("eventName1","eventDescription1", date1, date2);
	Event e2 = new Event("eventName2","eventDescription2", date1, date2);
	Event e3 = new Event("eventName3","eventDescription3", date1, date2);
	Event e4 = new Event("e4","Ed4",date1);
	
	
	
	@Test
	public  void getTitle() {
        assertEquals("title1", timeline1.getTitle());
    }
	@Test
    public  void getId(){
    	assertEquals(0,timeline1.getId());
    }
	@Test
	public void getStartDate() {
		assertEquals(LocalDate.parse(t1), timeline1.getStartDate());
	}
	@Test
	public void getEndDate() {
		assertEquals(LocalDate.parse(t2), timeline1.getEndDate());
	}
	@Test
	public  void getDescription() {
		assertEquals("DescriptionTL 1",timeline1.getDescription());
	}
	@Test
	public void getListOfEvents() {
		timeline1.addEvent(e1);
		assertEquals(e1, timeline1.getListOfEvents().get(0));
		
	}
	@Test
	public void setTitle() {
		timeline1.setTitle("Fishy");
		assertEquals("Fishy", timeline1.getTitle());
	}
	@Test
	public void setId(){
		timeline1.setId(4);
		assertEquals(4, timeline1.getId());
		
	} 
	@Test
	public void setStartDate() {
		timeline1.setStartDate(date2);
		assertEquals(LocalDate.parse(t2),timeline1.getStartDate());
	}
	@Test
	public void setEndDate() {
		timeline1.setEndDate(date1);
		assertEquals(LocalDate.parse(t1),timeline1.getEndDate());
		
	}
	@Test
	public void setDescription() {
		timeline1.setDescription("fish");
		assertEquals("fish", timeline1.getDescription());
	}
	@Test
	public void size() {
		assertEquals(0,timeline1.size());
	}
	@Test
	public void isEmpty() {
		//assertTrue(timeline1.isEmpty());  // supposed to fail.
		assertFalse(timeline1.isEmpty());
	}
	@Test
	public void addEvent() {
	timeline1.addEvent(e2);
	assertEquals(e2,timeline1.getListOfEvents().get(0));
	assertEquals(1,timeline1.getNumberOfEvents());
	
	}
	@Test
	public void deleteEvent(){
		timeline1.addEvent(e1);
		timeline1.addEvent(e2);
		timeline1.deleteEvent(e2);
		assertEquals(1, timeline1.getNumberOfEvents());
	}
	@Test
	public void TesttoString() {
		timeline1.addEvent(e1);
		String string = "0,title1,2017-05-01,2017-05-30,DescriptionTL 1,0,eventName1,2017-05-01,2017-05-30,eventDescription1,";
		assertEquals(string, timeline1.toString());
		
	}

	
}
