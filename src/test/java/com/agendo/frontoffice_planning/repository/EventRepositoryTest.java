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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    @MockBean
    StorageService storageService;

    @Test
    void given_EventTEST__when_findById__then_returnEvent(){

        // given
        Address address = new Address("TEST", "00000");
        entityManager.persist(address);
        Users user = new Users("Test", "test@test", "1234");
        user.setAddress(address);
        entityManager.persist(user);
        Planning planning = new Planning("Test's planning");
        entityManager.persist(planning);
        Action action = new Action("Test");
        entityManager.persist(action);
        Event event = new Event(LocalDateTime.now(), action);
        event.setUser(user);
        event.setPlanning(planning);
        entityManager.persist(event);

        // when
        Event result = eventRepository.findById(event.getIdEvent()).get();

        // then
        assertThat(result).isEqualTo(event);

    }

    @Test
    void given_EventTEST_when_delete__then_findByIdReturnNull(){

        // given
        Address address = new Address("TEST", "00000");
        entityManager.persist(address);
        Users user = new Users("Test", "test@test", "1234");
        user.setAddress(address);
        entityManager.persist(user);
        Planning planning = new Planning("Test's planning");
        entityManager.persist(planning);
        Action action = new Action("Test");
        entityManager.persist(action);
        Event event = new Event(LocalDateTime.now(), action);
        event.setUser(user);
        event.setPlanning(planning);
        entityManager.persist(event);

        // when
        eventRepository.delete(event);
        Optional<Event> result = eventRepository.findById(event.getIdEvent());

        // then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void given_3EventTEST_when_findAll__then_Size3AndAllEvents(){

        // given
        Address address = new Address("TEST", "00000");
        entityManager.persist(address);
        Users user = new Users("Test", "test@test", "1234");
        user.setAddress(address);
        entityManager.persist(user);
        Planning planning = new Planning("Test's planning");
        entityManager.persist(planning);
        Action action1 = new Action("Test1");
        Action action2 = new Action("Test2");
        Action action3 = new Action("Test3");
        entityManager.persist(action1);
        entityManager.persist(action2);
        entityManager.persist(action3);
        Event event1 = new Event(LocalDateTime.now(), action1);
        Event event2 = new Event(LocalDateTime.now(), action2);
        Event event3 = new Event(LocalDateTime.now(), action3);
        event1.setUser(user);
        event1.setPlanning(planning);
        event2.setUser(user);
        event2.setPlanning(planning);
        event3.setUser(user);
        event3.setPlanning(planning);
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);

        // when
        List<Event> result = eventRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(event1);
        assertThat(result).contains(event2);
        assertThat(result).contains(event3);

    }

    @Test
    void given_3EventTEST_when_findAllById1And3__then_returnSize2AndEvent1Andevent3(){

        // given
        Address address = new Address("TEST", "00000");
        entityManager.persist(address);
        Users user = new Users("Test", "test@test", "1234");
        user.setAddress(address);        entityManager.persist(user);
        Planning planning = new Planning("Test's planning");
        entityManager.persist(planning);
        Action action1 = new Action("Test1");
        Action action2 = new Action("Test2");
        Action action3 = new Action("Test3");
        entityManager.persist(action1);
        entityManager.persist(action2);
        entityManager.persist(action3);
        Event event1 = new Event(LocalDateTime.now(), action1);
        Event event2 = new Event(LocalDateTime.now(), action2);
        Event event3 = new Event(LocalDateTime.now(), action3);
        event1.setUser(user);
        event1.setPlanning(planning);
        event2.setUser(user);
        event2.setPlanning(planning);
        event3.setUser(user);
        event3.setPlanning(planning);
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);
        List<Long> ids = new ArrayList<>();
        ids.add(event1.getIdEvent());
        ids.add(event3.getIdEvent());

        // when
        List<Event> result = eventRepository.findAllById(ids);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(event1);
        assertThat(result).doesNotContain(event2);
        assertThat(result).contains(event3);

    }



}
