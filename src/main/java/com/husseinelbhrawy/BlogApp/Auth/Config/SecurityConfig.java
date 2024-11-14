package com.husseinelbhrawy.BlogApp.Auth.Config;

import com.husseinelbhrawy.BlogApp.Auth.Security.JWTAuthenticationEntryPoint;
import com.husseinelbhrawy.BlogApp.Auth.Security.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


    private  final  UserDetailsService userDetailsService;
    private  final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private  final JWTAuthenticationFilter jwtAuthenticationFilter;




    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers("/api/**" , "/api/forgot-password").permitAll()
                                .requestMatchers(  "/api/posts**").permitAll()
                                .requestMatchers(HttpMethod.POST , "/api/auth/**").permitAll().anyRequest().authenticated()

                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize.
//                requestMatchers(HttpMethod.GET , "/api/**")
//                        .permitAll()
//                        .requestMatchers(HttpMethod.POST , "/api/auth/**")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated()).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//        http.addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
//
//        http.httpBasic(Customizer.withDefaults());
//
//        http.csrf(AbstractHttpConfigurer::disable);
//
//
//        return  http.build();
//    }





}
