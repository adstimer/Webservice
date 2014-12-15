package de.ads.timer.webservice.persicetence;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;

public interface VertretungsRepository extends CrudRepository<Vertretung, Long> {
	public Iterable<Vertretung> findByDatum(Date datum);
}
