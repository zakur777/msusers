package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.dto.SingUpRequestDTO;
import globallogic.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T19:58:05-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class SignUpRequestMapperImpl implements SignUpRequestMapper {

    @Override
    public UserDTO toUserDTO(SingUpRequestDTO signUpRequestDTO) {
        if ( signUpRequestDTO == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setName( signUpRequestDTO.getName() );
        userDTO.setEmail( signUpRequestDTO.getEmail() );
        userDTO.setPassword( signUpRequestDTO.getPassword() );
        List<PhoneDTO> list = signUpRequestDTO.getPhones();
        if ( list != null ) {
            userDTO.setPhones( new ArrayList<PhoneDTO>( list ) );
        }

        return userDTO;
    }

    @Override
    public SingUpRequestDTO toSignUpRequestDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        SingUpRequestDTO singUpRequestDTO = new SingUpRequestDTO();

        singUpRequestDTO.setName( userDTO.getName() );
        singUpRequestDTO.setEmail( userDTO.getEmail() );
        singUpRequestDTO.setPassword( userDTO.getPassword() );
        List<PhoneDTO> list = userDTO.getPhones();
        if ( list != null ) {
            singUpRequestDTO.setPhones( new ArrayList<PhoneDTO>( list ) );
        }

        return singUpRequestDTO;
    }


}
