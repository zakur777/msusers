package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.dto.UserDTO;
import globallogic.model.Phone;
import globallogic.model.User;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T19:58:05-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    public User dtoToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDTO.getName() );
        user.setEmail( userDTO.getEmail() );
        user.setPassword( userDTO.getPassword() );
        user.setPhones( phoneDTOListToPhoneList( userDTO.getPhones() ) );
        user.setToken( userDTO.getToken() );

        return user;
    }

    @Override
    public UserDTO userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( uuidToString( user.getId() ) );
        userDTO.setPhones( phoneListToPhoneDTOList( user.getPhones() ) );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        if ( user.getCreated() != null ) {
            userDTO.setCreated( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( user.getCreated() ) );
        }
        if ( user.getLastLogin() != null ) {
            userDTO.setLastLogin( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( user.getLastLogin() ) );
        }
        userDTO.setToken( user.getToken() );
        userDTO.setActive( user.isActive() );

        return userDTO;
    }

    protected List<Phone> phoneDTOListToPhoneList(List<PhoneDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Phone> list1 = new ArrayList<Phone>( list.size() );
        for ( PhoneDTO phoneDTO : list ) {
            list1.add( phoneMapper.dtoToPhone( phoneDTO ) );
        }

        return list1;
    }

    protected List<PhoneDTO> phoneListToPhoneDTOList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneDTO> list1 = new ArrayList<PhoneDTO>( list.size() );
        for ( Phone phone : list ) {
            list1.add( phoneMapper.phoneToDto( phone ) );
        }

        return list1;
    }
}
