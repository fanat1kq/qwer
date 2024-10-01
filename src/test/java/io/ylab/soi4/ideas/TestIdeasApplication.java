package io.ylab.soi4.ideas;

import org.springframework.boot.SpringApplication;

public class TestIdeasApplication {

    public static void main(String[] args) {
        SpringApplication.from(IdeasApplication::main).with(TestcontainersConfiguration.class)
            .run(args);
    }

}
