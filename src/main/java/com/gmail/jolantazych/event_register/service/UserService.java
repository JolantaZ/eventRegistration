package com.gmail.jolantazych.event_register.service;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.Role;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.repository.RoleRepo;
import com.gmail.jolantazych.event_register.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private RoleRepo roleRepo;
    private UserRepo userRepo;
    private EventRepo eventRepo;

    public UserDetails userDetails;

    public UserService(RoleRepo roleRepo, UserRepo userRepo, EventRepo eventRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public User registerWithRoleUser(User user) {
        Role userRole = roleRepo.findByRole("User");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean isUserAlreadyExists(String email) {
        Role roleUser = roleRepo.findByRole("User");
        User user = userRepo.findByEmailAndRoles(email, roleUser);
        return user != null;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User activeUser = userRepo.findByEmail(email);
        if (activeUser == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        String userEmail = activeUser.getEmail();
        String userPassword = activeUser.getPassword();


        userDetails = new org.springframework.security.core.userdetails.User(userEmail, userPassword, getGrantedAuthorities(activeUser.getRoles()));
        return userDetails;


    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }


}
