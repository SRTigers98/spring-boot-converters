package io.github.srtigers98.springbootconverters.csv;

import io.github.srtigers98.springbootconverters.util.UnitTestUtils;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenCSVHttpMessageConverterTest {

  @InjectMocks
  private OpenCSVHttpMessageConverter tested;

  @Test
  void supportsTest() {
    var result = tested.supports(CsvDocument[].class);

    assertThat(result, is(true));
  }

  @Test
  void supportsNoArrayTest() {
    var result = tested.supports(CsvDocument.class);

    assertThat(result, is(false));
  }

  @Test
  void readInternalTest() throws IOException {
    var inputMessage = mock(HttpInputMessage.class);

    try (var csvStream = Files.newInputStream(testInputCsv())) {
      when(inputMessage.getBody()).thenReturn(csvStream);

      var result = tested.readInternal(CsvDocument[].class, inputMessage);

      assertThat(result, is(notNullValue()));
      assertThat(result, is(instanceOf(CsvDocument[].class)));
      var csvResult = (CsvDocument[]) result;
      assertThat(csvResult.length, is(2));
      assertThat(csvResult[0].id, is(1));
      assertThat(csvResult[0].name, is("Dent"));
      assertThat(csvResult[0].firstName, is("Arthur"));
      assertThat(csvResult[1].id, is(2));
      assertThat(csvResult[1].name, is("Prefect"));
      assertThat(csvResult[1].firstName, is("Ford"));
    }
  }

  @Test
  void writeInternalTest() throws IOException {
    var documents = new CsvDocument[]{ new CsvDocument(1, "Dent", "Arthur"),
        new CsvDocument(2, "Prefect", "Ford") };
    var outputMessage = mock(HttpOutputMessage.class);

    try (var csvStream = new ByteArrayOutputStream()) {
      when(outputMessage.getBody()).thenReturn(csvStream);

      tested.writeInternal(documents, outputMessage);

      var result = csvStream.toString();

      assertThat(result, is(notNullValue()));
      assertThat(result, is(UnitTestUtils.sanitizeFileContent(
          Files.readString(testOutputCsv())
      )));
    }
  }

  @Test
  void writeInternalCSVRequiredFieldEmptyExceptionTest() throws IOException {
    var documents = new CsvDocument[]{ new CsvDocument(null, "Dent", "Arthur"),
        new CsvDocument(2, "Prefect", "Ford") };
    var outputMessage = mock(HttpOutputMessage.class);

    try (var csvStream = new ByteArrayOutputStream()) {
      when(outputMessage.getBody()).thenReturn(csvStream);

      var e = assertThrows(ResponseStatusException.class,
                           () -> tested.writeInternal(documents, outputMessage));

      assertThat(e.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
      assertThat(e.getCause(), is(instanceOf(CsvRequiredFieldEmptyException.class)));
    }
  }

  private Path testInputCsv() throws FileNotFoundException {
    return ResourceUtils.getFile("classpath:csv/test-input.csv")
                        .toPath();
  }

  private Path testOutputCsv() throws FileNotFoundException {
    return ResourceUtils.getFile("classpath:csv/test-output.csv")
                        .toPath();
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CsvDocument implements Serializable {

    @CsvBindByName(required = true)
    private Integer id;
    @CsvBindByName
    private String  name;
    @CsvBindByName
    private String  firstName;
  }
}
