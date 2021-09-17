package com.github.srtigers98.spring.boot.converters.itest.dao;

import com.github.srtigers98.spring.boot.converters.itest.entity.CsvDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvDocumentRepository extends JpaRepository<CsvDocument, Integer> {
  // no additional methods needed
}
