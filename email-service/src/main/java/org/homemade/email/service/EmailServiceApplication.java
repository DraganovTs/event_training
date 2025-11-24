package org.homemade.email.service;


import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.homemade.email.service.command.interceptor.EmailCommandInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.homemade")
public class EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }

    @Autowired
    public void registerEmailCommandInterceptor(EmailCommandInterceptor interceptor, CommandGateway commandGateway) {
        commandGateway.registerDispatchInterceptor(interceptor);
    }

    @Autowired
    public void configure(EventProcessingConfigurer config) {
        config.registerListenerInvocationErrorHandler("email-group",
                conf -> PropagatingErrorHandler.instance());
    }
}
