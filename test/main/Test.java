package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import main.model.*;


public class Test {
	public static void main(String[] args){
		
	Timeline timeline1 = new Timeline("title1", null, null,"DescriptionTL 1");
	Event e1 = new Event("eventName1","eventDescription1", null, null);
	Event e2 = new Event("eventName2","eventDescription2", null, null);
	Event e3 = new Event("eventName3","eventDescription3", null, null);
	
	timeline1.addEvent(e1);
	timeline1.addEvent(e2);
	timeline1.addEvent(e3);
	System.out.println(timeline1.toString());
	String info = timeline1.toString();
	try {
		File savedfile = new File("C:/Temp/testfilen.txt");
		FileWriter fw = new FileWriter(savedfile);
		fw.write(info);
		fw.flush();
		
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	File savedfile = new File("C:/Temp/testfilen.txt");
	try {
		Scanner scan= new Scanner(savedfile);
		int ind =0;
		int current= 0;
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	
	}
	
	
}
	
	

