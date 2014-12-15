package de.ads.timer.webservice.Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.Models.Vertretungsplan.VertretungsMerger;
import de.ads.timer.webservice.Parser.VertretungplanParser;
import de.ads.timer.webservice.persicetence.VertretungsRepository;

@Controller
@RequestMapping("vertretungen")
public class VertretungsController {

	@Autowired
	VertretungsRepository vertrtungsRep;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getAll() {
		return this.vertrtungsRep.findAll();
	}
	
	@RequestMapping(value = "date", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Vertretung> getByDate(@RequestParam("date") String dateString) throws ParseException {
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
		return vertrtungsRep.findByDatum(date);
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
//				VertretungsMerger merger = new VertretungsMerger(parser.vertretungsList);
				vertrtungsRep.save(parser.vertretungsList);
				return new Integer(parser.vertretungsList.size()).toString();
			} catch (Exception e) {
				return "You failed to upload  => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
}