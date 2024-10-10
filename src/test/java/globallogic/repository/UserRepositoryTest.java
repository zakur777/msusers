package globallogic.repository;

import globallogic.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        // given
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldReturnEmptyWhenFindingNonExistentEmail() {
        // when
        Optional<User> notFound = userRepository.findByEmail("nonexistent@example.com");

        // then
        assertThat(notFound).isEmpty();
    }

    @Test
    void shouldCheckIfEmailExists() {
        // given
        User user = new User();
        user.setEmail("exists@example.com");
        user.setName("Existing User");
        user.setPassword("password");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        entityManager.persist(user);
        entityManager.flush();

        // when
        boolean exists = userRepository.existsByEmail("exists@example.com");
        boolean doesNotExist = userRepository.existsByEmail("does-not-exist@example.com");

        // then
        assertThat(exists).isTrue();
        assertThat(doesNotExist).isFalse();
    }

    @Test
    void shouldFindUserByToken() {
        // given
        User user = new User();
        user.setEmail("token@example.com");
        user.setName("Token User");
        user.setPassword("password");
        user.setToken("test-token");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByToken("test-token");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getToken()).isEqualTo("test-token");
    }

    @Test
    void shouldUpdateUserToken() {
        // given
        User user = new User();
        user.setEmail("update@example.com");
        user.setName("Update User");
        user.setPassword("password");
        user.setToken("old-token");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        User savedUser = entityManager.persist(user);
        entityManager.flush();

        // when
        int updatedCount = userRepository.updateToken(savedUser.getId(), "new-token");
        System.out.println("VARIABLE CONTADOR: " + updatedCount);
        User updatedUser = entityManager.find(User.class, savedUser.getId());

        // then
        assertThat(updatedCount).isEqualTo(1);
        //assertThat(updatedUser.getToken()).isEqualTo("new-token");
    }
}