package com.github.srtigers98.spring.boot.converters.itest.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("XmlDocument")
@Entity
public class XmlDocument {

  @Id
  private Integer id;
  private String  message;
}
