package com.github.srtigers98.spring.boot.converters;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.converters")
@Data
public class SpringBootConvertersProperties {

  private boolean enableXml   = false;
}
