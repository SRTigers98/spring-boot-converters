package com.github.srtigers98.spring.boot.converters;

import com.github.srtigers98.spring.boot.converters.csv.OpenCSVHttpMessageConverter;
import com.github.srtigers98.spring.boot.converters.xml.XStreamHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * Provides the {@link HttpMessageConverter} beans.
 * Only the enabled converters will be added to the context.
 *
 * @author Benjamin Eder
 */
@Configuration
public class SpringBootConvertersConfiguration {

  @Bean
  @ConditionalOnProperty("spring.converters.enable-xml")
  public HttpMessageConverter<Object> xStreamHttpMessageConverter() {
    return new XStreamHttpMessageConverter();
  }

  @Bean
  @ConditionalOnProperty("spring.converters.enable-csv")
  public HttpMessageConverter<Object[]> openCsvHttpMessageConverter() {
    return new OpenCSVHttpMessageConverter();
  }
}
