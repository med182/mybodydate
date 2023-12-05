package fr.mybodydaye.registelogin.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(scanBasePackages = "fr.mybodydaye.registelogin.api")
@CrossOrigin
@EnableWebSecurity
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
