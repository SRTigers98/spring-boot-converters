package io.github.srtigers98.springbootconverters.itest.util;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class IntegrationTest {

  protected MockMvc mvc;

  protected IntegrationTest(WebApplicationContext webApplicationContext) {
    this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                              .build();
  }

  protected String sanitizeFileContent(String fileContent) {
    if (fileContent.contains("\r\n")) {
      return fileContent.replaceAll("\r\n", "\n");
    } else if (fileContent.contains("\r")) {
      return fileContent.replaceAll("\r", "\n");
    } else {
      return fileContent;
    }
  }
}
