package main.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.*;


public class LocalDatetoXMLAdapter extends XmlAdapter<String, LocalDate>{

	@Override
	public String marshal(LocalDate LDT) throws Exception {
		
		return LDT.toString();
	}

	@Override
	public LocalDate unmarshal(String LDT) throws Exception {
		
		return LocalDate.parse(LDT);
	}
	

}
