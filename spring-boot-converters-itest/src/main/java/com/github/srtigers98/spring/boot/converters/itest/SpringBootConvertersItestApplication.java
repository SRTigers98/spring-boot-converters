package com.github.srtigers98.spring.boot.converters.itest;

import com.github.srtigers98.spring.boot.converters.annotation.EnableSpringBootConverters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringBootConverters
public class SpringBootConvertersItestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootConvertersItestApplication.class, args);
  }
}
