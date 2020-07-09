package com.gmail.jolantazych.event_register.repository;

import com.gmail.jolantazych.event_register.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {


    Optional<Event> findByIdEvent(Long aLong);

    @Query("SELECT u.event FROM User u WHERE u.idUser=?1")
    Event findEventByUserID(Long idUser);

}
