package kr.kohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Menu not founded.")
public class MenuNotFoundException extends RuntimeException {
}
