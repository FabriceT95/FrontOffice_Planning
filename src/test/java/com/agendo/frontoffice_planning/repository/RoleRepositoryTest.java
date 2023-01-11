package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Role;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    StorageService storageService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void when_findById_then_returnRole() {
        // given
        Role role = new Role("BASIC");
        role = entityManager.persist(role);

        // when
        Optional<Role> result = roleRepository.findById(1L);

        // then
        assertThat(result.get()).isEqualTo(role);
    }

    @Test
    void when_findByName_then_returnRole() {
        // given
        Role role = new Role("BASIC");
        role = entityManager.persist(role);

        // when
        Optional<Role> result = roleRepository.findRoleByName("BASIC");

        // then
        assertThat(result.get()).isEqualTo(role);
    }
}
