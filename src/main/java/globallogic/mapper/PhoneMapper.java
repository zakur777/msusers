package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.model.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    @Mapping(target = "id", ignore = true)
    Phone dtoToPhone(PhoneDTO phoneDTO);

    PhoneDTO phoneToDto(Phone phone);
}
