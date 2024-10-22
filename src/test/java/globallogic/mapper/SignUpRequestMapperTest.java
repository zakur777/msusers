package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SignUpRequestMapperTest {
    private final SignUpRequestMapper signUpRequestMapper = Mappers.getMapper(SignUpRequestMapper.class);

    @Test
    void shouldMapSignUpRequestDtoToUserDto() {
        SingUpRequestDTO signUpRequestDTO = new SingUpRequestDTO();
        signUpRequestDTO.setName("John Doe");
        signUpRequestDTO.setEmail("john@example.com");
        signUpRequestDTO.setPassword("Password123");
        signUpRequestDTO.setPhones(new ArrayList<>());

        UserDTO userDTO = signUpRequestMapper.toUserDTO(signUpRequestDTO);

        assertNotNull(userDTO);
        assertEquals(signUpRequestDTO.getName(), userDTO.getName());
        assertEquals(signUpRequestDTO.getEmail(), userDTO.getEmail());
        assertEquals(signUpRequestDTO.getPassword(), userDTO.getPassword());
        assertEquals(signUpRequestDTO.getPhones(), userDTO.getPhones());
    }

    @Test
    void shouldMapSignUpRequestDtoToUserDtoWithPhones() {
        SingUpRequestDTO signUpRequestDTO = new SingUpRequestDTO();
        signUpRequestDTO.setName("John Doe");
        signUpRequestDTO.setEmail("john@example.com");
        signUpRequestDTO.setPassword("Password123");
        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(new PhoneDTO(1L, 123456789L, 1, "123"));
        signUpRequestDTO.setPhones(phones);

        UserDTO userDTO = signUpRequestMapper.toUserDTO(signUpRequestDTO);

        assertNotNull(userDTO);
        assertEquals(signUpRequestDTO.getName(), userDTO.getName());
        assertEquals(signUpRequestDTO.getEmail(), userDTO.getEmail());
        assertEquals(signUpRequestDTO.getPassword(), userDTO.getPassword());
        assertEquals(signUpRequestDTO.getPhones(), userDTO.getPhones());
    }

    @Test
    void shouldMapSignUpRequestDtoToUserDtoWithNullPhones() {
        SingUpRequestDTO signUpRequestDTO = new SingUpRequestDTO();
        signUpRequestDTO.setName("John Doe");
        signUpRequestDTO.setEmail("john@example.com");
        signUpRequestDTO.setPassword("Password123");
        signUpRequestDTO.setPhones(null);

        UserDTO userDTO = signUpRequestMapper.toUserDTO(signUpRequestDTO);

        assertNotNull(userDTO);
        assertEquals(signUpRequestDTO.getName(), userDTO.getName());
        assertEquals(signUpRequestDTO.getEmail(), userDTO.getEmail());
        assertEquals(signUpRequestDTO.getPassword(), userDTO.getPassword());
        assertNull(userDTO.getPhones());
    }

    @Test
    void shouldMapUserDtoToSignUpRequestDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Password123");
        userDTO.setPhones(new ArrayList<>());

        SingUpRequestDTO signUpRequestDTO = signUpRequestMapper.toSignUpRequestDTO(userDTO);

        assertNotNull(signUpRequestDTO);
        assertEquals(userDTO.getName(), signUpRequestDTO.getName());
        assertEquals(userDTO.getEmail(), signUpRequestDTO.getEmail());
        assertEquals(userDTO.getPassword(), signUpRequestDTO.getPassword());
        assertEquals(userDTO.getPhones(), signUpRequestDTO.getPhones());
    }

    @Test
    void shouldMapUserDtoToSignUpRequestDtoWithPhones() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Password123");
        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(new PhoneDTO(2L, 123456789L, 1, "123"));
        userDTO.setPhones(phones);

        SingUpRequestDTO signUpRequestDTO = signUpRequestMapper.toSignUpRequestDTO(userDTO);

        assertNotNull(signUpRequestDTO);
        assertEquals(userDTO.getName(), signUpRequestDTO.getName());
        assertEquals(userDTO.getEmail(), signUpRequestDTO.getEmail());
        assertEquals(userDTO.getPassword(), signUpRequestDTO.getPassword());
        assertEquals(userDTO.getPhones(), signUpRequestDTO.getPhones());
    }

    @Test
    void shouldMapUserDtoToSignUpRequestDtoWithNullPhones() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Password123");
        userDTO.setPhones(null);

        SingUpRequestDTO signUpRequestDTO = signUpRequestMapper.toSignUpRequestDTO(userDTO);

        assertNotNull(signUpRequestDTO);
        assertEquals(userDTO.getName(), signUpRequestDTO.getName());
        assertEquals(userDTO.getEmail(), signUpRequestDTO.getEmail());
        assertEquals(userDTO.getPassword(), signUpRequestDTO.getPassword());
        assertNull(signUpRequestDTO.getPhones());
    }

    @Test
    void shouldReturnNullWhenMappingNullSignUpRequestDto() {
        UserDTO userDTO = signUpRequestMapper.toUserDTO(null);

        assertNull(userDTO);
    }

    @Test
    void shouldReturnNullWhenMappingNullUserDto() {
        SingUpRequestDTO signUpRequestDTO = signUpRequestMapper.toSignUpRequestDTO(null);

        assertNull(signUpRequestDTO);
    }
}