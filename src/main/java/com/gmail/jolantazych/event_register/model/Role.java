package com.gmail.jolantazych.event_register.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(name = "name")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", role='" + role + '\'' +
                '}';
    }
}
