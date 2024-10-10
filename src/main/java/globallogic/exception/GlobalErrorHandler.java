package globallogic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private Error createError() {
        Error error = new Error();
        if (error.getError() == null) {
            error.setError(new ArrayList<>());
        }
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAllExceptions(Exception ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<Error> handleModelNotFoundException(ModelNotFoundException ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Error> handleSQLException(SQLException ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handleServiceException(ServiceException ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormatException.class)
    public ResponseEntity<Error> handleFormatException(FormatException ex) {
        Error error = createError();
        error.getError().add(new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ErrorInfo> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        Error error = createError();
        error.setError(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
