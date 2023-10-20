package ua.kpi.ip22mp.trotsiuk.mmcmlr.handlers;

import org.apache.commons.math3.linear.SingularMatrixException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.ExceptionDto;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String BINDING_ERROR_JOINER = ": ";
    private static final String SINGULAR_MATRIX_EXCEPTION_MESSAGE_KEY =
            "independent.variables.singular.matrix.exception";

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(SingularMatrixException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handleSingularMatrixException() {
        return new ExceptionDto(singletonList(messageSource.getMessage(
                SINGULAR_MATRIX_EXCEPTION_MESSAGE_KEY, null, LocaleContextHolder.getLocale())), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ExceptionDto(singletonList(ex.getMessage()), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> globalErrors = ex.getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .filter(Objects::nonNull)
                .toList();

        List<String> fieldErrors = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .toList();

        return new ExceptionDto(globalErrors, fieldErrors);
    }
}
