package de.ads.timer.webservice.Notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import de.ads.timer.webservice.Models.OS;
import de.ads.timer.webservice.Models.Vertretungsplan.MergerInterface;
import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.persicetence.RegistrationRepository;

@Component
public class MessageController implements MergerInterface {
	
	@Autowired
	ApnsService apnsService;
	@Autowired
	GCMNotificationService gcmService;
	@Autowired
	private RegistrationRepository registrationRep;
	
	private List<String> klassen = new ArrayList<String>();
	
	public MessageController()  {
		
	}

	@Override
	public void didFindAdded(Vertretung vertretung) {
		for (String klasse : vertretung.klassen) {
			if (!klassen.contains(klasse)) {
				klassen.add(klasse);
			}
		}
	}

	@Override
	public void didFindRemoved(Vertretung vertretung) {
		for (String klasse : vertretung.klassen) {
			if (!klassen.contains(klasse)) {
				klassen.add(klasse);
			}
		}
	}
	
	public void didFindChanged(Vertretung oldVertretung, Vertretung newVertretung) {
		for (String klasse : oldVertretung.klassen) {
			if (!klassen.contains(klasse)) {
				klassen.add(klasse);
			}
		}	
	}

	private void sendNotification(String message, List<String> klassen) {
		if (klassen.size() <= 0) return;
		String payload = APNS.newPayload().alertBody(message).badge(1).sound("default").build();
		List<String> pushTokens = this.registrationRep.findPushTokensByKlassen(klassen, OS.iOS); 

		this.apnsService.push(pushTokens, payload);
		
		pushTokens = this.registrationRep.findPushTokensByKlassen(klassen, OS.Android); 
		this.gcmService.send(message, pushTokens);
		
		
		
	}

	@Override
	public void didFinish() {
		sendNotification("Deine Vertretungen haben sich geändert! /n Schau gleich mal nach.", klassen);
	}
}
