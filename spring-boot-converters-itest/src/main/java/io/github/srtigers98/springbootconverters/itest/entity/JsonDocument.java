package io.github.srtigers98.springbootconverters.itest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JsonDocument {

  @Id
  private Integer id;
  private String  message;
}
