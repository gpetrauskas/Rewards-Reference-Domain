package com.app;

import com.lib.HelloService;
import org.springframework.context.annotation.Bean;

public class MyOwnHelloService implements HelloService {
    @Bean
    public void greet() {
        System.out.println("Hello, myOwnHelloService");
    }
}
