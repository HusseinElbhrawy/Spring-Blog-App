package com.husseinelbhrawy.BlogApp.Security;

import com.husseinelbhrawy.BlogApp.Repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      try {
          System.out.println("The usernameOrEmail : " + usernameOrEmail);
          com.husseinelbhrawy.BlogApp.Entity.User users =  userRepository.findByUsernameOrEmail(usernameOrEmail ,usernameOrEmail)
                  .orElseThrow(
                          () -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

//          Set<GrantedAuthority> authoritySet = users.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toSet());
          Set<GrantedAuthority> authoritySet = users.getRoles() != null ?
                  users.getRoles().stream()
                          .filter(Objects::nonNull) // Avoid null roles
                          .map(roles -> new SimpleGrantedAuthority(roles.getName()))
                          .collect(Collectors.toSet())
                  : Collections.emptySet();


          return new User(
                  users.getEmail(),
                  users.getPassword(),
                  authoritySet);
      }catch (UsernameNotFoundException  e){
          System.out.println("User not found with username or email: "+ usernameOrEmail);
          throw e ;
      }
    }


}
