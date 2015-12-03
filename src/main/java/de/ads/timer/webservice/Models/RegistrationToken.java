package de.ads.timer.webservice.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import de.ads.timer.webservice.persicetence.RegistrationTokenRepository;

@Entity(name = "RegistrationToken")
@Table(name = "registrationToken")
public class RegistrationToken {

	@Id
	public String token;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET")
	public Date expireDate;

	public RegistrationToken() {
	}

	public boolean isValid() {
		return this.expireDate.after(org.joda.time.LocalDate.now().toDate());
	}

	public static boolean isValid(String token, RegistrationTokenRepository repo) {

		return repo.exists(token) && repo.findOne(token).isValid();
	}

	public RegistrationToken(String token) {
		this.token = token;
	}

}
