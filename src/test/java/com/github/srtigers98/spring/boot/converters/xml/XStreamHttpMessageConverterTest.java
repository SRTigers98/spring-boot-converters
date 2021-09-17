package com.github.srtigers98.spring.boot.converters.xml;

import com.github.srtigers98.spring.boot.converters.util.UnitTestUtils;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XStreamHttpMessageConverterTest {

  @InjectMocks
  private XStreamHttpMessageConverter tested;

  @Test
  void supportsTest() {
    var result = tested.supports(XmlDocument.class);

    assertThat(result, is(true));
  }

  @Test
  void readInternalTest() throws IOException {
    var inputMessage = mock(HttpInputMessage.class);

    try (var xmlStream = Files.newInputStream(testXml())) {
      when(inputMessage.getBody()).thenReturn(xmlStream);

      var result = tested.readInternal(XmlDocument.class, inputMessage);

      assertThat(result, is(notNullValue()));
      assertThat(result, is(instanceOf(XmlDocument.class)));
      assertThat(result, is(testDocument()));
    }
  }

  @Test
  void writeInternalTest() throws IOException {
    var testDocument = testDocument();
    var outputMessage = mock(HttpOutputMessage.class);

    try (var xmlStream = new ByteArrayOutputStream()) {
      when(outputMessage.getBody()).thenReturn(xmlStream);

      tested.writeInternal(testDocument, outputMessage);

      var result = xmlStream.toString();
      assertThat(result, is(notNullValue()));
      assertThat(result, is(UnitTestUtils.sanitizeFileContent(
          Files.readString(testXml())
      )));
    }
  }

  private Path testXml() throws IOException {
    return ResourceUtils.getFile("classpath:xml/test.xml")
                        .toPath();
  }

  private XmlDocument testDocument() {
    return XmlDocument.builder()
                      .id(42)
                      .name("Dent")
                      .firstName("Arthur")
                      .build();
  }

  // XML test class
  @Builder
  @Data
  private static class XmlDocument {

    private Integer id;
    private String  name;
    private String  firstName;
  }
}
