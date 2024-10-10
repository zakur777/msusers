package globallogic.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalErrorHandlerTest {
    @InjectMocks
    private GlobalErrorHandler globalErrorHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    }

    @Test
    void shouldHandleAllExceptions() {
        Exception exception = new Exception("Test exception");
        ResponseEntity<Error> response = globalErrorHandler.handleAllExceptions(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("Test exception", response.getBody().getError().get(0).getDetail());
    }

    @Test
    void shouldHandleModelNotFoundException() {
        ModelNotFoundException exception = new ModelNotFoundException("Model not found");
        ResponseEntity<Error> response = globalErrorHandler.handleModelNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("Model not found", response.getBody().getError().get(0).getDetail());
    }

    @Test
    void shouldHandleUserAlreadyExistsException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");
        ResponseEntity<Error> response = globalErrorHandler.handleUserAlreadyExistsException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("User already exists", response.getBody().getError().get(0).getDetail());
    }

    @Test
    void shouldHandleSQLException() {
        SQLException exception = new SQLException("SQL error");
        ResponseEntity<Error> response = globalErrorHandler.handleSQLException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("SQL error", response.getBody().getError().get(0).getDetail());
    }

    @Test
    void shouldHandleServiceException() {
        ServiceException exception = new ServiceException("Service error");
        ResponseEntity<Error> response = globalErrorHandler.handleServiceException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("Service error", response.getBody().getError().get(0).getDetail());
    }

    @Test
    void shouldHandleFormatException() {
        FormatException exception = new FormatException("Format error");
        ResponseEntity<Error> response = globalErrorHandler.handleFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getError().size());
        assertEquals("Format error", response.getBody().getError().get(0).getDetail());
    }

//    @Test
//    void shouldHandleMethodArgumentNotValid() {
//        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
//        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(
//                new FieldError("objectName", "field1", "Error 1"),
//                new FieldError("objectName", "field2", "Error 2")
//        ));
//
//        ResponseEntity<Object> response = globalErrorHandler.handleMethodArgumentNotValid(
//                methodArgumentNotValidException, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody() instanceof Error);
//        Error error = (Error) response.getBody();
//        assertEquals(2, error.getError().size());
//        assertEquals("Error 1", error.getError().get(0).getDetail());
//        assertEquals("Error 2", error.getError().get(1).getDetail());
//    }
}