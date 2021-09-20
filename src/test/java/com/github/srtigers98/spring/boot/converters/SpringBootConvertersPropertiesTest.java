package com.github.srtigers98.spring.boot.converters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SpringBootConvertersPropertiesTest {

  @InjectMocks
  private SpringBootConvertersProperties tested;

  @Test
  void enableXmlDefaultTest() {
    var result = (boolean) ReflectionTestUtils.getField(tested, "enableXml");

    assertThat(result, is(false));
  }

  @Test
  void enableCsvDefaultTest() {
    var result = (boolean) ReflectionTestUtils.getField(tested, "enableCsv");

    assertThat(result, is(false));
  }
}
