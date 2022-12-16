package com.example.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="Planning")
@Table(name = "planning")
public class Planning {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_planning")
    private Long idPlanning;
    @Basic
    @Column(name = "name_planning")
    private String namePlanning;
    @Basic
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @OneToOne(mappedBy = "planning")
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    @OneToMany(targetEntity = Event.class, mappedBy = "planning", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Event> events = new ArrayList<>();

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }


    @OneToMany(targetEntity = Task.class, mappedBy = "planning", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Task> tasks = new ArrayList<>();

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }


    @OneToMany(mappedBy = "planning", cascade = CascadeType.ALL)
    private Set<Share> share = new HashSet<>();


    public Planning(String namePlanning, LocalDateTime dateCreated) {
        this.namePlanning = namePlanning;
        this.dateCreated = dateCreated;
    }

    public Planning() {
    }

    public Planning(Long idPlanning, String namePlanning, LocalDateTime dateCreated) {
        this.idPlanning = idPlanning;
        this.namePlanning = namePlanning;
        this.dateCreated = dateCreated;
    }

    public Planning(Long idPlanning, String namePlanning, LocalDateTime dateCreated, Users user) {
        this.idPlanning = idPlanning;
        this.namePlanning = namePlanning;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    public Planning(String namePlanning) {
        this.namePlanning = namePlanning;
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public String getNamePlanning() {
        return namePlanning;
    }

    public void setNamePlanning(String namePlanning) {
        this.namePlanning = namePlanning;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planning planning = (Planning) o;

        if (idPlanning != planning.idPlanning) return false;
      //  if (user != planning.user) return false;
        if (namePlanning != null ? !namePlanning.equals(planning.namePlanning) : planning.namePlanning != null)
            return false;
        if (dateCreated != null ? !dateCreated.equals(planning.dateCreated) : planning.dateCreated != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlanning.intValue();
        result = 31 * result + (namePlanning != null ? namePlanning.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
      //  result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

   /* @Override
    public String toString() {
        return "Planning{" +
                "idPlanning=" + idPlanning +
                ", namePlanning='" + namePlanning + '\'' +
                ", dateCreated=" + dateCreated +
                ", events=" + events +
                ", tasks=" + tasks +
                '}';
    }*/
}
