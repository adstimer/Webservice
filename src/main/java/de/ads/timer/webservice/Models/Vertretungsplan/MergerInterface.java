package de.ads.timer.webservice.Models.Vertretungsplan;

public interface MergerInterface {
	
	public void didFindAdded(Vertretung vertretung);
	public void didFindRemoved(Vertretung vertretung);
	public void didFindChanged(Vertretung oldVertretung, Vertretung newVertretung);
	public void didFinish();
	
}
