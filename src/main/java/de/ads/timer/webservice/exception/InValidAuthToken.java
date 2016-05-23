package de.ads.timer.webservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "bad authToken")
public class InValidAuthToken extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
