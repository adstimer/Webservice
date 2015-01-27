package de.ads.timer.webservice.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;

public class VertretungplanParser extends DefaultHandler {

	String tempVal = "";
	Vertretung tempVertretung;
	Boolean firstSkipped = false;
	public ArrayList<Vertretung> vertretungsList = new ArrayList<Vertretung>();
	int fieldCounter;
	Date date;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("tr")) {
			if (this.firstSkipped) {
				this.tempVertretung = new Vertretung();
				this.fieldCounter = 0;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		this.tempVal = new String(ch, start, length);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("tr")) {
			if (this.firstSkipped) {
				this.tempVertretung.datum = this.date;
				this.vertretungsList.add(this.tempVertretung);
				this.tempVertretung = null;
			} else {
				this.firstSkipped = true;
			}
		}

		if (qName.equalsIgnoreCase("div")) {
			String[] values = tempVal.split(Pattern.quote("."));
			this.date = new Date();
			this.date.setDate(Integer.parseInt(values[0]));
			this.date.setMonth(Integer.parseInt(values[1])-1);
			String[] year = values[2].split(Pattern.quote(" "));
			this.date.setYear(Integer.parseInt(year[0]) - 1900 );
		}

		if (qName.equalsIgnoreCase("td") && this.firstSkipped) {
			switch (this.fieldCounter) {
			case 0:
				List<String> klassen = new ArrayList<String>();
				klassen.add(this.tempVal);
				this.tempVertretung.klassen = klassen;
				break;
			case 1:
				List<String> stunden = new ArrayList<String>();
				stunden.add(this.tempVal);
				this.tempVertretung.stunden = stunden;
				break;
			case 2:
				this.tempVertretung.vertreter = this.tempVal;
				break;
			case 3:
				this.tempVertretung.fach = this.tempVal;
				break;
			case 4:
				this.tempVertretung.raum = this.tempVal;
				break;
			case 5:
				this.tempVertretung.absenz = this.tempVal;
				break;
			case 6:
				this.tempVertretung.art = this.tempVal;
				break;
			default:
				break;
			}
			this.fieldCounter++;
		}
	}
}
