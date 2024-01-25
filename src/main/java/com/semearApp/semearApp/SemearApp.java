package com.semearApp.semearApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SemearApp {

	public static void main(String[] args) {
        // Log das variáveis de ambiente
        System.out.println("DATABASE_URL: " + System.getenv("DATABASE_URL"));
        System.out.println("DATABASE_USERNAME: " + System.getenv("DATABASE_USERNAME"));
        System.out.println("DATABASE_PASSWORD: " + System.getenv("DATABASE_PASSWORD"));

        SpringApplication.run(SemearApp.class, args);
    }
}

