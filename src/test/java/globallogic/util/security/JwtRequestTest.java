package globallogic.util.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtRequestTest {
    @Test
    void shouldCreateEmptyJwtRequest() {
        JwtRequest request = new JwtRequest();
        assertNotNull(request);
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }

    @Test
    void shouldCreateJwtRequestWithAllArguments() {
        JwtRequest request = new JwtRequest("testUser", "testPass");
        assertNotNull(request);
        assertEquals("testUser", request.getUsername());
        assertEquals("testPass", request.getPassword());
    }

    @Test
    void shouldSetAndGetUsername() {
        JwtRequest request = new JwtRequest();
        request.setUsername("newUser");
        assertEquals("newUser", request.getUsername());
    }

    @Test
    void shouldSetAndGetPassword() {
        JwtRequest request = new JwtRequest();
        request.setPassword("newPass");
        assertEquals("newPass", request.getPassword());
    }


}