package de.ads.timer.webservice.Controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notnoop.apns.ApnsService;

import de.ads.timer.webservice.Models.Registration;
import de.ads.timer.webservice.Models.RegistrationToken;
import de.ads.timer.webservice.exception.BadRequest;
import de.ads.timer.webservice.persicetence.RegistrationRepository;
import de.ads.timer.webservice.persicetence.RegistrationTokenRepository;

@Controller
@RequestMapping("register")
public class RegistrationController {

	@Autowired
	private RegistrationRepository registrationRep;
	@Autowired
	private ApnsService apnsService; 
	@Autowired
	RegistrationTokenRepository registrationTokenRep;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Registration registerWithTokenForKlasse(@RequestBody Registration registration) {
		if (!registration.klasse.isEmpty() && !registration.os.equals(null) && !registration.registrationToken
				.equals(null)) /* Field Validation */ {
			if (RegistrationToken.isValid(registration.registrationToken.token,
					this.registrationTokenRep)) /* Token Validation */ {
				if (this.registrationRep.existsByPushToken(registration.pushToken)) {
					// Bereits registriert
					Registration oldRegistration = this.registrationRep.findOneByPushToken(registration.pushToken);
					oldRegistration.lastActivity = new Date();
					oldRegistration.klasse = registration.klasse;
					this.registrationRep.save(oldRegistration);

					return oldRegistration;
				} else {
					// Neue Registrierung
					registration.registrationToken = this.registrationTokenRep
							.findOne(registration.registrationToken.token);
					this.registrationRep.save(registration);

					return registration;
				}
			} else {
				// Token nicht valid
				// return "Internal Server Error 0x0001";
				throw new BadRequest();
			}
		} else {
			// Nicht alle notwendigen Angaben gemacht
			// return "Internal Server Error 0x0000";
			throw new BadRequest();
		}

		// return "ok";
	}

//	@RequestMapping(value = "send", method = RequestMethod.GET)
//	@ResponseBody
//	public String sendTestNotification(@RequestParam("klasse") String klasse) {
//		String payload = APNS.newPayload().alertBody("Test Notification").badge(1).sound("default").build();
//		List<Registration> registrations = this.registrationRep.findPushTokensByKlassen(ne, os) 
//
//		Collection<String> deviceTokens = new ArrayList<String>();
//		registrations.forEach((registration) -> {
//			deviceTokens.add(registration.pushToken);
//		});
//		this.apnsService.push(deviceTokens, payload);
//
//		return payload;
//	}
}