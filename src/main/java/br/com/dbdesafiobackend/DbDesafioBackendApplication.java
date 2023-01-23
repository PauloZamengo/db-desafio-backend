package br.com.dbdesafiobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DbDesafioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbDesafioBackendApplication.class, args);
	}

}
