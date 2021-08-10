package yte.intern.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import yte.intern.spring.security.util.DatabasePopulator;

import javax.annotation.PostConstruct;
import javax.persistence.EntityListeners;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityApplication extends SpringBootServletInitializer {

	private final DatabasePopulator databasePopulator;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@PostConstruct
	public void populateDatabase() {
		databasePopulator.populateDatabase();
	}
}
