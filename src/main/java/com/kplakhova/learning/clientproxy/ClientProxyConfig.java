package com.kplakhova.learning.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {

    @Bean
    public UserResourceV1 getUserResourceV1() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        final String usersEndpointUrl = "http://localhost:8080/api/v1/users";
        ResteasyWebTarget target = client.target(usersEndpointUrl);
        UserResourceV1 proxy = target.proxy(UserResourceV1.class);
        return proxy;
    }
}
