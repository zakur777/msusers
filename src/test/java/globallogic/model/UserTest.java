package globallogic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", "password123",
                List.of(new Phone(1L, 1234567890L, 1, "US")), LocalDateTime.now(),
                LocalDateTime.now(), "sampleToken", true, new Role(1,
                "USER", true));
    }

    @Test
    void testId() {
        UUID id = UUID.randomUUID();
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    void testName() {
        String name = "Jane Doe";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    void testEmail() {
        String email = "jane.doe@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testPassword() {
        String password = "newpassword123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void testPhones() {
        List<Phone> phones = List.of(new Phone(2L, 9876543210L, 44, "UK"));
        user.setPhones(phones);
        assertEquals(phones, user.getPhones());
    }

    @Test
    void testCreated() {
        LocalDateTime created = LocalDateTime.now();
        user.setCreated(created);
        assertEquals(created, user.getCreated());
    }

    @Test
    void testLastLogin() {
        LocalDateTime lastLogin = LocalDateTime.now();
        user.setLastLogin(lastLogin);
        assertEquals(lastLogin, user.getLastLogin());
    }

    @Test
    void testToken() {
        String token = "newSampleToken";
        user.setToken(token);
        assertEquals(token, user.getToken());
    }

    @Test
    void testIsActive() {
        boolean isActive = false;
        user.setActive(isActive);
        assertEquals(isActive, user.isActive());
    }

    @Test
    void testRole() {
        Role role = new Role(2, "ADMIN", true);
        user.setRole(role);
        assertEquals(role, user.getRole());
    }

}