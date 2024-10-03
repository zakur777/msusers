package globallogic.service;

import globallogic.dto.UserDTO;
import globallogic.mapper.UserMapper;
import globallogic.model.Role;
import globallogic.model.User;
import globallogic.repository.UserRepository;
import globallogic.util.security.JwtTokenUtil;
import globallogic.util.security.JwtUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,12}$");

    public UserServiceImpl(UserRepository userRepository, JwtTokenUtil jwtUtil, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public User signUp(UserDTO userDTO) {
        validateUserData(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        User user = userMapper.dtoToUser(userDTO);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        //Asignar Rol
        Role role = new Role();
        role.setIdRole(1);
        role.setName("USER");
        role.setEnabled(true);
        user.setRole(role);

        userRepository.save(user);

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());


        user.setToken(jwtUtil.generateToken(userDetails));



        return user;
    }

    @Override
    public User login(String token) {
        String email = jwtUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        user.setLastLogin(LocalDateTime.now());
        //user.setToken(jwtUtil.generateToken(user.getEmail()));
        return userRepository.save(user);
    }

    private void validateUserData(UserDTO userDTO) {
        if (!EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
            throw new IllegalArgumentException("Formato de correo inválido");
        }
        if (!PASSWORD_PATTERN.matcher(userDTO.getPassword()).matches()) {
            throw new IllegalArgumentException("Formato de contraseña inválido");
        }
    }
}
