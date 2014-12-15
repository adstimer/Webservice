package de.ads.timer.webservice.Models.Vertretungsplan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.ads.timer.webservice.persicetence.VertretungsRepository;

public class VertretungsMerger {

	@Autowired
	VertretungsRepository vertretungsRep;

	public VertretungsMerger(List<Vertretung> newVertretung) {
		this.vertretungsRep.findByDatum(newVertretung.get(0).datum);
		
		vertretungsRep.save(newVertretung);
	}
}
