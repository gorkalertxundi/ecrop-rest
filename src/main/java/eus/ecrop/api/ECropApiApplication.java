package eus.ecrop.api;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@SpringBootApplication
public class ECropApiApplication extends SpringBootServletInitializer {

	@Value("${spring.security.jwt.secret}")
	private String jwtSecret;

	public static void main(String[] args) {
		SpringApplication.run(ECropApiApplication.class, args);
	}

	@Bean
	Algorithm getAlgorithm() {
		return Algorithm.HMAC256(jwtSecret);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ECropApiApplication.class);
	}
}
