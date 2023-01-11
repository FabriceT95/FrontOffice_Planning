package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.*;
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
public class ShareRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    StorageService storageService;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        entityManager.persist(new Role("BASIC"));
        entityManager.persist(new Role("ADMIN"));
        entityManager.persist(new Role("SUPERADMIN"));
    }

    @Test
    void when_addNewShare_then_returnShare() {

        Users user1 = new Users();
        user1.setActivated(true);
        user1.setEmail("test@test.fr");
        user1.setPassword("random_password");
        user1.setPhoto("/upload-dir/picture.jpg");
        user1.setUsername("Test");
        user1.setAddress(new Address("Paris", "75000"));
        user1.addRole(roleRepository.findRoleByName("BASIC").get());
        user1.setPlanning(new Planning("Test's planning", LocalDateTime.now()));
        user1 = entityManager.persist(user1);

        Users user2 = new Users();
        user2.setActivated(true);
        user2.setEmail("test2@test2.fr");
        user2.setPassword("random2_password2");
        user2.setPhoto("/upload-dir/picture2.jpg");
        user2.setUsername("Test2");
        user2.setAddress(new Address("Paris2", "75002"));
        user2.addRole(roleRepository.findRoleByName("BASIC").get());
        user2.setPlanning(new Planning("Test2's planning", LocalDateTime.now()));
        user2 = entityManager.persist(user2);


        // when
        user2.addShare(new Share(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()), user1.getPlanning(), user2, true));
        user2 = entityManager.persist(user2);

        Optional<Share> result = shareRepository.findById(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()));

        // then
        assertThat(result.get()).isEqualTo(user2.getShare().iterator().next());
    }

    @Test
    void when_findAllByUser_then_returnShare() {

        Users user1 = new Users();
        user1.setActivated(true);
        user1.setEmail("test@test.fr");
        user1.setPassword("random_password");
        user1.setPhoto("/upload-dir/picture.jpg");
        user1.setUsername("Test");
        user1.setAddress(new Address("Paris", "75000"));
        user1.addRole(roleRepository.findRoleByName("BASIC").get());
        user1.setPlanning(new Planning("Test's planning", LocalDateTime.now()));
        user1 = entityManager.persist(user1);

        Users user2 = new Users();
        user2.setActivated(true);
        user2.setEmail("test2@test2.fr");
        user2.setPassword("random2_password2");
        user2.setPhoto("/upload-dir/picture2.jpg");
        user2.setUsername("Test2");
        user2.setAddress(new Address("Paris2", "75002"));
        user2.addRole(roleRepository.findRoleByName("BASIC").get());
        user2.setPlanning(new Planning("Test2's planning", LocalDateTime.now()));
        user2 = entityManager.persist(user2);

        Users user3 = new Users();
        user3.setActivated(true);
        user3.setEmail("test3@test3.fr");
        user3.setPassword("random3_password3");
        user3.setPhoto("/upload-dir/picture2.jpg");
        user3.setUsername("Test3");
        user3.setAddress(new Address("Paris3", "75003"));
        user3.addRole(roleRepository.findRoleByName("BASIC").get());
        user3.setPlanning(new Planning("Test3's planning", LocalDateTime.now()));
        user3 = entityManager.persist(user3);


        // when
        user2.addShare(new Share(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()), user1.getPlanning(), user2, true));
        user2.addShare(new Share(new ShareId(user2.getIdUser(), user3.getPlanning().getIdPlanning()), user3.getPlanning(), user3, true));
        user2 = entityManager.persist(user2);

        Optional<Share> result = shareRepository.findById(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()));

        // then
        assertThat(result.get()).isEqualTo(user2.getShare().iterator().next());
    }


    @Test
    void when_deleteShare_then_return() {

        Users user1 = new Users();
        user1.setActivated(true);
        user1.setEmail("test@test.fr");
        user1.setPassword("random_password");
        user1.setPhoto("/upload-dir/picture.jpg");
        user1.setUsername("Test");
        user1.setAddress(new Address("Paris", "75000"));
        user1.addRole(roleRepository.findRoleByName("BASIC").get());
        user1.setPlanning(new Planning("Test's planning", LocalDateTime.now()));
        user1 = entityManager.persist(user1);

        Users user2 = new Users();
        user2.setActivated(true);
        user2.setEmail("test2@test2.fr");
        user2.setPassword("random2_password2");
        user2.setPhoto("/upload-dir/picture2.jpg");
        user2.setUsername("Test2");
        user2.setAddress(new Address("Paris2", "75002"));
        user2.addRole(roleRepository.findRoleByName("BASIC").get());
        user2.setPlanning(new Planning("Test2's planning", LocalDateTime.now()));
        user2 = entityManager.persist(user2);


        // when
        user2.addShare(new Share(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()), user1.getPlanning(), user2, true));
        user2 = entityManager.persist(user2);

        shareRepository.delete(user2.getShare().iterator().next());

        Optional<Share> result = shareRepository.findById(new ShareId(user2.getIdUser(), user1.getPlanning().getIdPlanning()));

        // then
        assertThat(result).isEqualTo(Optional.empty());
    }
}
