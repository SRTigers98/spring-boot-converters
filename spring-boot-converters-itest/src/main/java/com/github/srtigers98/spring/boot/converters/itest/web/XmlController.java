package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.XmlDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/xml")
public class XmlController {

  private final XmlDocumentRepository repository;

  @Autowired
  public XmlController(XmlDocumentRepository repository) {
    this.repository = repository;
  }

  @GetMapping(produces = "application/xml")
  public XmlDocument getXmlDocument(@RequestParam("id") Integer id) {
    return this.repository.findById(id)
                          .orElseThrow();
  }

  @PostMapping(consumes = "application/xml")
  public void saveXmlDocument(@RequestBody XmlDocument xmlDocument) {
    this.repository.save(xmlDocument);
  }
}
