package de.ads.timer.webservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ads.timer.webservice.Models.OS;
import de.ads.timer.webservice.Notification.GCMNotificationService;
import de.ads.timer.webservice.persicetence.RegistrationRepository;

@Controller
@RequestMapping("init")
public class InitController {

	@Autowired
	RegistrationRepository regRep;

	@RequestMapping("send")
	@ResponseBody
	public String initVertretungen() {

		GCMNotificationService service = new GCMNotificationService();
		service.send("Test", regRep.findPushTokenByOs(OS.Android));
		return "ok";
	}
}
