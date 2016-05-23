package de.ads.timer.webservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "expired registrationToken")
public class ExpiredAuthToken extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
