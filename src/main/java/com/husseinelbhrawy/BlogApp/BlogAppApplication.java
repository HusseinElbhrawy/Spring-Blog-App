package com.husseinelbhrawy.BlogApp;

import com.husseinelbhrawy.BlogApp.Auth.Entity.Roles;
import com.husseinelbhrawy.BlogApp.Auth.Repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogAppApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	private  final RolesRepository rolesRepository;

	@Override
	public void run(String... args) throws Exception {
		rolesRepository.save(Roles.builder().name("USER").build());
		rolesRepository.save(Roles.builder().name("ADMIN").build());
	}
}
