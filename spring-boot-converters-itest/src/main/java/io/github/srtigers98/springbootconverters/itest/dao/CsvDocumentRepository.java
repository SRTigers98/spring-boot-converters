package io.github.srtigers98.springbootconverters.itest.dao;

import io.github.srtigers98.springbootconverters.itest.entity.CsvDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvDocumentRepository extends JpaRepository<CsvDocument, Integer> {
  // no additional methods needed
}
