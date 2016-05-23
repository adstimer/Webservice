package de.ads.timer.webservice.Notification;

import java.util.ArrayList;
import java.util.List;

public class GCMProtocol {
	public List<String> registration_ids = new ArrayList<String>();
	public GCMNotification notification;
	
	public GCMProtocol(String message, List<String> tokens) {
		this.registration_ids = tokens;
		this.notification = new GCMNotification(message);
	}
}
