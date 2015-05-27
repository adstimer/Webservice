package de.ads.timer.webservice.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "registration")
@Table(name = "registration")
public class Registration {

	@Id
	public String token;
	public String klasse;
	@Enumerated(EnumType.STRING)
	public Device device;
	@Temporal(TemporalType.TIMESTAMP)
	public Date registered = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastActivity = new Date();

	public Registration() {
	}

}
