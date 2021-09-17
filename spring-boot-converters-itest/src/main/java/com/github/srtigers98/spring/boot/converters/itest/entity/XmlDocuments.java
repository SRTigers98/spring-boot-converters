package com.github.srtigers98.spring.boot.converters.itest.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmlDocuments {

  @XStreamAlias("XmlDocument")
  @XStreamImplicit
  private List<XmlDocument> documents;
}
