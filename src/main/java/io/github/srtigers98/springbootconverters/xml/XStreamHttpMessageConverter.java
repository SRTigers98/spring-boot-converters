package io.github.srtigers98.springbootconverters.xml;

import com.thoughtworks.xstream.XStream;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * {@link HttpMessageConverter} to parse xml.
 * For parsing {@link XStream} is used.
 *
 * @author Benjamin Eder
 */
public class XStreamHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

  private static final MediaType[] SUPPORTED_MEDIA_TYPES =
      { MediaType.APPLICATION_XML, MediaType.TEXT_XML };

  private final XStream xStream;

  public XStreamHttpMessageConverter() {
    super(SUPPORTED_MEDIA_TYPES);
    this.xStream = new XStream();
    this.xStream.autodetectAnnotations(true);
    this.xStream.addPermission(this::supports);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    // Every Class should be supported
    return Object.class.isAssignableFrom(clazz);
  }

  @Override
  public Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    try (var inputStream = inputMessage.getBody()) {
      this.createAlias(clazz);
      return xStream.fromXML(inputStream);
    }
  }

  @Override
  public void writeInternal(Object o, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    try (var outputStream = outputMessage.getBody()) {
      this.createAlias(o.getClass());
      xStream.toXML(o, outputStream);
    }
  }

  // Create an alias so the XML tag is the simple name of the class
  // e.g. <Object>...</Object>
  private void createAlias(Class<?> clazz) {
    xStream.alias(clazz.getSimpleName(), clazz);
  }
}
