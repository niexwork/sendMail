package com.niex.sendmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SendmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendmailApplication.class, args);
	}

}
