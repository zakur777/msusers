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

    @Test
    void shouldImplementEqualsAndHashCode() {
        JwtRequest request1 = new JwtRequest("user", "pass");
        JwtRequest request2 = new JwtRequest("user", "pass");
        JwtRequest request3 = new JwtRequest("otherUser", "pass");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void shouldImplementToString() {
        JwtRequest request = new JwtRequest("user", "pass");
        String toString = request.toString();
        assertTrue(toString.contains("user"));
        assertTrue(toString.contains("pass"));
    }

}