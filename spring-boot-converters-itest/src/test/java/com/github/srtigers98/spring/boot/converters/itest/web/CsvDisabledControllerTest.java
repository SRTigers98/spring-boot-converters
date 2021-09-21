package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.CsvDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.CsvDocument;
import com.github.srtigers98.spring.boot.converters.itest.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CsvDisabledControllerTest extends IntegrationTest {

  private final CsvDocumentRepository repository;

  @Autowired
  protected CsvDisabledControllerTest(WebApplicationContext webApplicationContext,
                                      CsvDocumentRepository repository) {
    super(webApplicationContext);
    this.repository = repository;
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    repository.save(new CsvDocument(0, "Another random message"));
  }

  @Test
  void getDocumentsTest() throws Exception {
    var request = MockMvcRequestBuilders.get("/csv");

    this.mvc.perform(request)
            .andExpect(status().isInternalServerError());
  }

  @Test
  void saveDocumentsTest() throws Exception {
    var csv = Files.readString(ResourceUtils.getFile("classpath:csv/test-post.csv")
                                            .toPath());

    var request = MockMvcRequestBuilders.post("/csv")
                                        .contentType("application/csv")
                                        .content(csv);

    this.mvc.perform(request)
            .andExpect(status().isUnsupportedMediaType());
  }
}
