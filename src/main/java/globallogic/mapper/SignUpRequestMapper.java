package globallogic.mapper;

import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SignUpRequestMapper {
    SignUpRequestMapper INSTANCE = Mappers.getMapper(SignUpRequestMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phones", target = "phones")
    UserDTO toUserDTO(SingUpRequestDTO signUpRequestDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phones", target = "phones")
    SingUpRequestDTO toSignUpRequestDTO(UserDTO userDTO);
}
