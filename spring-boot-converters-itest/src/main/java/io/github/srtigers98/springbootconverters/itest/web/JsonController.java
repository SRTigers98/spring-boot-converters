package io.github.srtigers98.springbootconverters.itest.web;

import io.github.srtigers98.springbootconverters.itest.dao.JsonDocumentRepository;
import io.github.srtigers98.springbootconverters.itest.entity.JsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to test that the default application type is still JSON.
 */
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
