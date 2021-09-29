package io.github.srtigers98.springbootconverters.itest.web;

import io.github.srtigers98.springbootconverters.itest.dao.CsvDocumentRepository;
import io.github.srtigers98.springbootconverters.itest.entity.CsvDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Controller to test the CSV conversion feature.
 */
@RestController
@RequestMapping("/csv")
public class CsvController {

  private final CsvDocumentRepository repository;

  @Autowired
  public CsvController(CsvDocumentRepository repository) {
    this.repository = repository;
  }

  @GetMapping(produces = "application/csv")
  public CsvDocument[] getDocuments() {
    return this.repository.findAll()
                          .toArray(CsvDocument[]::new);
  }

  @PostMapping(consumes = "application/csv")
  public void saveDocuments(@RequestBody CsvDocument[] documents) {
    this.repository.saveAll(Arrays.asList(documents));
  }
}
