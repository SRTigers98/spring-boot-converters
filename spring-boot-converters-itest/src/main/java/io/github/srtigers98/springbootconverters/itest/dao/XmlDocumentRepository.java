package io.github.srtigers98.springbootconverters.itest.dao;

import io.github.srtigers98.springbootconverters.itest.entity.XmlDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlDocumentRepository extends JpaRepository<XmlDocument, Integer> {
  // no additional methods needed
}
