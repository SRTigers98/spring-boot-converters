package com.github.srtigers98.spring.boot.converters.itest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class XmlDocument {

  @Id
  private Integer id;
  private String  message;
}
