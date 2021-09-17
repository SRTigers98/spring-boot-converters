package com.github.srtigers98.spring.boot.converters.util;

public final class UnitTestUtils {

  private UnitTestUtils() {
    // Utility class
  }

  public static String sanitizeFileContent(String fileContent) {
    if (fileContent.contains("\r\n")) {
      return fileContent.replaceAll("\r\n", "\n");
    } else if (fileContent.contains("\r")) {
      return fileContent.replaceAll("\r", "\n");
    } else {
      return fileContent;
    }
  }
}
