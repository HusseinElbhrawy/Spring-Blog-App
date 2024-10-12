package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Roles;
import com.husseinelbhrawy.BlogApp.Entity.User;
import com.husseinelbhrawy.BlogApp.Exceptions.BlogAPIException;
import com.husseinelbhrawy.BlogApp.Payload.LoginDTO;
import com.husseinelbhrawy.BlogApp.Payload.RegisterDTO;
import com.husseinelbhrawy.BlogApp.Repository.RolesRepository;
import com.husseinelbhrawy.BlogApp.Repository.UserRepository;
import com.husseinelbhrawy.BlogApp.Security.JWTTokenProvider;
import com.husseinelbhrawy.BlogApp.Service.Base.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private  final JWTTokenProvider jwtTokenProvider;


    @Autowired
    public AuthServiceImplementation(AuthenticationManager authenticationManager, UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDTO) {

        //? authenticate method need Authentication in param , but Authentication is an interface, so we use UsernamePasswordAuthenticationToken
        Authentication authentication =    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContext securityContext =   SecurityContextHolder. getContext();
        securityContext.setAuthentication(authentication);


        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public ResponseEntity<Object> register(RegisterDTO registerDTO) {
        //! add check if username exists in database
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        //! add check if email exists in database
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        User user = new User();
        //! Set Basic user data
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Roles> roles = new HashSet<>() ;


        //! Get And Set Role for New User
        Roles userRole = rolesRepository.findByName("USER").orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "Role not found"));
        roles.add(userRole);
        user.setRoles(roles);

        //? Save to database
        userRepository.save(user);

        return  ResponseEntity.ok("Registration Successful");
    }
}
