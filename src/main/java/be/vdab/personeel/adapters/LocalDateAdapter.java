package be.vdab.personeel.adapters;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public LocalDate unmarshal(String dateString) {
		return LocalDate.parse(dateString);
	}

	@Override
	public String marshal(LocalDate date) {
		return date.toString();
	}
}