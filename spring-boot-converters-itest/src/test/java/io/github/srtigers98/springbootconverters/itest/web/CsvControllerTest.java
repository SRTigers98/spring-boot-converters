package io.github.srtigers98.springbootconverters.itest.web;

import io.github.srtigers98.springbootconverters.itest.dao.CsvDocumentRepository;
import io.github.srtigers98.springbootconverters.itest.entity.CsvDocument;
import io.github.srtigers98.springbootconverters.itest.util.IntegrationTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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

  private final CsvDocumentRepository repository;

  @Autowired
  protected CsvControllerTest(WebApplicationContext webApplicationContext,
                              CsvDocumentRepository repository) {
    super(webApplicationContext);
    this.repository = repository;
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
    MatcherAssert.assertThat(dbSnapshot, CoreMatchers.hasItems(this.getTestDocuments()
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
