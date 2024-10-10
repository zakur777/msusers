package globallogic.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatExceptionTest {
    @Test
    void shouldCreateFormatExceptionWithGivenMessage() {
        String errorMessage = "Invalid format";
        FormatException exception = new FormatException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldInheritFromRuntimeException() {
        FormatException exception = new FormatException("Test");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldAllowEmptyMessage() {
        FormatException exception = new FormatException("");

        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void shouldHandleNullMessage() {
        FormatException exception = new FormatException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

}