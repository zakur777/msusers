package globallogic.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomErrorResponseTest {
    @Test
    void shouldCreateEmptyCustomErrorResponse() {
        CustomErrorResponse response = new CustomErrorResponse();

        assertNotNull(response);
        assertNull(response.getDatetime());
        assertNull(response.getMessage());
        assertNull(response.getPath());
    }

    @Test
    void shouldCreateCustomErrorResponseWithAllArguments() {
        LocalDateTime now = LocalDateTime.now();
        String message = "Test error message";
        String path = "/test/path";

        CustomErrorResponse response = new CustomErrorResponse(now, message, path);

        assertNotNull(response);
        assertEquals(now, response.getDatetime());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void shouldAllowSettingAndGettingDatetime() {
        CustomErrorResponse response = new CustomErrorResponse();
        LocalDateTime now = LocalDateTime.now();

        response.setDatetime(now);

        assertEquals(now, response.getDatetime());
    }

    @Test
    void shouldAllowSettingAndGettingMessage() {
        CustomErrorResponse response = new CustomErrorResponse();
        String message = "New error message";

        response.setMessage(message);

        assertEquals(message, response.getMessage());
    }

    @Test
    void shouldAllowSettingAndGettingPath() {
        CustomErrorResponse response = new CustomErrorResponse();
        String path = "/new/test/path";

        response.setPath(path);

        assertEquals(path, response.getPath());
    }

}