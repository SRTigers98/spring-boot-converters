# Spring Boot Converters

---

## Overview

Spring Boot Converters is a Spring Boot Library that adds some HttpMessageConverters to the application.
Some common file formats can then be used for the REST interfaces like XML.

## Supported Formats

|Format|Status|Since|
|---|:---:|:---:|
|XML|Supported|1.0.0|
|CSV|Planned|-|
|Excel|Planned|-|

## Usage

### Installation

> Installation details will be available when the first version was released.

### Enable in Spring Boot

To enable the converters, first add the `@EnableSpringBootConverters` annotation to the application class.

```java
@SpringBootApplication
@EnableSpringBootConverters
public class MyApplication {
  // ...
}
```

Then the formats that should be used have to been enabled in the properties.
This is an overview of all current available properties and the MIME types they support.

|Property|MIME Types|Since|
|---|---|:---:|
|spring.converters.enable-xml|application/xml & text/xml|1.0.0|

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
