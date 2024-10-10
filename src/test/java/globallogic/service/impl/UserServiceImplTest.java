package globallogic.service.impl;

import globallogic.dto.PhoneDTO;
import globallogic.dto.UserDTO;
import globallogic.exception.*;
import globallogic.mapper.UserMapper;
import globallogic.model.Phone;
import globallogic.model.Role;
import globallogic.model.User;
import globallogic.repository.PhoneRepository;
import globallogic.repository.RoleRepository;
import globallogic.repository.UserRepository;
import globallogic.util.security.JwtTokenUtil;
import globallogic.util.security.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenUtil jwtUtil;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Password123");
        userDTO.setPhones(new ArrayList<>());

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setPhones(new ArrayList<>());
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
    }

    @Test
    void shouldSignUpUserSuccessfully() throws ServiceException, UserAlreadyExistsException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.dtoToUser(any(UserDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUserDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("token");

        User result = userService.signUp(userDTO);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository).save(any(User.class));
        verify(userRepository).updateToken(any(UUID.class), anyString());
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenSigningUpExistingUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.signUp(userDTO));
    }

    @Test
    void shouldThrowFormatExceptionWhenEmailIsInvalid() {
        userDTO.setEmail("invalid-email");

        assertThrows(FormatException.class, () -> userService.signUp(userDTO));
    }

    @Test
    void shouldThrowFormatExceptionWhenPasswordIsInvalid() {
        userDTO.setPassword("weak");

        assertThrows(FormatException.class, () -> userService.signUp(userDTO));
    }

    @Test
    void shouldLoginUserSuccessfully() throws ServiceException, ModelNotFoundException {
        when(jwtUtil.getUsernameFromToken(anyString())).thenReturn("john@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUserDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("newToken");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.login("token");

        assertNotNull(result);
        assertEquals("newToken", result.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowModelNotFoundExceptionWhenUserNotFoundDuringLogin() {
        when(jwtUtil.getUsernameFromToken(anyString())).thenReturn("john@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> userService.login("token"));
    }

    @Test
    void shouldThrowServiceExceptionWhenErrorOccursDuringLogin() {
        when(jwtUtil.getUsernameFromToken(anyString())).thenThrow(new RuntimeException("Token error"));

        assertThrows(ServiceException.class, () -> userService.login("token"));
    }

    @Test
    void shouldThrowUpdateUserLoginExceptionWhenLoginUser() throws ServiceException, ModelNotFoundException {
        when(jwtUtil.getUsernameFromToken(anyString())).thenReturn("john@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUserDetailsService.loadUserByUsername(anyString()))
                .thenThrow(new UpdateUserLoginException("Update Error"));

        assertThrows(UpdateUserLoginException.class, () -> userService.login("token"));

    }

    @Test
    void shouldHandleExceptionInSignUp() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.dtoToUser(any(UserDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(User.class));

        assertThrows(ServiceException.class, () -> userService.signUp(userDTO));
    }

    @Test
    void shouldNotSavePhonesWhenPhonesIsNull() {
        userDTO.setPhones(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.dtoToUser(any(UserDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUserDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("token");

        User result = userService.signUp(userDTO);

        assertNotNull(result);
        verify(phoneRepository, never()).saveAll(anyList());
    }

    @Test
    void shouldSavePhonesWhenPhonesIsNotNull() {
        user.setPhones(Arrays.asList(new Phone(1L,12345678L, 1, "56"), new Phone(2L,12345679L, 1, "56")));
        userDTO.setPhones(Arrays.asList(new PhoneDTO(1L,12345678L, 1, "56"), new PhoneDTO(2L,12345679L, 1, "56")));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.dtoToUser(any(UserDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUserDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("token");

        User result = userService.signUp(userDTO);

        assertNotNull(result);
        verify(phoneRepository, times(1)).saveAll(anyList());
    }


}