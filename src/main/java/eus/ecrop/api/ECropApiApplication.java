package eus.ecrop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@SpringBootApplication
public class ECropApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ECropApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ECropApiApplication.class);
	}
}
