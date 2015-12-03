package de.ads.timer.webservice.Notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import de.ads.timer.webservice.Models.OS;
import de.ads.timer.webservice.Models.Registration;
import de.ads.timer.webservice.Models.Vertretungsplan.MergerInterface;
import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.persicetence.RegistrationRepository;

@Component
public class MessageController implements MergerInterface {
	
	@Autowired
	ApnsService apnsService;
	
	@Autowired
	private RegistrationRepository registrationRep;
	
	public MessageController()  {
		
	}

	@Override
	public void didFindAdded(Vertretung vertretung) {
		sendNotification("Es gibt neue Vertretungen! \n Schau gleich mal nach", vertretung.klassen);
	}

	@Override
	public void didFindRemoved(Vertretung vertretung) {
		sendNotification("Eine deiner Stunden finden doch statt! \n Schau gleich mal nach", vertretung.klassen);
	}
	
	public void didFindChanged(Vertretung oldVertretung, Vertretung newVertretung) {
		sendNotification("Eine deiner Vertretungen hat sich geändert! \n Schau gleich mal nach", oldVertretung.klassen);		
	}

	private void sendNotification(String text, List<String> klassen) {
		String payload = APNS.newPayload().alertBody(text).badge(1).sound("default").build();
		List<String> pushTokens = this.registrationRep.findPushTokensByKlassen(klassen, OS.iOS); 

		this.apnsService.push(pushTokens, payload);
	}
}
