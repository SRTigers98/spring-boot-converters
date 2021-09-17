# Spring Boot Converters

[![Build](https://github.com/SRTigers98/spring-boot-converters/actions/workflows/build.yml/badge.svg)](https://github.com/SRTigers98/spring-boot-converters/actions/workflows/build.yml)

---

## Overview

Spring Boot Converters is a Spring Boot Library that adds some HttpMessageConverters to the application.
Some common file formats can then be used for the REST interfaces.

## Supported Formats

|Format|Status|Since|Documentation|
|---|:---:|:---:|:---:|
|XML|Supported|1.0.0|In Progress|
|CSV|Supported|1.0.0|In Progress|

## Usage

### Installation

> Installation details will be available when the first version was released.

### Enable in Spring Boot

To enable the converters, the application class must first be provided with the annotation `@EnableSpringBootConverters`.

```java
@SpringBootApplication
@EnableSpringBootConverters
public class MyApplication {
  // ...
}
```

Then the formats, which should be used, must be activated in the properties.
Below is an overview of all currently available properties and the MIME types that are activated by them.

|Property|MIME Types|Since|
|---|---|:---:|
|spring.converters.enable-xml|application/xml & text/xml|1.0.0|
|spring.converters.enable-csv|application/csv & text/csv|1.0.0|

Now the formatter can be used in any rest controller.

### Example RestController - XML

```java
@RestController
public class DemoController {
  
  @GetMapping(produces = "application/xml")
  public DemoDto get() {
    // ...
  }
  
  @PostMapping(consumes = "application/xml")
  public void post(@RequestBody DemoDto demoDto) {
    // ...
  }
}
```
