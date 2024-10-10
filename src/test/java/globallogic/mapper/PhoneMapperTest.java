package globallogic.mapper;

import globallogic.dto.PhoneDTO;
import globallogic.model.Phone;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PhoneMapperTest {
    private final PhoneMapper phoneMapper = Mappers.getMapper(PhoneMapper.class);

    @Test
    void shouldMapDtoToPhone() {
        // Given
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(123456789L);
        phoneDTO.setCityCode(1);
        phoneDTO.setCountryCode("54");

        // When
        Phone phone = phoneMapper.dtoToPhone(phoneDTO);

        // Then
        assertNotNull(phone);
        assertNull(phone.getId());
        assertEquals(phoneDTO.getNumber(), phone.getNumber());
        assertEquals(phoneDTO.getCityCode(), phone.getCityCode());
        assertEquals(phoneDTO.getCountryCode(), phone.getCountryCode());
    }

    @Test
    void shouldMapPhoneToDto() {
        // Given
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setNumber(987654321L);
        phone.setCityCode(2);
        phone.setCountryCode("55");

        // When
        PhoneDTO phoneDTO = phoneMapper.phoneToDto(phone);

        // Then
        assertNotNull(phoneDTO);
        assertEquals(phone.getId(), phoneDTO.getId());
        assertEquals(phone.getNumber(), phoneDTO.getNumber());
        assertEquals(phone.getCityCode(), phoneDTO.getCityCode());
        assertEquals(phone.getCountryCode(), phoneDTO.getCountryCode());
    }

    @Test
    void shouldIgnoreIdWhenMappingDtoToPhone() {
        // Given
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setId(1L);
        phoneDTO.setNumber(123456789L);

        // When
        Phone phone = phoneMapper.dtoToPhone(phoneDTO);

        // Then
        assertNotNull(phone);
        assertNull(phone.getId());
        assertEquals(phoneDTO.getNumber(), phone.getNumber());
    }

    @Test
    void shouldReturnNullWhenMappingNullDto() {
        // Given
        PhoneDTO phoneDTO = null;

        // When
        Phone phone = phoneMapper.dtoToPhone(phoneDTO);

        // Then
        assertNull(phone);
    }

    @Test
    void shouldReturnNullWhenMappingNullPhone() {
        // Given
        Phone phone = null;

        // When
        PhoneDTO phoneDTO = phoneMapper.phoneToDto(phone);

        // Then
        assertNull(phoneDTO);
    }
}