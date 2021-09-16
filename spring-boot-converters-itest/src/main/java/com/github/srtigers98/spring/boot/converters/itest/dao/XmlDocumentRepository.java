package com.github.srtigers98.spring.boot.converters.itest.dao;

import com.github.srtigers98.spring.boot.converters.itest.entity.XmlDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlDocumentRepository extends JpaRepository<XmlDocument, Integer> {
  // no additional methods needed
}
