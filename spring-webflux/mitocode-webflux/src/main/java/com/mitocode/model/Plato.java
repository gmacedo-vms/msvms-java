package com.mitocode.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Si se utiliza lombook no es necesario crear los getters and setters @Data
@Document(collection = "platos")
public class Plato {

  @Id private String id;

  @NotEmpty
  @Field(name = "nombre")
  private String nombre;

  private Double precio;

  @NotNull(message = "No nulo")
  @Field(name = "estado")
  private Boolean estado;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }
}
