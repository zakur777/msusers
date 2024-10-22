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
}