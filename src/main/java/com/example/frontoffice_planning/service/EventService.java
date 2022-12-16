package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Event;
import com.example.frontoffice_planning.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event createEvent(Event event){return eventRepository.save(event);}
}
