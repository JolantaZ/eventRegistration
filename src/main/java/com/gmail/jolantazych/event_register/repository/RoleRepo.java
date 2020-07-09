package com.gmail.jolantazych.event_register.repository;

import com.gmail.jolantazych.event_register.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {

    Role findByRole(String roleName);

}
