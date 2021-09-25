package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.JsonDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.JsonDocument;
import com.github.srtigers98.spring.boot.converters.itest.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JsonControllerTest extends IntegrationTest {

  private final JsonDocumentRepository repository;

  @Autowired
  protected JsonControllerTest(WebApplicationContext webApplicationContext,
                               JsonDocumentRepository repository) {
    super(webApplicationContext);
    this.repository = repository;
  }

  @BeforeEach
  void setUp() {
    this.repository.deleteAll();
    this.repository.save(testGetDocument());
  }

  @Test
  void getDocumentByIdTest() throws Exception {
    var json = Files.readString(ResourceUtils.getFile("classpath:json/test-get.json")
                                             .toPath());

    var request = MockMvcRequestBuilders.get("/json")
                                        .param("id", "0");

    this.mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
              var result = mvcResult.getResponse().getContentAsString();
              assertThat(result, is(sanitizeFileContent(json)));
            });
  }

  @Test
  void saveDocumentTest() throws Exception {
    var json = Files.readString(ResourceUtils.getFile("classpath:json/test-post.json")
                                             .toPath());

    var request = MockMvcRequestBuilders.post("/json")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json);

    this.mvc.perform(request)
            .andExpect(status().isOk());

    var dbEntry = this.repository.findById(42);
    assertThat(dbEntry.isPresent(), is(true));
    assertThat(dbEntry.get(), is(equalTo(testPostDocument())));
  }

  private JsonDocument testGetDocument() {
    return new JsonDocument(0, "Hello World");
  }

  private JsonDocument testPostDocument() {
    return new JsonDocument(42, "Hello Marvin ._.");
  }
}
