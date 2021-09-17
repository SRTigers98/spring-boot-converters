package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.CsvDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.CsvDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
