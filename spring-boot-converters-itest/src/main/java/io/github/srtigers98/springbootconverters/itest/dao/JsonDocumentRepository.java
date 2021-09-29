package io.github.srtigers98.springbootconverters.itest.dao;

import io.github.srtigers98.springbootconverters.itest.entity.JsonDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonDocumentRepository extends JpaRepository<JsonDocument, Integer> {
  // no additional methods needed
}
