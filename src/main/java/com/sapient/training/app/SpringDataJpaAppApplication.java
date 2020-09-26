package com.sapient.training.app;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(scanBasePackages = "com.sapient.training")
@EnableJpaRepositories(basePackages = "com.sapient.training.data")
@EntityScan(basePackages = "com.sapient.training.entity")
@EnableOpenApi
public class SpringDataJpaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaAppApplication.class, args);
	}
	
	@Bean
	  public Docket openApiEmployeeStore() {
	    return new Docket(DocumentationType.OAS_30)
	        .groupName("open-api-employee-store")
	        .select()
	        .paths(empPaths())
	        .build();
	  }

	  private Predicate<String> empPaths() {
	    return regex(".*/api/.*");
	  }

}
