package com.agendo.frontoffice_planning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "action")
public class Action {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_action")
    private Long idAction;

    @Basic
    @Column(name = "name")
    private String name;

    public Action() {

    }

    public Action(String name) {
        this.name = name;
    }

    public Long getIdAction() {
        return idAction;
    }

    public void setIdAction(Long idAction) {
        this.idAction = idAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (idAction != action.idAction) return false;
        if (name != null ? !name.equals(action.name) : action.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAction.intValue();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
