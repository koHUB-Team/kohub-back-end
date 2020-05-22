package kr.kohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Promotion Not Found.")
public class PromotionNotFoundException extends RuntimeException {

}
