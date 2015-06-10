package de.ads.timer.webservice.persicetence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.ads.timer.webservice.Models.Registration;

public interface RegistrationRepository extends
		CrudRepository<Registration, String> {

	public List<Registration> findByKlasse(String klasse);
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN 'true' ELSE 'false' END FROM registration r WHERE r.pushToken = ?1")
    public Boolean existsByPushToken(String pushToken);
	public Registration findOneByPushToken(String pushToken);

}
