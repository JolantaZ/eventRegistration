package com.gmail.jolantazych.event_register.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;

    @NotEmpty(message = "Filed connot be empty!") @NotNull
    private String title;

    @NotNull(message = "Field cannot be empty!")
    private double price;

    @Column(columnDefinition = "MEDIUMTEXT") // bez tego pole mapuje się na Varchar(255)
    private String description;

    @NotNull(message = "Field cannot be empty!")
    @Future(message = "Date must be from the future!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime term;

    @Column(name = "address")
    @NotEmpty(message = "Field cannot be empty!")
    private String location;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<User> users;

    public Event() {
    }

    public Event(@NotEmpty String title, @NotEmpty double price, String description, @Future LocalDateTime term, String location) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.term = term;
        this.location = location;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public void setTerm(LocalDateTime term) {
        this.term = term;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (Double.compare(event.getPrice(), getPrice()) != 0) return false;
        if (!getIdEvent().equals(event.getIdEvent())) return false;
        if (!getTitle().equals(event.getTitle())) return false;
        if (getDescription() != null ? !getDescription().equals(event.getDescription()) : event.getDescription() != null)
            return false;
        if (!getTerm().equals(event.getTerm())) return false;
        if (!getLocation().equals(event.getLocation())) return false;
        return getUsers() != null ? getUsers().equals(event.getUsers()) : event.getUsers() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getIdEvent().hashCode();
        result = 31 * result + getTitle().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getTerm().hashCode();
        result = 31 * result + getLocation().hashCode();
        result = 31 * result + (getUsers() != null ? getUsers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", term=" + term +
                ", location='" + location + '\'' +
                '}';
    }
}
