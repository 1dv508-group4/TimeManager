package tests;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

import main.model.*;

import org.junit.Test;

public class TimelineTest {
	Timeline tl = new Timeline();
	Timeline tl2 = new Timeline("goat", null, null,"asodnasd");
	
	

	
	
	@Test
	public void testTitle() {
	tl.setTitle("Fish");	
	System.out.println(tl.getTitle());
	assertEquals("Fish", tl.getTitle());
	
	}
	@Test
	public void testID(){
		tl.setId(1);
		assertEquals(1,tl.getId());
		
	}
	@Test
	public void testDesc(){
		tl.setDescription("teststring");
		assertEquals("teststring", tl.getDescription());
	}
	@Test
	public void testListOfEvents() {
		assertTrue(tl.isEmpty());
		assertEquals(0,tl.size());
		
	}

}
