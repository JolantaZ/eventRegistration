package com.gmail.jolantazych.event_register.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
// @NotEmpty - validates that the property is not null or empty; can be applied to String, Collection, Map or Array

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    //unique = true - ponowne postawienie bazy dla "create" wywala się przez constrainty
    private String email;

    //@Length(min= 6, message = "Password min lenght is 6 characters")
    private String password;

    @Column(name = "Full_Name")
    private String name;

    @Column(name = "Phone_Number")  // String? możliwość wpisywania tylo cyfr?
    private String phone;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Event")
    private Event event;

    public User() {
    }

    public User(@NotEmpty String email, @NotEmpty String password, @NotEmpty String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public User(@NotEmpty String email, @NotEmpty String password, @NotEmpty String name, String phone, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.roles = roles;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!getIdUser().equals(user.getIdUser())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getName().equals(user.getName())) return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        return getRoles().equals(user.getRoles());
    }

    @Override
    public int hashCode() {
        int result = getIdUser().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + getRoles().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", roles=" + roles +
                '}';
    }
}
