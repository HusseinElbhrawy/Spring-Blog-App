package com.husseinelbhrawy.BlogApp.Config;

import com.husseinelbhrawy.BlogApp.Security.JWTAuthenticationEntryPoint;
import com.husseinelbhrawy.BlogApp.Security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    private  final  UserDetailsService userDetailsService;     //! This is Optional
    private  final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private  final JWTAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;  //! This is Optional
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    //! This is Optional
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.
                requestMatchers(HttpMethod.GET , "/api/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST , "/api/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);


        http.httpBasic(Customizer.withDefaults());


        http.csrf(AbstractHttpConfigurer::disable);


        return  http.build();
    }




//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails hussein = User.builder().username("hussein").password(passwordEncoder().encode("123456")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("123456")).roles("ADMIn"). build();
//        return  new InMemoryUserDetailsManager(hussein,admin);
//
//    }



}
