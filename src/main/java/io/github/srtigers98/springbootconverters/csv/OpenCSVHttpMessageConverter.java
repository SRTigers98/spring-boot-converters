package io.github.srtigers98.springbootconverters.csv;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MimeType;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * {@link HttpMessageConverter} to parse csv.
 * For parsing OpenCSV library is used.
 *
 * @author Benjamin Eder
 */
public class OpenCSVHttpMessageConverter extends AbstractHttpMessageConverter<Object[]> {

  private static final MediaType[] SUPPORTED_MEDIA_TYPES =
      { MediaType.asMediaType(new MimeType("application", "csv")),
          MediaType.asMediaType(new MimeType("text", "csv")) };

  public OpenCSVHttpMessageConverter() {
    super(SUPPORTED_MEDIA_TYPES);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return clazz.isArray();
  }

  @Override
  protected Object[] readInternal(Class<? extends Object[]> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    try (var inputStream = inputMessage.getBody();
         var inputStreamReader = new InputStreamReader(inputStream);
         var reader = new BufferedReader(inputStreamReader)) {
      return new CsvToBeanBuilder<>(reader)
          .withType(clazz.getComponentType())
          .build()
          .parse()
          .toArray(size -> (Object[]) Array.newInstance(clazz.getComponentType(), size));
    }
  }

  @Override
  protected void writeInternal(Object[] o, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    try (var outputStream = outputMessage.getBody();
         var outputWriter = new OutputStreamWriter(outputStream);
         var writer = new BufferedWriter(outputWriter)) {
      new StatefulBeanToCsvBuilder<>(writer)
          .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
          .withApplyQuotesToAll(false)
          .withOrderedResults(true)
          .build()
          .write(Arrays.asList(o));
    } catch (CsvFieldAssignmentException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                        String.format("CSV conversion failed: %s", e.getMessage()),
                                        e);
    }
  }
}
