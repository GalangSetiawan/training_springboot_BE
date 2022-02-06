package co.id.sofcograha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={ "co.id.sofcograha" })
public class SofcoRestApiApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SofcoRestApiApp.class, args);
	}
}
