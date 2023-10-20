package ua.kpi.ip22mp.trotsiuk.mmcmlr.handlers;

import org.apache.commons.math3.linear.SingularMatrixException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.ExceptionDto;

import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String BINDING_ERROR_JOINER = ": ";
    private static final String SINGULAR_MATRIX_EXCEPTION_MESSAGE = "Independent variables matrix is singular";

    @ExceptionHandler(SingularMatrixException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionDto handleSingularMatrixException() {
        return new ExceptionDto(singletonList(SINGULAR_MATRIX_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ExceptionDto(singletonList(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ExceptionDto(ex.getFieldErrors().stream()
                .filter(fieldError -> nonNull(fieldError.getDefaultMessage()))
                .map(fieldError ->
                        fieldError.getField().concat(BINDING_ERROR_JOINER).concat(fieldError.getDefaultMessage()))
                .collect(Collectors.toList()));
    }
}
