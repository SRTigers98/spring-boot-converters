package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.JsonDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.JsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class JsonController {

  private final JsonDocumentRepository repository;

  @Autowired
  public JsonController(JsonDocumentRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public JsonDocument getDocumentById(@RequestParam("id") Integer id) {
    return this.repository.findById(id)
                          .orElseThrow();
  }

  @PostMapping
  public void saveDocument(@RequestBody JsonDocument document) {
    this.repository.save(document);
  }
}
