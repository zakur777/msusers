package globallogic.controller;

import globallogic.dto.LogInRequestDTO;
import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import globallogic.mapper.SignUpRequestMapper;
import globallogic.mapper.UserMapper;
import globallogic.model.User;
import globallogic.service.UserService;
import globallogic.util.security.JwtTokenUtil;
import globallogic.util.security.JwtUserDetailsService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final SignUpRequestMapper signUpRequestMapper;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          UserMapper userMapper,
                          SignUpRequestMapper signUpRequestMapper,
                          JwtUserDetailsService jwtUserDetailsService,
                          JwtTokenUtil jwtTokenUtil,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.signUpRequestMapper = signUpRequestMapper;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LogInRequestDTO requestDTO) throws Exception {
        String username = requestDTO.getUsername();
        String rawPassword = requestDTO.getPassword();
        String token = requestDTO.getToken();

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(token, userDetails)) {
            authenticate(username, rawPassword);
            User user = userService.login(token);
            UserDTO response = userMapper.userToDto(user);
            return ResponseEntity.ok(response);
        } else {
            throw new Exception("INVALID_TOKEN");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUpUser(@Valid @RequestBody SingUpRequestDTO request) {

        UserDTO userDTO = signUpRequestMapper.toUserDTO(request);
        User user = userService.signUp(userDTO);
        userDTO = userMapper.userToDto(user);

        return ResponseEntity.ok(userDTO);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
