package globallogic.service;

import globallogic.dto.UserDTO;
import globallogic.exception.ServiceException;
import globallogic.exception.UserAlreadyExistsException;
import globallogic.model.User;

public interface UserService {
    User signUp(UserDTO userDTO) throws ServiceException, UserAlreadyExistsException;
    User login(String token);
}
