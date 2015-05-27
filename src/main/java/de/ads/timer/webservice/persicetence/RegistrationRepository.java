package de.ads.timer.webservice.persicetence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.ads.timer.webservice.Models.Registration;

public interface RegistrationRepository extends
		CrudRepository<Registration, String> {

	public List<Registration> findByKlasse(String klasse);

}
