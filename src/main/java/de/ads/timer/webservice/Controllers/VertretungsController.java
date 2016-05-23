package de.ads.timer.webservice.Controllers;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ads.timer.webservice.Models.Registration;
import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.Models.Vertretungsplan.VertretungsMerger;
import de.ads.timer.webservice.Notification.MessageController;
import de.ads.timer.webservice.Parser.VertretungplanParser;
import de.ads.timer.webservice.exception.ExpiredAuthToken;
import de.ads.timer.webservice.exception.InValidAuthToken;
import de.ads.timer.webservice.persicetence.RegistrationRepository;
import de.ads.timer.webservice.persicetence.RegistrationTokenRepository;
import de.ads.timer.webservice.persicetence.VertretungsRepository;

@Controller
@RequestMapping("vertretungen")
public class VertretungsController {

	@Autowired
	VertretungsRepository vertretungsRep;
	@Autowired
	RegistrationRepository registrationRep;
	@Autowired
	RegistrationTokenRepository registrationTokenRep;
	@Autowired
	MessageController messageController;

	@Deprecated
	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getAll() {
		Iterable<Vertretung> vertretungen = this.vertretungsRep.findAll();
		return vertretungen;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<Integer, List<Vertretung>> getByAuthToken(@RequestParam("authToken") String authToken)
			throws ParseException {
		if (!authToken.isEmpty() && this.registrationRep.exists(authToken)) {
			Registration registration = this.registrationRep.findOne(authToken);
			if (registration.registrationToken.isValid()) {
				Map<Integer, List<Vertretung>> vertretungen = new HashMap<Integer, List<Vertretung>>();
				List<Date> dates = this.vertretungsRep.findNextDays(new PageRequest(0, 3));
				for (Integer i = 0; i < dates.size(); i++) {
					vertretungen.put(i, this.vertretungsRep.findByAuthToken(authToken, dates.get(i)));
				}
				return vertretungen;

			} else {
				// Token nicht mehr gültig CODE 412
				throw new ExpiredAuthToken();
			}
		} else {
			// Kein Token angegeben oder nicht registriert CODE 403
			throw new InValidAuthToken();
		}
	}

	@Deprecated
	@RequestMapping(value = "date", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getByDate(@RequestParam("date") String dateString) throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
		return this.vertretungsRep.findByDatum(date);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getFileUpload() {
		return "vertretungsplanUpload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				VertretungplanParser parser = new VertretungplanParser();
				file.transferTo(new File("/usr/" + Long.toString(new Date().getTime())));
				SAXParserImpl.newInstance(null).parse(file.getInputStream(), parser);
				VertretungsMerger merger = new VertretungsMerger(parser, this.vertretungsRep, this.messageController);
				merger.merge();
				return "setsAdded: " + merger.setsAdded.toString() + "\n setsChanged: " + merger.setsChanged.toString()
						+ "\n setsRemoved: " + merger.setsRemoved.toString();
			} catch (Exception e) {
				return "You failed to upload  => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
}