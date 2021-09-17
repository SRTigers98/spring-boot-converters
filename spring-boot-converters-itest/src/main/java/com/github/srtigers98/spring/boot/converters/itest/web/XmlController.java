package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.XmlDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocument;
import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping(path = "/list", produces = "application/xml")
  public List<XmlDocument> getXmlDocumentList() {
    return this.repository.findAll();
  }

  @GetMapping(path = "/list/custom", produces = "application/xml")
  public XmlDocuments getXmlDocumentListCustom() {
    return new XmlDocuments(this.repository.findAll());
  }

  @PostMapping(consumes = "application/xml")
  public void saveXmlDocument(@RequestBody XmlDocument xmlDocument) {
    this.repository.save(xmlDocument);
  }

  @PostMapping(path = "/list", consumes = "application/xml")
  public void saveXmlDocumentList(@RequestBody XmlDocuments xmlDocuments) {
    this.repository.saveAll(xmlDocuments.getDocuments());
  }
}
