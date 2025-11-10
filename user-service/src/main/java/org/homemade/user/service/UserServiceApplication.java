package org.homemade.user.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.homemade.user.service.command.interceptor.UserCommandInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.homemade")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Autowired
    public void registerUserCommandInterceptor(UserCommandInterceptor interceptor, CommandGateway commandGateway) {
        commandGateway.registerDispatchInterceptor(interceptor);
    }
}
