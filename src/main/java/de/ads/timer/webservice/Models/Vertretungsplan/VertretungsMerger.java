package de.ads.timer.webservice.Models.Vertretungsplan;

import java.util.List;

import de.ads.timer.webservice.persicetence.VertretungsRepository;

public class VertretungsMerger {

	// Identität wird durch Datum, Klasse, Absenz und Fach festgelegt
	VertretungsRepository vertretungsRep;
	private List<Vertretung> newVertretungenList;
	

	public VertretungsMerger(List<Vertretung> newVertretungenList, VertretungsRepository vertretungsRep) {
		this.newVertretungenList = newVertretungenList;
		this.vertretungsRep = vertretungsRep;
	}
	
	
	
	public void merge() {
		for (Vertretung newVertretung : newVertretungenList) {
			Vertretung oldVertretung = vertretungsRep.findByDatumAndKlassenAndAbsenzAndFach(newVertretung.datum, newVertretung.klassen, newVertretung.absenz, newVertretung.fach);
			if (oldVertretung != null) {
				// Eintrag in Betehenden gefunden
				if (!oldVertretung.isIdenical(newVertretung)) {
					didFindChanged(oldVertretung, newVertretung);
				}
			} else {
				// Eintrag nicht in Bestehenden gefunden -> neuer Eintrag
				didFindAdded(newVertretung);
			}
		}
		
		List<Vertretung> oldVertretungenList = vertretungsRep
													.findByDatum(newVertretungenList.get(0).datum);
		if (oldVertretungenList.size() != newVertretungenList.size()) {
			// Einträge wurden gelöscht
			for (Vertretung vertretung : oldVertretungenList) {
				// Vorhandene herausfiltern
				if(newVertretungenList.contains(vertretung)) {
					oldVertretungenList.remove(vertretung);
				}
			}
			for (Vertretung vertretung : oldVertretungenList) {
				// Für nicht vorhandene delete ausführen
				didFindRemoved(vertretung);
			}
		}
	}
	
	private void didFindRemoved(Vertretung vertretung) {
		vertretungsRep.delete(vertretung);
	}
	
	private void didFindAdded(Vertretung vertretung) {
		vertretungsRep.save(vertretung);
	}
	
	private void didFindChanged(Vertretung oldVertretung, Vertretung newVertretung) {
		newVertretung.id = oldVertretung.id;
		vertretungsRep.save(newVertretung);
	}
}
