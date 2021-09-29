package io.github.srtigers98.springbootconverters.itest.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CsvDocument {

  @Id
  @CsvBindByName
  private Integer id;
  @CsvBindByName
  private String  message;
}
