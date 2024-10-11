package fr.ceured.wall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WallApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WallApiApplication.class, args);
	}

}
