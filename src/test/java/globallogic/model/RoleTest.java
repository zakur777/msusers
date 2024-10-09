package globallogic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(1, "USER", true);
    }

    @Test
    void testId() {
        int id = 2;
        role.setIdRole(id);
        assertEquals(id, role.getIdRole());
    }

    @Test
    void testName() {
        String name = "ADMIN";
        role.setName(name);
        assertEquals(name, role.getName());
    }

    @Test
    void testEnabled() {
        boolean enabled = false;
        role.setEnabled(enabled);
        assertEquals(enabled, role.isEnabled());
    }
}