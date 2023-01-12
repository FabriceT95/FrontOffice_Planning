package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Action;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class ActionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ActionRepository actionRepository;

    @MockBean
    StorageService storageService;

    @Test
    void given_actionTEST__when_findById__then_returnAction(){

        // given
        Action action = new Action("TEST");
        entityManager.persist(action);

        // when
        Action result = actionRepository.findById(action.getIdAction()).get();

        // then
        assertThat(result).isEqualTo(action);

    }

    @Test
    void given_actionTEST_when_delete__then_findByIdReturnNull(){

        // given
        Action action = new Action("TEST");
        entityManager.persist(action);

        // when
        actionRepository.delete(action);
        Optional<Action> result = actionRepository.findById(action.getIdAction());

        // then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void given_3actionTEST_when_findAll__then_Size3AndAllActions(){

        // given
        Action action1 = new Action("TEST1");
        Action action2 = new Action("TEST2");
        Action action3 = new Action("TEST3");
        entityManager.persist(action1);
        entityManager.persist(action2);
        entityManager.persist(action3);

        // when
        List<Action> result = actionRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(action1);
        assertThat(result).contains(action2);
        assertThat(result).contains(action3);

    }

    @Test
    void given_3actionTEST_when_findAllById1And3__then_returnSize2AndAction1AndAction3(){

        // given
        Action action1 = new Action("TEST1");
        Action action2 = new Action("TEST2");
        Action action3 = new Action("TEST3");
        entityManager.persist(action1);
        entityManager.persist(action2);
        entityManager.persist(action3);
        List<Long> ids = new ArrayList<>();
        ids.add(action1.getIdAction());
        ids.add(action3.getIdAction());

        // when
        List<Action> result = actionRepository.findAllById(ids);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(action1);
        assertThat(result).doesNotContain(action2);
        assertThat(result).contains(action3);

    }

}
