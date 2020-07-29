package com.gmail.jolantazych.event_register.service;

import com.gmail.jolantazych.event_register.model.Role;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.model.UserDTO;
import com.gmail.jolantazych.event_register.repository.RoleRepo;
import com.gmail.jolantazych.event_register.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private RoleRepo roleRepo;
    private UserRepo userRepo;
    public UserDetails userDetails;

    @Autowired
    public UserService(RoleRepo roleRepo, UserRepo userRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    public User registerWithRoleUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role userRole = roleRepo.findByRole("User");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public boolean isUserAlreadyExists(String email) {
        Role roleUser = roleRepo.findByRole("User");
        User user = userRepo.findByEmailAndRoles(email, roleUser);
        return user != null;
    }

    // metoda wykorzystywana przy logowaniu
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User activeUser = userRepo.findByEmail(email);
        if (activeUser == null) {
            throw new UsernameNotFoundException("User not found!"); //brak info dla użytkownika o błędzie, w konsoli też nie ma info
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
