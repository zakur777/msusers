package globallogic.service.impl;

import globallogic.dto.UserDTO;
import globallogic.exception.*;
import globallogic.mapper.UserMapper;
import globallogic.model.Phone;
import globallogic.model.Role;
import globallogic.model.User;
import globallogic.repository.PhoneRepository;
import globallogic.repository.RoleRepository;
import globallogic.repository.UserRepository;
import globallogic.service.UserService;
import globallogic.util.security.JwtTokenUtil;
import globallogic.util.security.JwtUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,12}$");

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PhoneRepository phoneRepository,
                           JwtTokenUtil jwtUtil, PasswordEncoder passwordEncoder, UserMapper userMapper,
                           JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.phoneRepository = phoneRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public User signUp(UserDTO userDTO) throws ServiceException, UserAlreadyExistsException {
        validateUserData(userDTO);
        checkIfUserExists(userDTO.getEmail());

        try {
            User user = createUserFromDTO(userDTO);
            assignRoleToUser(user);
            saveUserPhones(user);
            userRepository.save(user);
            generateAndSetUserToken(user);
            return user;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User login(String token) throws ServiceException, ModelNotFoundException {
        try {
            String email = jwtUtil.getUsernameFromToken(token);
            User user = findUserByEmail(email);
            return updateUserLoginInfo(user);
        } catch (ModelNotFoundException e) {
            throw new ModelNotFoundException(e.getMessage());
        } catch (UpdateUserLoginException e) {
            throw new UpdateUserLoginException(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private User createUserFromDTO(UserDTO userDTO) {
        User user = userMapper.dtoToUser(userDTO);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        return user;
    }

    private void assignRoleToUser(User user) {
        Role role = new Role();
        role.setName("USER");
        role.setEnabled(true);
        role = roleRepository.save(role);
        user.setRole(role);
    }

    private void saveUserPhones(User user) {
        List<Phone> phones = user.getPhones();
        if (phones != null) {
            phoneRepository.saveAll(phones);
        }
    }

    private void generateAndSetUserToken(User user) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        user.setToken(token);
        userRepository.updateToken(user.getId(), token);
    }

    private User findUserByEmail(String email) throws ModelNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
    }

    private User updateUserLoginInfo(User user) throws ServiceException {
        try {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());
            String newToken = jwtUtil.generateToken(userDetails);
            user.setLastLogin(LocalDateTime.now());
            user.setToken(newToken);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UpdateUserLoginException(e.getMessage());
        }
    }

    private void validateUserData(UserDTO userDTO) {
        if (!EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
            throw new FormatException("Formato de correo inválido");
        }
        if (!PASSWORD_PATTERN.matcher(userDTO.getPassword()).matches()) {
            throw new FormatException("Formato de contraseña inválido");
        }
    }

    private void checkIfUserExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + email);
        }
    }
}
