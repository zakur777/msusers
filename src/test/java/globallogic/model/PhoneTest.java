package globallogic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    private Phone phone;

    @BeforeEach
    void setUp() {
        phone = new Phone(1L, 1234567890L, 1, "US");
    }

    @Test
    void testId() {
        Long id = 2L;
        phone.setId(id);
        assertEquals(id, phone.getId());
    }

    @Test
    void testNumber() {
        Long number = 9876543210L;
        phone.setNumber(number);
        assertEquals(number, phone.getNumber());
    }

    @Test
    void testCityCode() {
        int cityCode = 44;
        phone.setCityCode(cityCode);
        assertEquals(cityCode, phone.getCityCode());
    }

    @Test
    void testCountryCode() {
        String countryCode = "UK";
        phone.setCountryCode(countryCode);
        assertEquals(countryCode, phone.getCountryCode());
    }
}