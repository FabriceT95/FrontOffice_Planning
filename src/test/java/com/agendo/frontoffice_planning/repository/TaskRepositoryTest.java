package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Address;
import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Task;
import com.agendo.frontoffice_planning.entity.Users;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    StorageService storageService;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void when_findById_then_returnTask() {

        Planning planning = new Planning("Fabrice's planning", LocalDateTime.now());

        Planning p = entityManager.persist(planning);

        // given
        Task task = new Task();
        task.setNameTask("Task Name");
        task.setDescription("Description");
        task.setDateCreated(LocalDateTime.now());
        task.setDateTaskStart(LocalDateTime.now().plusDays(1));
        task.setDateTaskEnd(LocalDateTime.now().plusDays(1).plusHours(5));
        task.setPlanning(p);
        entityManager.persist(task);

        // when
        Optional<Task> result = taskRepository.findById(1L);

        // then
        assertThat(result.get()).isEqualTo(task);
    }

    @Test
    void when_deleteById_then_return() {

        Planning planning = new Planning("Fabrice's planning", LocalDateTime.now());

        Planning p = entityManager.persist(planning);

        // given
        Task task = new Task();
        task.setNameTask("Task Name");
        task.setDescription("Description");
        task.setDateCreated(LocalDateTime.now());
        task.setDateTaskStart(LocalDateTime.now().plusDays(1));
        task.setDateTaskEnd(LocalDateTime.now().plusDays(1).plusHours(5));
        task.setPlanning(p);
        task = entityManager.persist(task);


        // when

        entityManager.remove(task);
        Optional<Task> emptyResult = taskRepository.findById(1L);

        // then
        assertThat(emptyResult).isEqualTo(Optional.empty());
    }

    @Test
    void when_findByIdNotExist_then_returnEmpty() {

        // when
        Optional<Task> result = taskRepository.findById(5L);

        // then
        assertThat(result).isEqualTo(Optional.empty());
    }
}
