package io.github.srtigers98.springbootconverters.itest.web;

import io.github.srtigers98.springbootconverters.itest.dao.XmlDocumentRepository;
import io.github.srtigers98.springbootconverters.itest.entity.XmlDocument;
import io.github.srtigers98.springbootconverters.itest.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class XmlDisabledControllerTest extends IntegrationTest {

  private final XmlDocumentRepository repository;

  @Autowired
  protected XmlDisabledControllerTest(WebApplicationContext webApplicationContext,
                                      XmlDocumentRepository repository) {
    super(webApplicationContext);
    this.repository = repository;
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    repository.save(new XmlDocument(0, "A random message"));
  }

  @Test
  void getXmlDocumentTest() throws Exception {
    var request = MockMvcRequestBuilders.get("/xml")
                                        .queryParam("id", "0");

    this.mvc.perform(request)
            .andExpect(status().isInternalServerError());
  }

  @Test
  void saveXmlDocumentTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-post.xml")
                                            .toPath());

    var request = MockMvcRequestBuilders.post("/xml")
                                        .contentType(MediaType.APPLICATION_XML)
                                        .content(xml);

    this.mvc.perform(request)
            .andExpect(status().isUnsupportedMediaType());
  }
}
