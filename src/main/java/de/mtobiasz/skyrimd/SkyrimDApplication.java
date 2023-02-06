package de.mtobiasz.skyrimd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.mtobiasz.skyrimd"})
public class SkyrimDApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyrimDApplication.class, args);

    }

}
