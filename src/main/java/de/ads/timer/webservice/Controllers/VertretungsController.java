package de.ads.timer.webservice.Controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ads.timer.webservice.Models.Registration;
import de.ads.timer.webservice.Models.RegistrationToken;
import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.Models.Vertretungsplan.VertretungsMerger;
import de.ads.timer.webservice.Parser.VertretungplanParser;
import de.ads.timer.webservice.persicetence.RegistrationRepository;
import de.ads.timer.webservice.persicetence.RegistrationTokenRepository;
import de.ads.timer.webservice.persicetence.VertretungsRepository;

@Controller
@RequestMapping("vertretungen")
public class VertretungsController {

	@Autowired
	VertretungsRepository vertrtungsRep;
	@Autowired
	RegistrationRepository registrationRep;
	@Autowired
	RegistrationTokenRepository registrationTokenRep;

	@Deprecated
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getAll() {
		return this.vertrtungsRep.findAll();
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public String registerWithTokenForKlasse(@RequestBody Registration registration) {
		if (!registration.klasse.isEmpty() && !registration.os.equals(null) && !registration.registrationToken.equals(null)) /*Field Validation*/{
			if (RegistrationToken.isValid(registration.registrationToken.token, registrationTokenRep)) /*Token Validation */{
				if (this.registrationRep.existsByPushToken(registration.pushToken)) {
					// Bereits registriert
					Registration oldRegistration = this.registrationRep.findOneByPushToken(registration.pushToken);
					oldRegistration.lastActivity = new Date();
					oldRegistration.klasse = registration.klasse;
					this.registrationRep.save(oldRegistration);
					
					return oldRegistration.registrationToken.expireDate.toString();
				} else {
					// Neue Registrierung
					registration.registrationToken = registrationTokenRep.findOne(registration.registrationToken.token);
					this.registrationRep.save(registration);
					
					return registration.authToken;
				}
			} else {
				//Token nicht valid
				return "Internal Server Error 0x0001";
			}
		} else {
			//Nicht alle notwendigen Angaben gemacht
			return "Internal Server Error 0x0000";
		}

		//return "ok";
	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	@ResponseBody
//	public Iterable<Vertretung> getByAuthToken(
//			@RequestParam("authToken") String authToken) throws ParseException {
//	}
	
	@Deprecated
	@RequestMapping(value = "date", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getByDate(
			@RequestParam("date") String dateString) throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
		return this.vertrtungsRep.findByDatum(date);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getFileUpload() {
		return "vertretungsplanUpload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				VertretungplanParser parser = new VertretungplanParser();
				SAXParserImpl.newInstance(null).parse(file.getInputStream(),
						parser);
				VertretungsMerger merger = new VertretungsMerger(parser,
						this.vertrtungsRep);
				merger.merge();
				return "setsAdded: " + merger.setsAdded.toString()
						+ "\n setsChanged: " + merger.setsChanged.toString()
						+ "\n setsRemoved: " + merger.setsRemoved.toString();
			} catch (Exception e) {
				return "You failed to upload  => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
}