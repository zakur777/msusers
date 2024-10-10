package globallogic.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogInRequestDTOTest {
    @Test
    void shouldCreateEmptyLogInRequestDTO() {
        LogInRequestDTO dto = new LogInRequestDTO();
        assertNotNull(dto);
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
        assertNull(dto.getToken());
    }

    @Test
    void shouldCreateLogInRequestDTOWithAllArguments() {
        LogInRequestDTO dto = new LogInRequestDTO("testUser", "testPass", "testToken");
        assertNotNull(dto);
        assertEquals("testUser", dto.getUsername());
        assertEquals("testPass", dto.getPassword());
        assertEquals("testToken", dto.getToken());
    }

    @Test
    void shouldSetAndGetUsername() {
        LogInRequestDTO dto = new LogInRequestDTO();
        dto.setUsername("newUser");
        assertEquals("newUser", dto.getUsername());
    }

    @Test
    void shouldSetAndGetPassword() {
        LogInRequestDTO dto = new LogInRequestDTO();
        dto.setPassword("newPass");
        assertEquals("newPass", dto.getPassword());
    }

    @Test
    void shouldSetAndGetToken() {
        LogInRequestDTO dto = new LogInRequestDTO();
        dto.setToken("newToken");
        assertEquals("newToken", dto.getToken());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        LogInRequestDTO dto1 = new LogInRequestDTO("user", "pass", "token");
        LogInRequestDTO dto2 = new LogInRequestDTO("user", "pass", "token");
        LogInRequestDTO dto3 = new LogInRequestDTO("otherUser", "pass", "token");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void shouldImplementToString() {
        LogInRequestDTO dto = new LogInRequestDTO("user", "pass", "token");
        String toString = dto.toString();
        assertTrue(toString.contains("user"));
        assertTrue(toString.contains("pass"));
        assertTrue(toString.contains("token"));
    }

}