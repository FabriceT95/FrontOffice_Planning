package com.agendo.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_task")
    private Long idTask;
    @Basic
    @Column(name = "name_task")
    private String nameTask;
    @Basic
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @Basic
    @Column(name = "date_task_start")
    private LocalDateTime dateTaskStart;
    @Basic
    @Column(name = "date_task_end")
    private LocalDateTime dateTaskEnd;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Event.class, mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Event> events = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_planning", referencedColumnName = "id_planning", nullable = false)
    private Planning planning;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateTaskStart() {
        return dateTaskStart;
    }

    public void setDateTaskStart(LocalDateTime dateTaskStart) {
        this.dateTaskStart = dateTaskStart;
    }

    public LocalDateTime getDateTaskEnd() {
        return dateTaskEnd;
    }

    public void setDateTaskEnd(LocalDateTime dateTaskEnd) {
        this.dateTaskEnd = dateTaskEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Objects.equals(idTask, task.idTask)) return false;
        if (!Objects.equals(nameTask, task.nameTask)) return false;
        if (!Objects.equals(dateCreated, task.dateCreated)) return false;
        if (!Objects.equals(dateTaskStart, task.dateTaskStart))
            return false;
        if (!Objects.equals(dateTaskEnd, task.dateTaskEnd)) return false;
        if (!Objects.equals(description, task.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTask.intValue();
        result = 31 * result + (nameTask != null ? nameTask.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateTaskStart != null ? dateTaskStart.hashCode() : 0);
        result = 31 * result + (dateTaskEnd != null ? dateTaskEnd.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public List<Event> getEventsByIdTask() {
        return events;
    }

    public void setEvents(List<Event> eventsByIdTask) {
        this.events = eventsByIdTask;
    }

    public Planning getPlanningByIdPlanning() {
        return planning;
    }

    public void setPlanning(Planning planningByIdPlanning) {
        this.planning = planningByIdPlanning;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }
}
