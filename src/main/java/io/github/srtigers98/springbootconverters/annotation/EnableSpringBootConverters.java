package io.github.srtigers98.springbootconverters.annotation;

import io.github.srtigers98.springbootconverters.SpringBootConvertersConfiguration;
import io.github.srtigers98.springbootconverters.SpringBootConvertersProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables the {@link SpringBootConvertersConfiguration} to use the enabled converters.
 *
 * @author Benjamin Eder
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties
@Import({ SpringBootConvertersConfiguration.class,
    SpringBootConvertersProperties.class })
public @interface EnableSpringBootConverters {
  // no additional attributes needed
}
