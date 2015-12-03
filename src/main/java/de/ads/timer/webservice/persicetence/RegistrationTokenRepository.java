package de.ads.timer.webservice.persicetence;

import org.springframework.data.repository.CrudRepository;

import de.ads.timer.webservice.Models.RegistrationToken;

public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, String> {

}
