package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.XmlDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocument;
import com.github.srtigers98.spring.boot.converters.itest.util.IntegrationTest;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("xml")
class XmlControllerTest extends IntegrationTest {

  @Autowired
  private XmlDocumentRepository repository;

  @Autowired
  XmlControllerTest(WebApplicationContext webApplicationContext) {
    super(webApplicationContext);
  }

  @BeforeEach
  void setUp() {
    repository.save(testDocumentGet());
  }

  @Test
  void getXmlDocumentTest() throws Exception {
    var xml = Files.contentOf(ResourceUtils.getFile("classpath:xml/test-get.xml"),
                              StandardCharsets.UTF_8);
    var testGetXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.get("/xml")
                                        .queryParam("id", testDocumentGet().getId().toString());

    this.mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
              var result = mvcResult.getResponse().getContentAsString();
              assertThat(result, is(testGetXml));
            });
  }

  @Test
  void saveXmlDocumentTest() throws Exception {
    var xml = Files.contentOf(ResourceUtils.getFile("classpath:xml/test-post.xml"),
                              StandardCharsets.UTF_8);
    var testPostXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.post("/xml")
                                        .contentType(MediaType.APPLICATION_XML)
                                        .content(testPostXml);

    this.mvc.perform(request)
            .andExpect(status().isOk());

    assertThat(repository.findById(99).isPresent(), is(true));
    assertThat(repository.findById(99).get(), is(testDocumentPost()));
  }

  private XmlDocument testDocumentGet() {
    return new XmlDocument(42, "The Answer!");
  }

  private XmlDocument testDocumentPost() {
    return new XmlDocument(99, "Hello World");
  }
}
