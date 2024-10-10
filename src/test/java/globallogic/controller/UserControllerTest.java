package globallogic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import globallogic.dto.LogInRequestDTO;
import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import globallogic.mapper.SignUpRequestMapper;
import globallogic.mapper.UserMapper;
import globallogic.model.User;
import globallogic.service.UserService;
import globallogic.util.security.JwtTokenUtil;
import globallogic.util.security.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private SignUpRequestMapper signUpRequestMapper;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;


    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() throws NoSuchMethodException {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldLoginUserSuccessfully() throws Exception {
        LogInRequestDTO loginRequest = new LogInRequestDTO();
        loginRequest.setUsername("user@example.com");
        loginRequest.setPassword("password");
        loginRequest.setToken("valid-token");

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("user@example.com")
                .password("password")
                .authorities("USER")
                .build();

        User user = new User();
        user.setEmail("user@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user@example.com");

        when(jwtUserDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken("valid-token", userDetails)).thenReturn(true);
        when(userService.login("valid-token")).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userDTO);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenLoginWithInvalidToken() throws Exception {
        LogInRequestDTO loginRequest = new LogInRequestDTO();
        loginRequest.setUsername("user@example.com");
        loginRequest.setPassword("password");
        loginRequest.setToken("invalid-token");

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("user@example.com")
                .password("password")
                .authorities("USER")
                .build();

        when(jwtUserDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken("invalid-token", userDetails)).thenReturn(false);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldSignUpUserSuccessfully() throws Exception {
        SingUpRequestDTO signUpRequest = new SingUpRequestDTO();
        signUpRequest.setName("John Doe");
        signUpRequest.setEmail("john@example.com");
        signUpRequest.setPassword("Admin1234");

        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("Admin1234");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("Admin1234");

        when(signUpRequestMapper.toUserDTO(any(SingUpRequestDTO.class))).thenReturn(userDTO);
        when(userService.signUp(any(UserDTO.class))).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userDTO);

        mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

}