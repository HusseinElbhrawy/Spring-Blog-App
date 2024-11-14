package com.husseinelbhrawy.BlogApp.Auth.Security;

import com.husseinelbhrawy.BlogApp.Auth.Repository.UserRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private  final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username or email : " + username));
//      try {
//          System.out.println("The usernameOrEmail : " + usernameOrEmail);
//          com.husseinelbhrawy.BlogApp.Auth.Entity.User users =  userRepository.findByUsernameOrEmail(usernameOrEmail ,usernameOrEmail)
//                  .orElseThrow(
//                          () -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
//
//          Set<GrantedAuthority> authoritySet = users.getRoles() != null ?
//                  users.getRoles().stream()
//                          .filter(Objects::nonNull) //! Avoid null roles
//                          .map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toSet())
//                  : Collections.emptySet();
//
//
//          return new User(
//                  users.getEmail(),
//                  users.getPassword(),
//                  authoritySet);
//      }catch (UsernameNotFoundException  e){
//          System.out.println("User not found with username or email: "+ usernameOrEmail);
//          throw e ;
//      }
    }


}
