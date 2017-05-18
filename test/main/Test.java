package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.xml.internal.ws.util.Pool.Unmarshaller;

import main.model.*;


public class Test {
	public static void main(String[] args) throws JAXBException{
		String t1 = "2017-05-01";
		String t2 = "2017-05-30";
		LocalDate date1 = LocalDate.parse(t1.subSequence(0, 10));
		LocalDate date2 = LocalDate.parse(t2.subSequence(0, 10));

		Timeline timeline1 = new Timeline("title1", date1, date2,"DescriptionTL 1");
		Event e1 = new Event("eventName1","eventDescription1", date1, date2);
		Event e2 = new Event("eventName2","eventDescription2", date1, date2);
		Event e3 = new Event("eventName3","eventDescription3", date1, date2);

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
		jaxbObjectToXML(timeline1);
		
		Timeline tl2 = XMLtoObject(new File("C:\\Temp\\Timeline.xml"));
		System.out.println("Fishingboat");
		System.out.println(tl2.toString());

	}

	private static void jaxbObjectToXML(Timeline tl) {

		try {
			JAXBContext context = JAXBContext.newInstance(Timeline.class);
			Marshaller m = context.createMarshaller();
			//for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// Write to File
			m.marshal(tl, new File("C:\\Temp\\Timeline.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	private static Timeline XMLtoObject(File file) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(Timeline.class);
		javax.xml.bind.Unmarshaller unMarshaller = context.createUnmarshaller();

		return (Timeline) unMarshaller.unmarshal(file);

	}



}






