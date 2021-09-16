package com.github.srtigers98.spring.boot.converters.itest.web;

import com.github.srtigers98.spring.boot.converters.itest.dao.XmlDocumentRepository;
import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocument;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("xml")
class XmlControllerTest {

  @Autowired
  private MockMvc               mvc;
  @Autowired
  private XmlDocumentRepository repository;

  @BeforeEach
  void setUp() {
    repository.save(testDocumentGet());
  }

  @Test
  void getXmlDocumentTest() throws Exception {
    var testGetXml = Files.contentOf(ResourceUtils.getFile("classpath:xml/test-get.xml"),
                                     StandardCharsets.UTF_8);

    var request = MockMvcRequestBuilders.get("/xml")
                                        .queryParam("id", testDocumentGet().getId().toString());

    mvc.perform(request)
       .andExpect(status().isOk())
       .andExpect(mvcResult -> {
         var result = mvcResult.getResponse().getContentAsString();
         assertThat(result, is(testGetXml));
       });
  }

  @Test
  void saveXmlDocumentTest() throws Exception {
    var xml = Files.contentOf(ResourceUtils.getFile("classpath:xml/test-post.xml"),
                              StandardCharsets.UTF_8);

    var request = MockMvcRequestBuilders.post("/xml")
                                        .contentType(MediaType.APPLICATION_XML)
                                        .content(xml);

    mvc.perform(request)
       .andExpect(status().isOk());

    assertThat(repository.findById(99).isPresent(), is(true));
    assertThat(repository.findById(99).get(), is(testDocumentPost()));
  }

  private XmlDocument testDocumentGet() {
    return new XmlDocument(42, "The Answer!");
  }

  private XmlDocument testDocumentPost() {
    return new XmlDocument(99, "Hello World");
  }
}
