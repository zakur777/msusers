package globallogic.repository;

import globallogic.model.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PhoneRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    void shouldSavePhone() {
        // given
        Phone phone = new Phone();
        phone.setNumber(1234567890L);
        phone.setCityCode(1);
        phone.setCountryCode("1");

        // when
        Phone savedPhone = phoneRepository.save(phone);

        // then
        assertThat(savedPhone).isNotNull();
        assertThat(savedPhone.getId()).isNotNull();
        assertThat(savedPhone.getNumber()).isEqualTo(1234567890L);
    }

    @Test
    void shouldFindPhoneById() {
        // given
        Phone phone = new Phone();
        phone.setNumber(9876543210L);
        phone.setCityCode(2);
        phone.setCountryCode("1");
        entityManager.persist(phone);
        entityManager.flush();

        // when
        Optional<Phone> found = phoneRepository.findById(phone.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNumber()).isEqualTo(phone.getNumber());
    }

    @Test
    void shouldFindAllPhones() {
        // given
        Phone phone1 = new Phone();
        phone1.setNumber(1111111111L);
        phone1.setCityCode(1);
        phone1.setCountryCode("1");
        entityManager.persist(phone1);

        Phone phone2 = new Phone();
        phone2.setNumber(2222222222L);
        phone2.setCityCode(2);
        phone2.setCountryCode("1");
        entityManager.persist(phone2);

        entityManager.flush();

        // when
        List<Phone> phones = phoneRepository.findAll();

        // then
        assertThat(phones).hasSize(2);
        assertThat(phones).extracting(Phone::getNumber).containsExactlyInAnyOrder(1111111111L, 2222222222L);
    }

    @Test
    void shouldUpdatePhone() {
        // given
        Phone phone = new Phone();
        phone.setNumber(3333333333L);
        phone.setCityCode(3);
        phone.setCountryCode("1");
        entityManager.persist(phone);
        entityManager.flush();

        // when
        phone.setNumber(4444444444L);
        Phone updatedPhone = phoneRepository.save(phone);

        // then
        assertThat(updatedPhone.getNumber()).isEqualTo(4444444444L);
    }

    @Test
    void shouldDeletePhone() {
        // given
        Phone phone = new Phone();
        phone.setNumber(5555555555L);
        phone.setCityCode(5);
        phone.setCountryCode("1");
        entityManager.persist(phone);
        entityManager.flush();

        // when
        phoneRepository.delete(phone);
        Optional<Phone> deletedPhone = phoneRepository.findById(phone.getId());

        // then
        assertThat(deletedPhone).isEmpty();
    }

}