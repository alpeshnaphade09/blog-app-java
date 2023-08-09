package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.config.AppConstants;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;


@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
		System.out.println("Hello...");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("password"));
		
		try {
			Role role = new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_NORMAL);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepository.saveAll(roles);
			
			result.forEach(r -> {
				System.out.println(r.getName());
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
