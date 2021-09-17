package com.github.srtigers98.spring.boot.converters;

import com.github.srtigers98.spring.boot.converters.csv.OpenCSVHttpMessageConverter;
import com.github.srtigers98.spring.boot.converters.xml.XStreamHttpMessageConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SpringBootConvertersConfigurationTest {

  @InjectMocks
  private SpringBootConvertersConfiguration tested;

  @Test
  void xStreamHttpMessageConverterTest() {
    var result = tested.xStreamHttpMessageConverter();

    assertThat(result, is(notNullValue()));
    assertThat(result, is(instanceOf(XStreamHttpMessageConverter.class)));
  }

  @Test
  void openCsvHttpMessageConverterTest() {
    var result = tested.openCsvHttpMessageConverter();

    assertThat(result, is(notNullValue()));
    assertThat(result, is(instanceOf(OpenCSVHttpMessageConverter.class)));
  }
}
