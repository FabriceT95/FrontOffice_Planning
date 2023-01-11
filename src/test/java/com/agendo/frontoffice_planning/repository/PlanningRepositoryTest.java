package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class PlanningRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlanningRepository planningRepository;

    @MockBean
    StorageService storageService;

    @Test
    void given_PlanningTEST__when_findById__then_returnPlanning(){

        // given
        Planning Planning = new Planning("TEST");
        entityManager.persist(Planning);

        // when
        Planning result = planningRepository.findById(Planning.getIdPlanning()).get();

        // then
        assertThat(result).isEqualTo(Planning);

    }

    @Test
    void given_PlanningTEST__when_delete__then_findByIdReturnNull(){

        // given
        Planning Planning = new Planning("TEST");
        entityManager.persist(Planning);

        // when
        planningRepository.delete(Planning);
        Optional<Planning> result = planningRepository.findById(Planning.getIdPlanning());

        // then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void given_3PlanningTEST_when_findAll__then_Size3AndAllPlannings(){

        // given
        Planning Planning1 = new Planning("TEST1");
        Planning Planning2 = new Planning("TEST2");
        Planning Planning3 = new Planning("TEST3");
        entityManager.persist(Planning1);
        entityManager.persist(Planning2);
        entityManager.persist(Planning3);

        // when
        List<Planning> result = planningRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(Planning1);
        assertThat(result).contains(Planning2);
        assertThat(result).contains(Planning3);

    }

    @Test
    void given_3PlanningTEST_when_findAllById1And3__then_returnSize2AndPlanning1AndPlanning3(){

        // given
        Planning Planning1 = new Planning("TEST1");
        Planning Planning2 = new Planning("TEST2");
        Planning Planning3 = new Planning("TEST3");
        entityManager.persist(Planning1);
        entityManager.persist(Planning2);
        entityManager.persist(Planning3);
        List<Long> ids = new ArrayList<>();
        ids.add(Planning1.getIdPlanning());
        ids.add(Planning3.getIdPlanning());

        // when
        List<Planning> result = planningRepository.findAllById(ids);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(Planning1);
        assertThat(result).doesNotContain(Planning2);
        assertThat(result).contains(Planning3);

    }

}
