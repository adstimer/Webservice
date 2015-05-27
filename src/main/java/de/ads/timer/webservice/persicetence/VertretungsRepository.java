package de.ads.timer.webservice.persicetence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;

public interface VertretungsRepository extends CrudRepository<Vertretung, Long> {
	public List<Vertretung> findByDatum(Date datum);

	@Query("SELECT DISTINCT(v) FROM vertretung v WHERE v.datum = :datum AND v.klassen IN :klassen AND v.absenz = :absenz AND  v.stunde = :stunde")
	public Vertretung findByIdentityCombination(@Param("datum") Date datum,
			@Param("klassen") List<String> klassen,
			@Param("absenz") String absenz, @Param("stunde") Integer stunde);
}
