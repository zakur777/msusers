package globallogic.controller;

import globallogic.dto.SignUpResponseDTO;
import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import globallogic.mapper.SignUpRequestMapper;
import globallogic.mapper.UserMapper;
import globallogic.model.User;
import globallogic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SignUpRequestMapper signUpRequestMapper;

 //   @PostMapping("/login")
//    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody UserDTO userDTO) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtil.generateToken(userDTO.getEmail());
//
//        User user = userService.login(jwt);
//        UserDTO response = userMapper.userToDto(user);
//        return ResponseEntity.ok(response);
 //   }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUpUser(@RequestBody SingUpRequestDTO request) {

        UserDTO userDTO = signUpRequestMapper.toUserDTO(request);
        User user = userService.signUp(userDTO);
        userDTO = userMapper.userToDto(user);

        return ResponseEntity.ok(userDTO);
    }

}
