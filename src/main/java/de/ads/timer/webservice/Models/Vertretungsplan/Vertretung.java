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

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity(name = "vertretung")
@Table(name = "vertretung")
public class Vertretung {

	// Identität wird durch Datum, Klasse, Absenz und Fach festgelegt.
	// Sie bestimmen wer welche Stunde vertreten bekommt. Die anderen Werte sind
	// Parameter, die die Vertreung beschreiben.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@ElementCollection
	public List<String> klassen;
	public Integer stunde;
	public String vertreter;
	public String fach;
	public String raum;
	public String absenz;
	public String art;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET")
	public Date datum;

	public Vertretung() {
	}

	public Vertretung(List<String> klassen, Integer stunde, String vertreter, String fach, String raum, String absenz,
			String art, Date datum) {
		super();
		this.klassen = klassen;
		this.stunde = stunde;
		this.vertreter = vertreter;
		this.fach = fach;
		this.raum = raum;
		this.absenz = absenz;
		this.art = art;
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Vertretung [id=" + this.id + ", klassen=" + this.klassen + ", stunde=" + this.stunde + ", vertreter="
				+ this.vertreter + ", fach=" + this.fach + ", raum=" + this.raum + ", absenz=" + this.absenz + ", art="
				+ this.art + ", datum=" + this.datum + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vertretung other = (Vertretung) obj;
		if (this.absenz == null) {
			if (other.absenz != null) {
				return false;
			}
		} else if (!this.absenz.equals(other.absenz)) {
			return false;
		}
		if (this.datum == null) {
			if (other.datum != null) {
				return false;
			}
		} else if (!DateUtils.isSameDay(this.datum, other.datum)) {
			return false;
		}
		if (this.klassen == null) {
			if (other.klassen != null) {
				return false;
			}
		} else if (!this.klassen.equals(other.klassen)) {
			return false;
		}
		if (this.stunde == null) {
			if (other.stunde != null) {
				return false;
			}
		} else if (!this.stunde.equals(other.stunde)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.absenz == null) ? 0 : this.absenz.hashCode());
		result = prime * result + ((this.art == null) ? 0 : this.art.hashCode());
		result = prime * result + ((this.datum == null) ? 0 : this.datum.hashCode());
		result = prime * result + ((this.fach == null) ? 0 : this.fach.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.klassen == null) ? 0 : this.klassen.hashCode());
		result = prime * result + ((this.raum == null) ? 0 : this.raum.hashCode());
		result = prime * result + ((this.stunde == null) ? 0 : this.stunde.hashCode());
		result = prime * result + ((this.vertreter == null) ? 0 : this.vertreter.hashCode());
		return result;
	}

	public boolean isIdentical(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vertretung other = (Vertretung) obj;
		if (this.absenz == null) {
			if (other.absenz != null) {
				return false;
			}
		} else if (!this.absenz.equals(other.absenz)) {
			return false;
		}
		if (this.art == null) {
			if (other.art != null) {
				return false;
			}
		} else if (!this.art.equals(other.art)) {
			return false;
		}
		if (this.datum == null) {
			if (other.datum != null) {
				return false;
			}
		} else if (!DateUtils.isSameDay(this.datum, other.datum)) {
			return false;
		}
		if (this.fach == null) {
			if (other.fach != null) {
				return false;
			}
		} else if (!this.fach.equals(other.fach)) {
			return false;
		}
		if (this.klassen == null) {
			if (other.klassen != null) {
				return false;
			}
		} else if (!this.klassen.containsAll(other.klassen)) {
			return false;
		}
		if (this.raum == null) {
			if (other.raum != null) {
				return false;
			}
		} else if (!this.raum.equals(other.raum)) {
			return false;
		}
		if (this.stunde == null) {
			if (other.stunde != null) {
				return false;
			}
		} else if (!this.stunde.equals(other.stunde)) {
			return false;
		}
		if (this.vertreter == null) {
			if (other.vertreter != null) {
				return false;
			}
		} else if (!this.vertreter.equals(other.vertreter)) {
			return false;
		}
		return true;
	}
}
