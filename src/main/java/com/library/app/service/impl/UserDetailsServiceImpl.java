package com.library.app.service.impl;

import com.library.app.entity.User;
import com.library.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userToFind = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User doesnt exists!"));

        Set<SimpleGrantedAuthority> authorities = userToFind.getRoles().stream()
                .flatMap(role -> getPermissionsForRole(role).stream())  // Ottieni tutti i permessi per ciascun ruolo
                .map(SimpleGrantedAuthority::new)  // Mappa ogni permesso in un SimpleGrantedAuthority
                .collect(Collectors.toSet());  // Colleziona tutto in un Set

        return new org.springframework.security.core.userdetails.User(userToFind.getUsername(), userToFind.getPassword(), authorities);
    }

    private Set<String> getPermissionsForRole(String role) {
        Set<String> permissions = new HashSet<>();

        switch (role) {
            case "ADMIN":
                permissions.add("CREATE_USER");
                permissions.add("UPDATE_USER");
                permissions.add("GET_USER");
                permissions.add("DELETE_USER");
                permissions.add("CREATE_BOOK");
                permissions.add("READ_BOOK");
                permissions.add("UPDATE_BOOK");
                permissions.add("DELETE_BOOK");
                permissions.add("DELETE_GENRE");
                break;
            case "USER":
                permissions.add("READ_BOOK");
                break;
        }
        return permissions;
    }
}
