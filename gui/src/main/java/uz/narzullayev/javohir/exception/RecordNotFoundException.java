package uz.narzullayev.javohir.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Slf4j
public class RecordNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message) {
        super(message);
        log.warn(message);
    }

    public RecordNotFoundException(String message, Throwable t) {
        super(message, t);
        log.warn(message);
    }
}

