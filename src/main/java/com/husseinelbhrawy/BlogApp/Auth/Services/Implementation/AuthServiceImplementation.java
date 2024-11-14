package com.husseinelbhrawy.BlogApp.Auth.Services.Implementation;

import com.husseinelbhrawy.BlogApp.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.BlogApp.Auth.Entity.Roles;
import com.husseinelbhrawy.BlogApp.Auth.Entity.User;
import com.husseinelbhrawy.BlogApp.Exceptions.BlogAPIException;
import com.husseinelbhrawy.BlogApp.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.BlogApp.Auth.DTO.RegisterRequest;
import com.husseinelbhrawy.BlogApp.Auth.Repository.RolesRepository;
import com.husseinelbhrawy.BlogApp.Auth.Repository.UserRepository;
import com.husseinelbhrawy.BlogApp.Auth.Security.JWTTokenProvider;
import com.husseinelbhrawy.BlogApp.Auth.Services.Base.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private  final JWTTokenProvider jwtTokenProvider;


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        System.out.println("AUTH Service Login Request: " + loginRequest.getUsername());

        User user;
        if (loginRequest.getUsername().contains("@")) {
            user =  userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + loginRequest.getUsername()));
        }
        else  {
            user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + loginRequest.getUsername()));
        }
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder. getContext().setAuthentication(authentication);
        var accessToken = jwtTokenProvider.generateToken(user);


        return  AuthResponse.builder()
                .accessToken(accessToken)
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles((Roles) user.getRoles().toArray()[0])
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        //! add check if username exists in database
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        //! add check if email exists in database
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        Set<Roles> roles = new HashSet<>();

        Roles userRole = rolesRepository.findByName("USER").orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "Role not found"));

        roles.add(userRole);

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();



        //? Save to database
        User savedUser =  userRepository.save(user);
        var accessToken = jwtTokenProvider.generateToken(savedUser);



        return  AuthResponse.builder()
                .accessToken(accessToken)
                .name(savedUser.getName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .roles((Roles) savedUser.getRoles().toArray()[0])
                .build();
    }
}
