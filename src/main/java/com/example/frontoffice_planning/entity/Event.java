package com.example.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event")
    private Long idEvent;
    @Basic
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "id_action", referencedColumnName = "id_action", nullable = false)
    private Action action;

    @ManyToOne
    @JoinColumn(name="id_task", referencedColumnName = "id_task", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name="id_planning", referencedColumnName = "id_planning", nullable = false)
    private Planning planning;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private Users users;

    public Event(LocalDateTime dateCreated, Action action) {
        this.dateCreated = dateCreated;
        this.action = action;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Event(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (idEvent != event.idEvent) return false;
        if (dateCreated != null ? !dateCreated.equals(event.dateCreated) : event.dateCreated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEvent.intValue();
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users user) {
        this.users = user;
    }
}
