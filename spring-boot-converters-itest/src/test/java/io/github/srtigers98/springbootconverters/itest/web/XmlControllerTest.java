package io.github.srtigers98.springbootconverters.itest.web;

import io.github.srtigers98.springbootconverters.itest.dao.XmlDocumentRepository;
import io.github.srtigers98.springbootconverters.itest.entity.XmlDocument;
import io.github.srtigers98.springbootconverters.itest.util.IntegrationTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("xml")
class XmlControllerTest extends IntegrationTest {

  private final XmlDocumentRepository repository;

  @Autowired
  protected XmlControllerTest(WebApplicationContext webApplicationContext,
                              XmlDocumentRepository repository) {
    super(webApplicationContext);
    this.repository = repository;
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    repository.save(testDocumentGet());
  }

  @Test
  void getXmlDocumentTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-get.xml")
                                            .toPath());
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
  void getXmlDocumentListTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-get-list.xml")
                                            .toPath());
    var testGetXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.get("/xml/list");

    this.mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
              var result = mvcResult.getResponse().getContentAsString();
              assertThat(result, is(notNullValue()));
              assertThat(result, is(testGetXml));
            });
  }

  @Test
  void getXmlDocumentListCustomTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-get-list-custom.xml")
                                            .toPath());
    var testGetXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.get("/xml/list/custom");

    this.mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
              var result = mvcResult.getResponse().getContentAsString();
              assertThat(result, is(notNullValue()));
              assertThat(result, is(testGetXml));
            });
  }

  @Test
  void saveXmlDocumentTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-post.xml")
                                            .toPath());
    var testPostXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.post("/xml")
                                        .contentType(MediaType.APPLICATION_XML)
                                        .content(testPostXml);

    this.mvc.perform(request)
            .andExpect(status().isOk());

    assertThat(repository.findById(99).isPresent(), is(true));
    assertThat(repository.findById(99).get(), CoreMatchers.is(testDocumentPost()));
  }

  @Test
  void saveXmlDocumentListTest() throws Exception {
    var xml = Files.readString(ResourceUtils.getFile("classpath:xml/test-post-list.xml")
                                            .toPath());
    var testPostXml = this.sanitizeFileContent(xml);

    var request = MockMvcRequestBuilders.post("/xml/list")
                                        .contentType(MediaType.APPLICATION_XML)
                                        .content(testPostXml);

    this.mvc.perform(request)
            .andExpect(status().isOk());

    var dbSnapshot = repository.findAll();
    assertThat(dbSnapshot, is(notNullValue()));
    assertThat(dbSnapshot.size(), is(3));
    assertThat(dbSnapshot.stream()
                         .map(XmlDocument::getId)
                         .collect(Collectors.toList()), hasItems(42, 55, 99));
  }

  private XmlDocument testDocumentGet() {
    return new XmlDocument(42, "The Answer!");
  }

  private XmlDocument testDocumentPost() {
    return new XmlDocument(99, "Hello World");
  }
}
