package de.ads.timer.webservice.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ads.timer.webservice.Models.Vertretungsplan.Vertretung;
import de.ads.timer.webservice.persicetence.VertretungsRepository;

@Controller
@RequestMapping("init")
public class InitController {

	@Autowired
	VertretungsRepository vertretungsRep;

	@RequestMapping("vertretungen")
	@ResponseBody
	public String initVertretungen() {

		List<String> klassen = new ArrayList<String>();
		klassen.add("6a");

		this.vertretungsRep.save(new Vertretung(klassen, 2, "Pau", "D", "115",
				"We", "EVA", new Date()));
		return "ok";
	}
}
