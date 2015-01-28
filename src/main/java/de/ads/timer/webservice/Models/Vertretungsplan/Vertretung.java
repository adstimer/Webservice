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
	
	// Identität wird durch Datum, Klasse, Absenz und Fach festgelegt.
	// Sie bestimmen wer welche Stunde vertreten bekommt. Die anderen Werte sind Parameter, die die Vertreung beschreiben.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absenz == null) ? 0 : absenz.hashCode());
		result = prime * result + ((art == null) ? 0 : art.hashCode());
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + ((fach == null) ? 0 : fach.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((klassen == null) ? 0 : klassen.hashCode());
		result = prime * result + ((raum == null) ? 0 : raum.hashCode());
		result = prime * result + ((stunden == null) ? 0 : stunden.hashCode());
		result = prime * result
				+ ((vertreter == null) ? 0 : vertreter.hashCode());
		return result;
	}

	public boolean isIdenical(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertretung other = (Vertretung) obj;
		if (absenz == null) {
			if (other.absenz != null)
				return false;
		} else if (!absenz.equals(other.absenz))
			return false;
		if (art == null) {
			if (other.art != null)
				return false;
		} else if (!art.equals(other.art))
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (fach == null) {
			if (other.fach != null)
				return false;
		} else if (!fach.equals(other.fach))
			return false;
		if (klassen == null) {
			if (other.klassen != null)
				return false;
		} else if (!klassen.equals(other.klassen))
			return false;
		if (raum == null) {
			if (other.raum != null)
				return false;
		} else if (!raum.equals(other.raum))
			return false;
		if (stunden == null) {
			if (other.stunden != null)
				return false;
		} else if (!stunden.equals(other.stunden))
			return false;
		if (vertreter == null) {
			if (other.vertreter != null)
				return false;
		} else if (!vertreter.equals(other.vertreter))
			return false;
		return true;
	}
	
	public boolean equals(Object obj) {
		Vertretung compareObj = (Vertretung)obj;
		if (compareObj.absenz != this.absenz) {
			return false;
		}
		if (compareObj.datum != this.datum) {
			return false;
		}
		if (!this.klassen.containsAll(compareObj.klassen)){
			return false;
		}
		if (compareObj.fach != this.fach) {
			return false;
		}
		return true;
	}
}
