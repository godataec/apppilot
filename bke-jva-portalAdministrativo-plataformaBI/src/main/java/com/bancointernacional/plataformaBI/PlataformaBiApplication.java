package com.bancointernacional.plataformaBI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class PlataformaBiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PlataformaBiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(PlataformaBiApplication.class);
	}

	
}
