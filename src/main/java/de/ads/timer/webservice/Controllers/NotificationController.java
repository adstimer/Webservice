package de.ads.timer.webservice.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import de.ads.timer.webservice.Models.Registration;
import de.ads.timer.webservice.persicetence.RegistrationRepository;

@Controller
@RequestMapping("notification")
public class NotificationController {

	@Autowired
	private RegistrationRepository registrationRep;
	@Autowired
	private ApnsService apnsService;

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public String registerWithTokenForKlasse(
			@RequestBody Registration registration) {

		if (this.registrationRep.exists(registration.pushToken)) {
			registration = this.registrationRep.findOne(registration.pushToken);
			registration.lastActivity = new Date();
			this.registrationRep.save(registration);
		} else {
			this.registrationRep.save(registration);
		}

		return "ok";
	}

	@RequestMapping(value = "send", method = RequestMethod.GET)
	@ResponseBody
	public String sendTestNotification() {
		String payload = APNS.newPayload().alertBody("Test Notification")
				.build();
		List<Registration> registrations = this.registrationRep
				.findByKlasse("Q1");

		Collection<String> deviceTokens = new ArrayList<String>();
		registrations.forEach((registration) -> {
			deviceTokens.add(registration.pushToken);
		});
		this.apnsService.push(deviceTokens, payload);

		return payload;
	}
}