package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Address;
import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Role;
import com.agendo.frontoffice_planning.entity.Users;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @MockBean
    StorageService storageService;

    @BeforeEach
    public void setup() {
        entityManager.persist(new Role("BASIC"));
        entityManager.persist(new Role("ADMIN"));
        entityManager.persist(new Role("SUPERADMIN"));
    }

    @Test
    void when_findByEmail_then_returnUser() {

        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");
        users.setAddress(new Address("Paris", "75000"));
        users.addRole(roleRepository.findRoleByName("BASIC").get());
        users.setPlanning(new Planning("Fabrice's planning", LocalDateTime.now()));
        entityManager.persist(users);

        // when
        Optional<Users> result = userRepository.findByEmail("test@test.fr");

        // then
        assertThat(result.get()).isEqualTo(users);
    }

    @Test
    void when_findByUser_then_returnUser() {

        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");
        users.setAddress(new Address("Paris", "75000"));
        users.addRole(roleRepository.findRoleByName("BASIC").get());
        users.setPlanning(new Planning("Fabrice's planning", LocalDateTime.now()));
        entityManager.persist(users);

        // when
        Optional<Users> result = userRepository.findByUsername("Test");

        // then
        assertThat(result.get()).isEqualTo(users);
    }

    @Test
    void when_existsUsersByEmailAndPlanning_then__returnTrue() {

        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");
        users.setAddress(new Address("Paris", "75000"));
        users.addRole(roleRepository.findRoleByName("BASIC").get());
        users.setPlanning(new Planning("Fabrice's planning", LocalDateTime.now()));
        entityManager.persist(users);

        // when
        boolean result = userRepository.existsUsersByEmailAndPlanning(users.getEmail(), users.getPlanning());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void when_findByIdNotExist_then__returnEmpty() {

        // when
        Optional<Users> result = userRepository.findById(5L);

        // then
        assertThat(result).isEqualTo(Optional.empty());
    }
}
