package globallogic.mapper;

import globallogic.dto.UserDTO;
import globallogic.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapDtoToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Password123");

        User user = userMapper.dtoToUser(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertNull(user.getId());
        assertNull(user.getCreated());
        assertNull(user.getLastLogin());
        assertFalse(user.isActive());
    }

    @Test
    void shouldMapUserToDto() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("Password123");

        UserDTO userDTO = userMapper.userToDto(user);

        assertNotNull(userDTO);
        assertEquals(user.getId().toString(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    void shouldConvertUuidToString() {
        UUID uuid = UUID.randomUUID();
        String uuidString = userMapper.uuidToString(uuid);

        assertNotNull(uuidString);
        assertEquals(uuid.toString(), uuidString);
    }

    @Test
    void shouldReturnNullWhenUuidIsNull() {
        String uuidString = userMapper.uuidToString(null);

        assertNull(uuidString);
    }

    @Test
    void shouldReturnNullWhenMappingNullDto() {
        User user = userMapper.dtoToUser(null);

        assertNull(user);
    }

    @Test
    void shouldReturnNullWhenMappingNullUser() {
        UserDTO userDTO = userMapper.userToDto(null);

        assertNull(userDTO);
    }

    @Test
    void shouldMapEmptyPhoneList() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setPhones(null);

        // Act
        User user = userMapper.dtoToUser(userDTO);

        // Assert
        assertNotNull(user);
        assertNull(user.getPhones());
    }

    @Test
    void shouldHandleNullCreatedAndLastLogin() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setCreated(null);
        user.setLastLogin(null);

        // Act
        UserDTO userDTO = userMapper.userToDto(user);

        // Assert
        assertNotNull(userDTO);
        assertNull(userDTO.getCreated());
        assertNull(userDTO.getLastLogin());
    }
}