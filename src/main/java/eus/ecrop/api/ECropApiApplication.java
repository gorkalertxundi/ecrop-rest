package eus.ecrop.api;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@SpringBootApplication
public class ECropApiApplication {

	@Value("${spring.security.jwt.secret}")
	private String jwtSecret;

	public static void main(String[] args) {
		SpringApplication.run(ECropApiApplication.class, args);
	}

	@Bean
	Algorithm getAlgorithm() {
		return Algorithm.HMAC256(jwtSecret);
	}
}
