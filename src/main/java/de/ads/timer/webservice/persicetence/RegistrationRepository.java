package de.ads.timer.webservice.persicetence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.ads.timer.webservice.Models.OS;
import de.ads.timer.webservice.Models.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, String> {

	@Query("SELECT DISTINCT(r.pushToken) FROM registration r where r.klasse in :klassen AND r.os = :os")
	public List<String> findPushTokensByKlassen(@Param("klassen") List<String> klassen, @Param("os") OS os);

	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN 'true' ELSE 'false' END FROM registration r WHERE r.pushToken = ?1")
	public Boolean existsByPushToken(String pushToken);

	public Registration findOneByPushToken(String pushToken);
	
	@Query("SELECT r.pushToken FROM registration r where r.os = :os AND NOT r.pushToken = ''")
	public List<String> findPushTokenByOs(@Param("os") OS android);

}
