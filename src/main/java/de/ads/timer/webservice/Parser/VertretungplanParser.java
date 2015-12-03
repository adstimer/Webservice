package de.ads.timer.webservice.Parser;

import java.util.ArrayList;
import java.util.Arrays;
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
	List<Integer> stunden;
	Boolean currentIsDataTable = false;
	Boolean currentIsDateDiv = false;
	Boolean firstRowSkipped = false;
	public ArrayList<Vertretung> vertretungsList = new ArrayList<Vertretung>();
	int fieldCounter;
	public Date date;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("table") && attributes.getValue("class").equalsIgnoreCase("mon_list")) {
			this.currentIsDataTable = true;
			this.firstRowSkipped = false;
		} else if (this.currentIsDataTable && qName.equalsIgnoreCase("tr") && this.firstRowSkipped) {
			this.tempVertretung = new Vertretung();
			this.fieldCounter = 0;
		} else if (qName.equalsIgnoreCase("div") && attributes.getValue("class").equals("mon_title")) {
			this.currentIsDateDiv = true;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		this.tempVal = new String(ch, start, length);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("table")) {
			this.currentIsDataTable = false;
		} else if (this.currentIsDataTable && qName.equalsIgnoreCase("tr")) {
			if (!this.firstRowSkipped) {
				this.firstRowSkipped = true;
			} else {
				this.tempVertretung.datum = this.date;

				// Für jede Stunde wird für weniger Logik im Merger eine neue
				// Identität angelegt
				this.stunden.forEach((stunde) -> {
					this.tempVertretung.stunde = stunde;
					Vertretung copy = new Vertretung(tempVertretung.klassen, //Notwendig für Vertretungen mit mehreren Stunden, da sonst immer das selbe Objekt hinzugefügt wird
							stunde, 
							tempVertretung.vertreter, 
							tempVertretung.fach, 
							tempVertretung.raum, 
							tempVertretung.absenz, 
							tempVertretung.art, 
							tempVertretung.datum);
					this.vertretungsList.add(copy);
				});

				this.tempVertretung = null;
			}
		} else if (this.currentIsDataTable && qName.equalsIgnoreCase("td") && this.firstRowSkipped) {
			switch (this.fieldCounter) {
			case 0:
				List<String> klassen = new ArrayList<String>();
				klassen.addAll(Arrays.asList(this.tempVal.split(Pattern.quote(", "))));
				this.tempVertretung.klassen = klassen;
				break;
			case 1:
				this.stunden = new ArrayList<Integer>();
				if (this.tempVal.contains("-")) {
					// Stundenbereich
					int vonStunde = Integer.parseInt(this.tempVal.split(Pattern.quote(" - "))[0]);
					int bisStunde = Integer.parseInt(this.tempVal.split(Pattern.quote(" - "))[1]);

					for (int i = vonStunde; i <= bisStunde; i++) {
						this.stunden.add(i);
					}
				} else {
					// Einzelstunde
					this.stunden.add(Integer.parseInt(this.tempVal));
				}
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
			}
			this.fieldCounter++;
		} else if (this.currentIsDateDiv && qName.equalsIgnoreCase("div")) {
			this.currentIsDateDiv = false;
			String[] values = this.tempVal.split(Pattern.quote("."));
			this.date = new Date();
			this.date.setTime(0);
			this.date.setDate(Integer.parseInt(values[0]));
			this.date.setMonth(Integer.parseInt(values[1]) - 1);
			String[] year = values[2].split(Pattern.quote(" "));
			this.date.setYear(Integer.parseInt(year[0]) - 1900);
		}
	}
}
