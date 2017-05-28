import main.model.Event;
import main.model.Timeline;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {
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
	public void getEvent_title() {
        assertEquals("eventName1", e1.getEvent_title());
    }
	@Test
    public void setEvent_title() {
      e1.setEvent_title("fish");
      assertEquals("fish", e1.getEvent_title());
    }
	@Test
    public void getEvent_description() {
        assertEquals("eventDescription1", e1.getEvent_description());
    }
	@Test
    public void setEvent_description() {
        e1.setEvent_description("fishing");
        assertEquals("fishing", e1.getEvent_description());
        
        
    }
	@Test
    public void getEvent_startDate() {
        assertEquals(LocalDate.parse("2017-05-01"),e1.getEvent_startDate());
    }
	@Test
    public void setEvent_startDate() {
        e1.setEvent_startDate(LocalDate.parse("2017-05-30"));
        assertEquals(date2, e1.getEvent_startDate());
    }
	@Test
    public void getEvent_endDate() {
		assertEquals(date2, e1.getEvent_endDate());
    }
	@Test
    public void setEvent_endDate() {
        e1.setEvent_endDate(date1);
        assertEquals(date1, e1.getEvent_endDate());
    }
	@Test
    public void getTimeline_id() {
       assertEquals(0, timeline1.getId());
    }
	@Test
    public void setTimeline_id() {
		timeline1.setId(3);
		assertEquals(3, timeline1.getId());
		
        
    }
	@Test
    public void getEvent_id() {
        assertEquals(0, e1.getEvent_id());
    }
	@Test
    public void setEvent_id() {
       e1.setEvent_id(14);
       assertEquals(14, e1.getEvent_id());
    }
	@Test
    public void setLevel() {
		e1.setLevel(1);
		assertEquals(1, e1.getLevel());
    }

   @Test
    public void isDurational() {
        assertTrue(e1.isDurational());
        
    }
   @Test
    public void TesttoString(){
       String string = "eventName1,2017-05-01,2017-05-30,eventDescription1";
       assertEquals(string,e1.toString());
      
    }

   
   @Test
    public void TestcompareTo() {
      assertEquals(-1,e1.compareTo(e4));  
      assertEquals(1,e4.compareTo(e1));
      
      
    }

}
