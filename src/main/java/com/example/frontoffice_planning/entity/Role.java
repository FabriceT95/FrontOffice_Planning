package com.example.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_role")
    private Long idRole;
    @Basic
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable( name = "users_roles",
            joinColumns = @JoinColumn( name = "id_role" ),
            inverseJoinColumns = @JoinColumn( name = "id_user" ) )
    private Set<Users> users = new HashSet<>();

   /* @ManyToOne @JoinColumn(name="id_user")
    private Users users;*/

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (idRole != role.idRole) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole.intValue();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


}
