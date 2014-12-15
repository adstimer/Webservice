package de.ads.timer.webservice.Models.Vertretungsplan;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "vertretung")
@Table(name = "vertretung")
public class Vertretung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@ElementCollection
	public List<String> klassen;
	@ElementCollection
	public List<String> stunden;
	public String vertreter;
	public String fach;
	public String raum;
	public String absenz;
	public String art;
	@Temporal(TemporalType.DATE)
	public Date datum;

	public Vertretung() {
	}

	public Vertretung(List<String> klassen, List<String> stunden,
			String vertreter, String fach, String raum, String absenz,
			String art, Date datum) {
		super();
		this.klassen = klassen;
		this.stunden = stunden;
		this.vertreter = vertreter;
		this.fach = fach;
		this.raum = raum;
		this.absenz = absenz;
		this.art = art;
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Vertretung [id=" + this.id + ", klassen=" + this.klassen
				+ ", stunden=" + this.stunden + ", vertreter=" + this.vertreter
				+ ", fach=" + this.fach + ", raum=" + this.raum + ", absenz="
				+ this.absenz + ", art=" + this.art + ", datum=" + this.datum
				+ "]";
	}

}
