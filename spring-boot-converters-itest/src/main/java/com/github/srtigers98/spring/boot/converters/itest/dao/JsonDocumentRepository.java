package com.github.srtigers98.spring.boot.converters.itest.dao;

import com.github.srtigers98.spring.boot.converters.itest.entity.JsonDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonDocumentRepository extends JpaRepository<JsonDocument, Integer> {
  // no additional methods needed
}
