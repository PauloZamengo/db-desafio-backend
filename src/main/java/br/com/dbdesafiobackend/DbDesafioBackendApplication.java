package br.com.dbdesafiobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbDesafioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbDesafioBackendApplication.class, args);

		System.out.println("Hello Desafio DB!");
	}

}
