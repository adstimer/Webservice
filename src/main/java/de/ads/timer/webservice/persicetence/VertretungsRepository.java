package de.ads.timer.webservice.persicetence;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;

public interface VertretungsRepository extends CrudRepository<Vertretung, Long> {
	public List<Vertretung> findByDatum(Date datum);
	public Vertretung findByDatumAndKlassenAndAbsenzAndFach(Date datum, List<String> klassen, String absenz, String fach);
}
