package me.renedo.johndeere.infraestrucure.http;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.renedo.johndeere.application.FetchTotalsUseCase;

@ControllerAdvice
public class AppExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(FetchTotalsUseCase.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse illegalArgumentException(IllegalArgumentException ex) {
        log.error("Bad request", ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse illegalArgumentException(HttpMessageNotReadableException ex) {
        log.error("Not request", ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse sqlException(SQLException ex) {
        //Cover the real error message
        log.error("Sql exception", ex);
        return new ErrorResponse("internal error");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse genericRuntimeException(RuntimeException ex) {
        //Cover the real error message
        log.error("Runtime exception", ex);
        return new ErrorResponse("internal error");
    }

}
