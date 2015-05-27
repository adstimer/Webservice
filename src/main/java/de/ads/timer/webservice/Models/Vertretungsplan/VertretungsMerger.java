package de.ads.timer.webservice.Models.Vertretungsplan;

import java.util.Date;
import java.util.List;

import de.ads.timer.webservice.Parser.VertretungplanParser;
import de.ads.timer.webservice.persicetence.VertretungsRepository;

public class VertretungsMerger {

	// Identität wird durch Datum, Klasse, Absenz und Fach festgelegt
	VertretungsRepository vertretungsRep;
	private List<Vertretung> newVertretungenList;
	private Date date;
	public Integer setsInFile = 0, setsInDatabase = 0, setsAdded = 0,
			setsChanged = 0, setsRemoved = 0;

	public VertretungsMerger(VertretungplanParser parser,
			VertretungsRepository vertretungsRep) {
		this.newVertretungenList = parser.vertretungsList;
		this.vertretungsRep = vertretungsRep;
		this.date = parser.date;
	}

	public void merge() {
		this.setsInFile = this.newVertretungenList.size();
		for (Vertretung newVertretung : this.newVertretungenList) {
			Vertretung oldVertretung = this.vertretungsRep
					.findByIdentityCombination(newVertretung.datum,
							newVertretung.klassen, newVertretung.absenz,
							newVertretung.stunde);
			if (oldVertretung != null) {
				// Eintrag in Bestehenden gefunden
				if (!oldVertretung.isIdentical(newVertretung)) {
					didFindChanged(oldVertretung, newVertretung);
				}
			} else {
				// Eintrag nicht in Bestehenden gefunden -> neuer Eintrag
				didFindAdded(newVertretung);
			}
		}

		List<Vertretung> oldVertretungenList = this.vertretungsRep
				.findByDatum(this.date);

		this.setsInDatabase = oldVertretungenList.size();

		oldVertretungenList.removeIf(this.newVertretungenList::contains);
		oldVertretungenList.forEach(this::didFindRemoved);
	}

	private void didFindRemoved(Vertretung vertretung) {
		this.setsRemoved++;
		this.vertretungsRep.delete(vertretung);
	}

	private void didFindAdded(Vertretung vertretung) {
		this.setsAdded++;
		this.vertretungsRep.save(vertretung);
	}

	private void didFindChanged(Vertretung oldVertretung,
			Vertretung newVertretung) {
		this.setsChanged++;

		newVertretung.id = oldVertretung.id;
		this.vertretungsRep.save(newVertretung);
	}
}
