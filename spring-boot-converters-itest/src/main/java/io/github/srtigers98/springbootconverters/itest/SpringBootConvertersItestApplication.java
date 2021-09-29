package io.github.srtigers98.springbootconverters.itest;

import io.github.srtigers98.springbootconverters.annotation.EnableSpringBootConverters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringBootConverters
public class SpringBootConvertersItestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootConvertersItestApplication.class, args);
  }
}
