package com.swang.activemqtt;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class ActivemqttApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivemqttApplication.class, args);
	}

}
