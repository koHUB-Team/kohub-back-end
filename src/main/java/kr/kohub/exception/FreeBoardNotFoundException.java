package kr.kohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Free Board Not Founde.")
public class FreeBoardNotFoundException extends RuntimeException {

}
