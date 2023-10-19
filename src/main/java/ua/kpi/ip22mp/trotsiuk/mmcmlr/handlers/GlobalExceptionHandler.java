package ua.kpi.ip22mp.trotsiuk.mmcmlr.handlers;

import org.apache.commons.math3.linear.SingularMatrixException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SingularMatrixException.class)
    @ResponseStatus(BAD_REQUEST)
    public void handleSingularMatrixException() {
        LOGGER.info("Independent variables matrix is singular");
    }
}
