package globallogic.util.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    @Test
    void shouldCreateJwtResponseWithToken() {
        String token = "testToken";
        JwtResponse response = new JwtResponse(token);
        assertNotNull(response);
        assertEquals(token, response.getJwtToken());
    }

}