package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.CsvDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.CsvDocument;
import com.github.srtigers98.spring.boot.converters.itest.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("csv")
class CsvControllerTest extends IntegrationTest {

  @Autowired
  private CsvDocumentRepository repository;

  @Autowired
  CsvControllerTest(WebApplicationContext webApplicationContext) {
    super(webApplicationContext);
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
    repository.saveAll(getTestDocuments());
  }

  @Test
  void getDocumentsTest() throws Exception {
    var request = MockMvcRequestBuilders.get("/csv");

    this.mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(mvcResult -> {
              var result = mvcResult.getResponse().getContentAsString();
              assertThat(result, is(notNullValue()));
              assertThat(result, is(this.sanitizeFileContent(
                  Files.readString(this.testGetCsvFile())
              )));
            });
  }

  @Test
  void saveDocumentsTest() throws Exception {
    var csv = Files.readString(this.testPostCsvFile());
    var testCsv = this.sanitizeFileContent(csv);

    var request = MockMvcRequestBuilders.post("/csv")
                                        .contentType("application/csv")
                                        .content(testCsv);

    this.mvc.perform(request)
            .andExpect(status().isOk());

    var dbSnapshot = this.repository.findAll();
    assertThat(dbSnapshot, is(notNullValue()));
    assertThat(dbSnapshot.size(), is(4));
    assertThat(dbSnapshot, hasItems(this.getTestDocuments()
                                        .toArray(CsvDocument[]::new)));
    assertThat(dbSnapshot, hasItems(
        new CsvDocument(42, "mouse"),
        new CsvDocument(99, "tigers")
    ));
  }

  private Path testGetCsvFile() throws FileNotFoundException {
    return ResourceUtils.getFile("classpath:csv/test-get.csv")
                        .toPath();
  }

  private Path testPostCsvFile() throws FileNotFoundException {
    return ResourceUtils.getFile("classpath:csv/test-post.csv")
                        .toPath();
  }

  private List<CsvDocument> getTestDocuments() {
    return List.of(new CsvDocument(1, "Hello World"),
                   new CsvDocument(2, "Hello from the other side"));
  }
}
