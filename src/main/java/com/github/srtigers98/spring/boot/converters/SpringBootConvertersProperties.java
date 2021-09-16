package com.github.srtigers98.spring.boot.converters;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The properties to enable the implemented converters.
 *
 * @author Benjamin Eder
 */
@ConfigurationProperties(prefix = "spring.converters")
@Data
public class SpringBootConvertersProperties {

  private boolean enableXml = false;
}
