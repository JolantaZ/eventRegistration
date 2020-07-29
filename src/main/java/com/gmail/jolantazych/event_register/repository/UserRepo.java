package com.gmail.jolantazych.event_register.repository;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.Role;
import com.gmail.jolantazych.event_register.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndRoles(String email, Role role);


    User findByEmailAndRolesAndRoles(String email, Role role1, Role role2); // wyszukiwanie dla więcej niż 1 rola

    /*
    java.lang.IllegalArgumentException: Failed to create query for method public abstract
    com.gmail.jolantazych.event_register.model.User com.gmail.jolantazych.event_register.repository.UserRepo.findByEmailAndRoles(java.lang.String,java.util.Set)!
    Operator SIMPLE_PROPERTY on roles requires a scalar argument, found interface java.util.Set in method public abstract
     */
    //User findByEmailAndRoles(String email, Set<Role> role);  //NIE MOŻNA DAWAĆ ARG. SET, LIST ITD.



}
