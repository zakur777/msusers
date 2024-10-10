package globallogic.repository;

import globallogic.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldSaveRole() {
        // given
        Role role = new Role();
        role.setName("ADMIN");
        role.setEnabled(true);

        // when
        Role savedRole = roleRepository.save(role);

        // then
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getIdRole()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ADMIN");
    }

    @Test
    void shouldFindRoleById() {
        // given
        Role role = new Role();
        role.setName("USER");
        role.setEnabled(true);
        entityManager.persist(role);
        entityManager.flush();

        // when
        Optional<Role> found = roleRepository.findById(role.getIdRole());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(role.getName());
    }

    @Test
    void shouldFindAllRoles() {
        // given
        Role role1 = new Role();
        role1.setName("ADMIN");
        role1.setEnabled(true);
        entityManager.persist(role1);

        Role role2 = new Role();
        role2.setName("USER");
        role2.setEnabled(true);
        entityManager.persist(role2);

        entityManager.flush();

        // when
        List<Role> roles = roleRepository.findAll();

        // then
        assertThat(roles).hasSize(2);
        assertThat(roles).extracting(Role::getName).containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    void shouldUpdateRole() {
        // given
        Role role = new Role();
        role.setName("MODERATOR");
        role.setEnabled(true);
        entityManager.persist(role);
        entityManager.flush();

        // when
        role.setName("SUPER_MODERATOR");
        Role updatedRole = roleRepository.save(role);

        // then
        assertThat(updatedRole.getName()).isEqualTo("SUPER_MODERATOR");
    }

    @Test
    void shouldDeleteRole() {
        // given
        Role role = new Role();
        role.setName("GUEST");
        role.setEnabled(true);
        entityManager.persist(role);
        entityManager.flush();

        // when
        roleRepository.delete(role);
        Optional<Role> deletedRole = roleRepository.findById(role.getIdRole());

        // then
        assertThat(deletedRole).isEmpty();
    }

}