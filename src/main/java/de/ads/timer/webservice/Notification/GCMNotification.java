package de.ads.timer.webservice.Notification;

public class GCMNotification {
	public String body = "";
	public String title = "ADS Timer";
	
	public GCMNotification(String message) {
		this.body = message;
	}
}
