package globallogic.service;

import globallogic.dto.UserDTO;
import globallogic.model.User;

public interface UserService {
    User signUp(UserDTO userDTO);
    User login(String token);
}
