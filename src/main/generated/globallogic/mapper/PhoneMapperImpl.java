package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.model.Phone;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T19:58:06-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class PhoneMapperImpl implements PhoneMapper {

    @Override
    public Phone dtoToPhone(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setNumber( phoneDTO.getNumber() );
        phone.setCityCode( phoneDTO.getCityCode() );
        phone.setCountryCode( phoneDTO.getCountryCode() );

        return phone;
    }

    @Override
    public PhoneDTO phoneToDto(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();

        phoneDTO.setId( phone.getId() );
        phoneDTO.setNumber( phone.getNumber() );
        phoneDTO.setCityCode( phone.getCityCode() );
        phoneDTO.setCountryCode( phone.getCountryCode() );

        return phoneDTO;
    }
}
