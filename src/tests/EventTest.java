package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import main.model.Event;
import main.model.Timeline;

public class EventTest {
	Timeline tl = new Timeline();
	Event ev = new Event("Fishing boats", "TestDesc", null);


	@Before
	public void setUp() throws Exception {

	}


	@Test
	public void testgetEvent_title() {
		assertEquals(ev.getEvent_title(), "Fishing boats");

	}

	@Test
	public void testgetEvent_description() {
		assertEquals(ev.getEvent_description(), "TestDesc");
	}


	@Test
	public void testgetTimeline_id() {
		tl.addEvent(ev);
		assertEquals(ev.getTimeline_id(), 0);
	}


	@Test
	public void TestgetEvent_id() {
		tl.addEvent(ev);
		assertEquals(ev.getEvent_id(), 0);
	}



}
