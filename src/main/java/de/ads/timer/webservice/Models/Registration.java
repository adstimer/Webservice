package de.ads.timer.webservice.Models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "registration")
@Table(name = "registration")
public class Registration {	
	@Id
	public String authToken = Long.toString(UUID.randomUUID().getMostSignificantBits());
	public String pushToken;
	public String klasse;
	@OneToOne
	public RegistrationToken registrationToken;
	@Enumerated(EnumType.STRING)
	public OS os;
	@Temporal(TemporalType.TIMESTAMP)
	public Date registrationDate = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastActivity = new Date();

	public Registration() {
	}
}
